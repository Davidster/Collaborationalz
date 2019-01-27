"use strict";

const PROTOCOL = "http";
const ENCS_DOMAIN = "users.encs.concordia.ca";
const DB_FILE_NAME = "netNames.db";
const DB_TABLE_NAME = "encspageinfo";
const ALLOWED_EXTENSIONS = [ "", ".html", ".txt" ];
const ES_DOMAIN = "search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com"; // David's ES domain
const PAGES_INDEX = "pages";

const path = require("path");
const fs = require("promise-fs");
const cheerio = require("cheerio");
const rp = require("request-promise");
const _cliProgress = require("cli-progress");
const sqlite3 = require('sqlite3').verbose();
const englishWords = require('an-array-of-english-words');
const franc = require("franc");
const elasticsearch = require("elasticsearch");
let client = new elasticsearch.Client({
  host: ES_DOMAIN,
  apiVersion: "6.3",
  requestTimeout: 300000
});

String.prototype.replaceAll = function(search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

// create progres bar to be displayed in terminal
const progressBar = new _cliProgress.Bar({
  format: `Progress: {bar} {percentage}% || {value}/{total} {eta}s`,
  etaBuffer: 100
 }, _cliProgress.Presets.shades_classic);


let connectToDB = () => {
  return new Promise((resolve, reject) => {
    let db = new sqlite3.Database(DB_FILE_NAME, (err) => {
      if (err) {
        return reject(err);
      }
      resolve(db);
    });
  });
};

let performQuery = (db, query, params) => {
  return new Promise((resolve, reject) => {
    db.run(query, params, (err) => {
      if(err) {
        return reject(err);
      }
      resolve(this);
    });
  });
};

let insertRow = (db, data) => {
  return performQuery(db, `
    INSERT INTO ${DB_TABLE_NAME} (
      originNetName,
      pageUrl,
      statusCode,
      statusMessage,
      pageText
    ) VALUES (
      $originNetName,
      $pageUrl,
      $statusCode,
      $statusMessage,
      $pageText
    )
  `, data);
};

let getRows = (db) => {
  return new Promise((resolve, reject) => {
    db.all(`SELECT * FROM ${DB_TABLE_NAME}`, (err, rows) => {
      if(err) {
        return reject(err);
      }
      resolve(rows);
    });
  });
};

let rowExists = (db, pageUrl) => {
  return new Promise((resolve, reject) => {
    db.get(`SELECT * FROM ${DB_TABLE_NAME} WHERE pageUrl = $pageUrl`, {
      $pageUrl: pageUrl
    }, (err, result) => {
      if(err) {
        return reject(err);
      }
      resolve(!!result);
    });
  })
}

(async () => {
  let db;
  try {
    console.log("Reading raw rows from sqlite...");

    db = await connectToDB();
    let rawRows = await getRows(db);
    // let netNameSet = new Set();
    // // select a random row from the DB
    // let pageInfo = rows[Math.round(Math.random() * rows.length)];

    console.log("Removing duplicate rows...");

    // console.log("before dedupe", rawRows.length);

    // remove accidental duplicates:
    let acc = [];
    let rows = [];
    rawRows.forEach(rawRow => {
      let rowUrl = new URL(rawRow.pageUrl);
      let fixedUrl = `${rowUrl.origin}${rowUrl.pathname}`;
      rawRow.pageUrl = fixedUrl;
      if(acc.indexOf(fixedUrl) < 0) {
        rows.push(rawRow);
        acc.push(fixedUrl);
      }
    });

    // console.log("after dedupe", rows.length);

    // sort by length of html
    // rows = rows.sort((a,b) => {
    //   return b.pageText.length - a.pageText.length
    // });
    // let pageInfo = rows[0];
    // let $ = cheerio.load(pageInfo.pageText);
    // let pageTextt = $.text();
    // console.log("is README_ENCS.txt:", (path.basename(pageInfo.pageUrl) === "README_ENCS.txt" && pageTextt.length === 196));
    // console.log(pageInfo.pageUrl);
    // console.log(pageTextt.length);
    // console.log(pageTextt);

    // print url and pageLength of 100 largest pages
    // console.log(rows.slice(0,100).map(row => {
    //   row.pageTextLength = row.pageText.length;
    //   row.pageText = row.pageText.slice(0,100);
    //   // return row;
    //   return {
    //     url: row.pageUrl,
    //     length: row.pageTextLength
    //   };
    // }));

    // return;

    rows = rows.slice(0, rows.length);

    console.log("Discarding empty, README_ENCS.txt, and non-english rows...");

    progressBar.start(rows.length, 0);

    // console.log(rows);

    // return;

    let wordCount = 0;
    let validTextCount = 0;
    let finalDocs = [];

    for(let i = 0; i < rows.length; i++) {
      let pageInfo = rows[i];
      let $ = cheerio.load(pageInfo.pageText);
      let pageText = $.text();
      let guessedLanguage = franc(pageText);
      let isEmpty = pageInfo.pageText.length === 0;
      let isREADME_ENCS = (path.basename(pageInfo.pageUrl) === "README_ENCS.txt" && pageText.length === 196);
      if(!isREADME_ENCS && !isEmpty && (guessedLanguage === "eng" || guessedLanguage === "fra")) {
        // console.log();
        // console.log(pageInfo.pageUrl);
        // console.log(pageText.slice(0,250).replaceAll("\n", ""));
        // console.log();
        // let pageWords = pageText.split(" ");
        // console.log(`Total words: ${pageWords.length}`);
        // console.log(`Franc output: ${franc(pageText)}`);
        // wordCount = wordCount + pageWords.length;
        // let pageEnglishWords = pageWords.filter(word => {
        //   return englishWords.indexOf(word) >= 0;
        // });
        // console.log(`Total english words: ${pageEnglishWords.length}`);
        validTextCount++;
        /*
          Insert doc into Elasicsearch here:
          {
            url: pageInfo.pageUrl,
            pageText: pageText
          }
        */
        finalDocs.push({
          url: pageInfo.pageUrl,
          page_text: pageText
        });
      }
      progressBar.update(i);
    }

    progressBar.stop();

    console.log();
    console.log(`Ready to insert ${finalDocs.length}/${rawRows.length} of the raw rows into Elasticsearch`);
    console.log();

    // try {
    //   await client.index({
    //     index: PAGES_INDEX,
    //     type: "_doc",
    //     id: pageInfo.pageUrl,
    //     body: {
    //       url: pageInfo.pageUrl,
    //       page_text: pageText
    //     }
    //   });
    // } catch (error) {
    //   console.log("ES error:", error);
    // }

    progressBar.start(finalDocs.length, 0);

    let docsPerBatch = 50;
    let batches = Math.ceil(finalDocs.length / docsPerBatch);
    let count = 0;

    for(let batch = 0; batch < batches; batch++) {
      let docs = finalDocs.slice(batch * docsPerBatch, (batch + 1) * docsPerBatch);
      let requestBody = [];
      docs.forEach(doc => {
        requestBody.push({ index: { _index: PAGES_INDEX, _type: "_doc", _id: doc.url } });
        requestBody.push(doc);
      });
      // console.log(requestBody);
      let response = await client.bulk({
        body: requestBody
      });
      count += docs.length;
      progressBar.update(count);
    }

    progressBar.stop();

    // console.log("wordCount:", wordCount);

    // rows.forEach(row => {
    //   netNameSet.add(row.originNetName);
    // });
    // console.log(netNameSet.size);
    // console.log(rows.filter(row => {
    //   return path.extname(row.pageUrl) === ".txt"
    // }).length);
  } catch (err) {
    console.log("error", err);
  }
  db.close();
})();
