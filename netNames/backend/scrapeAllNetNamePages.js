"use strict";

const PROTOCOL = "http";
const ENCS_DOMAIN = "users.encs.concordia.ca";
const DB_FILE_NAME = "netNames.db";
const DB_TABLE_NAME = "encspageinfo";
const ALLOWED_EXTENSIONS = [ "", ".html", ".txt" ];
const NET_NAME_BLACKLIST = [ "s_ranadi","c_harma", "to_hang", "w_anqi", "d_gadhiy" ];

const path = require("path");
const fs = require("promise-fs");
const cheerio = require("cheerio");
const rp = require("request-promise");
const _cliProgress = require("cli-progress");
const sqlite3 = require('sqlite3').verbose();

let visitedUrls = [];
let completed = 0;

// create progres bar to be displayed in terminal
const progressBar = new _cliProgress.Bar({
  format: `Progress: {bar} {percentage}% || {value}/{total} {eta}s || {lastPathSearched}`,
  etaBuffer: 100
 }, _cliProgress.Presets.shades_classic);

let loadAndParsePageRequest = async (pageUrl, parentPage) => {
  // console.log("Using request to load page:", pageUrl);
  visitedUrls.push(pageUrl);
  progressBar.update(completed, { lastPathSearched: pageUrl });
  let response, $, parseError, networkError;
  try {
    response = await rp({
      uri: pageUrl,
      resolveWithFullResponse: true,
      followRedirect: false
    });
  } catch (error) {
    console.log(`\nEncountered network error (${error.statusCode}) for url ${pageUrl} with parent page = ${parentPage}\n`);
    return [ {
      pageUrl: pageUrl,
      statusCode: error.statusCode,
      statusMessage: error.statusMessage,
      pageText: "NETWORK ERROR"
    } ];
  }
  let linksToFollow = [];
  try {
    // console.log("Page text", response.body);
    $ = cheerio.load(response.body);
    let hyperLinks = $("a");
    // console.log(`Links:`);
    hyperLinks.each((i, hyperLink) => {
      let urlObj = new URL($(hyperLink).attr("href"), pageUrl);
      let newPageUrl = `${urlObj.origin}${urlObj.pathname}`;
      // console.log(urlObj);
      if(newPageUrl &&
      urlObj.host === ENCS_DOMAIN &&
      visitedUrls.indexOf(newPageUrl) < 0 &&
      ALLOWED_EXTENSIONS.indexOf(path.extname(urlObj.pathname)) >= 0) {
        visitedUrls.push(newPageUrl);
        linksToFollow.push(newPageUrl);
      }
      // console.log(`${href} ==> ${urlObj.href} (${urlObj.host})`);
    });
  } catch (error) {
    parseError = true;
    console.log(`\nEncountered parse error parse error  for url ${pageUrl}: with parent page = ${parentPage}\n`);
  }
  //
  // console.log();
  // console.log(pageUrl);
  // console.log("Page text:", response.body.slice(0, 50));
  // console.log("Content length:", response.headers['content-length']);
  // console.log("Links to follow:", linksToFollow);
  // console.log("Parse error:", parseError);
  // console.log();

  let allResults = [ {
    pageUrl: pageUrl,
    statusCode: response.statusCode,
    statusMessage: response.statusMessage,
    pageText: response.body
  } ];

  if(linksToFollow.length > 0) {
    let subResults = [];
    for(let i = 0; i < linksToFollow.length; i++) {
      let subResult = await loadAndParsePageRequest(linksToFollow[i], pageUrl);
      allResults = allResults.concat(subResult);
    }
  }
  return allResults;
};

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

let checkIfRowExists = (db, pageUrl) => {
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
  // create sqlite3 database and setup table(s)
  let db;
  try {
    // await fs.unlink(DB_FILE_NAME);
    db = await connectToDB();
    // await performQuery(db, `
    //   CREATE TABLE ${DB_TABLE_NAME} (
    //     originNetName TEXT,
    //     pageUrl TEXT PRIMARY KEY,
    //     statusCode TEXT,
    //     statusMessage TEXT,
    //     pageText TEXT
    //   )
    // `);
    console.log("Database setup complete");
    // return;
  } catch (err) {
    return console.log("sqlite connection error", err);
  }

  // read and parse net names from netnames.txt
  let allNetNames = await fs.readFile("netnames.txt", "utf-8").then(netNamesText => {
    return JSON.parse(netNamesText);
  }).then(async allNetNames => {
    return allNetNames.filter(netName => {
      return NET_NAME_BLACKLIST.indexOf(netName) < 0;
    });
  })

  // allNetNames = [ "c_harma" ];

  // define variables
  // let allResults = [];
  let workers = 4;
  let netNamesPerWorker = Math.ceil(allNetNames.length / workers);
  let startTime = Date.now();

  // log some useful shiet
  console.log(`Number of workers: ${workers}`);
  console.log(`Number of net names per worker: ${netNamesPerWorker}`);
  console.log(`Start time: ${new Date(startTime).toLocaleTimeString()}\n`);

  progressBar.start(allNetNames.length, 0);

  // split net names into segments, assign each segment to one work, run all workers concurrently
  let workerPromises = [];
  for(let i = 0; i < workers; i++) {
    let netNames = allNetNames.slice(i * netNamesPerWorker, (i + 1) * netNamesPerWorker);
    // async functions return Promises implicitly
    let workerPromise = (async () => {
      for(let j = 0; j < netNames.length; j++) {
        let pageUrl = `${PROTOCOL}://${ENCS_DOMAIN}/~${netNames[j]}/`;
        let rowExists = await checkIfRowExists(db, pageUrl);
        if(!rowExists) {
          let results = await loadAndParsePageRequest(pageUrl);
          // console.log("loadAndParsePageRequest returned", results);
          // console.log(results.map(result => {
          //   return result.pageUrl
          // }));
          // console.log(`Finished traversing ${netNames[j]}. Inserting ${results.length} rows into db.`);
          for(let k = 0; k < results.length; k++) {
            let result = results[k];
            try {
              await insertRow(db, {
                $originNetName: netNames[j],
                $pageUrl: result.pageUrl,
                $statusCode: result.statusCode,
                $statusMessage: result.statusMessage,
                $pageText: result.pageText
              });
            } catch (err) {
              console.log(`SQL error: ${err.message} for page ${result.pageUrl}`);
            }
            // allResults.push(result);
          }
        }
        progressBar.update(++completed);
      }
    })();
    workerPromises.push(workerPromise);
  };

  // wait for all workers to finish
  await Promise.all(workerPromises);
  progressBar.stop();

  // let rows = await getRows(db);
  // console.log(rows.map(row => {
  //   return row.pageUrl
  // }));

  // convert results to csv string and write to file
  // let outFileName = "netNamesBasicInfo.csv";
  // await fs.writeFile(outFileName,
  //   `netName,pageUrl,statusCode,statusMessage,pageTextLength,hyperlinkCount,wordCount \
  //   \n${allResults.map(result => result.join(",")).join("\n")}`
  // );
  // console.log(`\nResults written to ${outFileName}`);

  // close db connection
  db.close();

  let endTime = Date.now();
  let deltaT = endTime - startTime;
  console.log(`End time: ${new Date(endTime).toLocaleTimeString()}`);
  console.log(`Elapsed time: ${Math.floor(deltaT/3600000)} or ${Math.floor(deltaT/60000)} mins or ${deltaT/1000} sex`);
})();
