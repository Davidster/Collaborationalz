"use strict";

// delete index with:
// curl -X DELETE https://search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com/pages

// force merge index with:
// curl -X POST https://search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com/pages/_forcemerge

const esDomain = "search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com"; // David's ES domain
const PAGES_INDEX = "pages";

const elasticsearch = require("elasticsearch");
let client = new elasticsearch.Client({
  host: esDomain,
  apiVersion: "6.3"
});

(async () => {
  // Create the pages index
  try {
    const response = await client.indices.create({
      index: PAGES_INDEX,
      body: {
        mappings: {
          _doc: {
            properties: {
              url: { type: "keyword" },
              page_text: {
                type: "text",
                analyzer: "english"
              }
            }
          }
        }
      }
    });
    console.log("Successfully created pages index!", response);
  } catch (error) {
    console.log("Error creating pages index:", error.body.error.reason);
  }
})();
