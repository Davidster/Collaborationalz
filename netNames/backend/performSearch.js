"use strict";

const esDomain = "search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com"; // David's ES domain
const PAGES_INDEX = "pages";

const elasticsearch = require("elasticsearch");
let client = new elasticsearch.Client({
  host: esDomain,
  apiVersion: "6.3"
});

String.prototype.replaceAll = function(search, replacement) {
  var target = this;
  return target.replace(new RegExp(search, 'g'), replacement);
};

let fixWhitespace = (string) => {
  return string.replaceAll("\t","").replaceAll(/\n\s*\n\s*\n/,"\n\n");
};

(async () => {
  try {
    const response = await client.search({
      index: PAGES_INDEX,
      body: {
        size: 25,
        query: {
          match: {
            page_text: "computer"
          }
        }
      }
    });
    let rawText = response.hits.hits[0]._source.page_text;
    console.log(fixWhitespace(rawText));
  } catch (error) {
    console.log("Error:", error);
  }
})();
