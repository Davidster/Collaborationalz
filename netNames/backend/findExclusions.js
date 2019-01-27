"use strict";

const PROTOCOL = "http";
const ENCS_DOMAIN = "users.encs.concordia.ca";
const DB_FILE_NAME = "netNames.db";
const DB_TABLE_NAME = "encspageinfo";
const ALLOWED_EXTENSIONS = [ "", ".html", ".txt" ];

const path = require("path");
const fs = require("promise-fs");
const cheerio = require("cheerio");
const rp = require("request-promise");
const _cliProgress = require("cli-progress");
const sqlite3 = require('sqlite3').verbose();

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
  // create sqlite3 database and setup table(s)
  let db;
  try {
    db = await connectToDB();
    let rows = await getRows(db);
    console.log(rows.length);
    // console.log(rows.length);
    // let row = await rowExists(db, "http://users.encs.concordia.ca/~d_hucul/");
    // console.log(row);
    // return;
  } catch (err) {
    return console.log("error", err);
  }
})();
