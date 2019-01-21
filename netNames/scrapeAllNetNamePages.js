"use strict";

const PROTOCOL = "http";
const ENCS_DOMAIN = "users.encs.concordia.ca";

const path = require("path");
const fs = require("promise-fs");
const cheerio = require("cheerio");
const rp = require("request-promise");
const _cliProgress = require("cli-progress");

let loadAndParsePageRequest = async (netName) => {
  let pageUrl = `${PROTOCOL}://${ENCS_DOMAIN}/~${netName}`;
  // console.log("Using request to load page:", pageUrl);
  let response, $;
  try {
    response = await rp({
      uri: pageUrl,
      resolveWithFullResponse: true
    });
  } catch (error) {
    console.log(`Encountered network error (${error.statusCode}) for url ${pageUrl}`);
    return [
      netName, pageUrl, error.statusCode, error.message, "NETWORK ERROR", "NETWORK ERROR", "NETWORK ERROR"
    ];
  }
  try {
    $ = cheerio.load(response.body);
  } catch (error) {
    console.log(`Encountered parse error parse error  for url ${pageUrl}:`, error, "page content:", response.body);
    return [
      netName, pageUrl, response.statusCode, response.statusMessage, "PARSE ERROR", "PARSE ERROR", "PARSE ERROR"
    ];
  }
  return [
    netName, pageUrl, response.statusCode, response.statusMessage, $.html().length, $("a").length, $.text().split(" ").filter(word => word !== "").length
  ];
};

(async () => {
  // read and parse net names from netnames.txt
  let allNetNames = await fs.readFile("netnames.txt", "utf-8").then(netNamesText => {
    return JSON.parse(netNamesText);
  });

  // define variables
  let allResults = [];
  let workers = 4;
  let netNamesPerWorker = Math.ceil(allNetNames.length / workers);
  let completed = 0;
  let startTime = Date.now();

  // log some useful shiet
  console.log(`Number of workers: ${workers}`);
  console.log(`Number of net names per worker: ${netNamesPerWorker}`);
  console.log(`Start time: ${new Date(startTime).toLocaleTimeString()}\n`);

  // create progres bar to be displayed in terminal
  const progressBar = new _cliProgress.Bar({ etaBuffer: 100 }, _cliProgress.Presets.shades_classic);
  progressBar.start(allNetNames.length, 0);

  // split net names into segments, assign each segment to one work, run all workers concurrently
  let workerPromises = [];
  for(let i = 0; i < workers; i++) {
    let netNames = allNetNames.slice(i * netNamesPerWorker, (i + 1) * netNamesPerWorker);
    // async functions return Promises implicitly
    let workerPromise = (async () => {
      for(let j = 0; j < netNames.length; j++) {
        let results = await loadAndParsePageRequest(netNames[j]);
        allResults.push(results);
        progressBar.update(++completed);
      }
    })();
    workerPromises.push(workerPromise);
  };

  // wait for all workers to finish
  await Promise.all(workerPromises);
  progressBar.stop();

  // convert results to csv string and write to file
  let outFileName = "netNamesBasicInfo.csv";
  await fs.writeFile(outFileName,
    `netName,pageUrl,statusCode,statusMessage,pageTextLength,hyperlinkCount,wordCount \
    \n${allResults.map(result => result.join(",")).join("\n")}`
  );

  let endTime = Date.now();
  let deltaT = endTime - startTime;
  console.log(`\nResults written to ${outFileName}`);
  console.log(`End time: ${new Date(endTime).toLocaleTimeString()}`);
  console.log(`Elapsed time: ${Math.floor(deltaT/3600000)} or ${Math.floor(deltaT/60000)} mins or ${deltaT/1000} sex`);
})();
