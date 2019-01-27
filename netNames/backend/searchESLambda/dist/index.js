'use strict';

console.log('Loading function');

const esDomain = "search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com"; // David's ES domain
const PAGES_INDEX = "pages";
const RESULT_PAGE_SIZE = 25;
const TEXT_PREVIEW_CHAR_LIMIT = 1000;

const AWS = require("aws-sdk");
const elasticsearch = require("elasticsearch");
const httpAwsEs = require("http-aws-es");

String.prototype.replaceAll = function(search, replacement, flags = "g") {
  var target = this;
  return target.replace(new RegExp(search, flags), replacement);
};

let fixWhitespace = (string) => {
  return string.replaceAll("\t","").replaceAll(/\n\s*\n\s*\n/,"\n\n");
};

let performSearch = async (client, query, offset = 0) => {
  try {
    const response = await client.search({
      index: PAGES_INDEX,
      body: {
        from: offset,
        size: RESULT_PAGE_SIZE,
        query: {
          match: {
            page_text: query
          }
        },
        highlight : {
          fields : {
            page_text : {}
          },
          pre_tags: [ "<span class='highlighted'>" ],
          post_tags: [ "</span>" ]
        }
      }
    });
    return {
      total: response.hits.total,
      items: response.hits.hits.map(hit => ({
          url: hit._source.url,
          highlightedPageText: hit.highlight.page_text.map(pageText => fixWhitespace(pageText)).join("\n")
      }))
    };
  } catch (error) {
    console.log("Search error:", error);
  }
};

exports.handler = (event, context, callback) => {
  console.log('Received event:', JSON.stringify(event, null, 2));

  if(!event.queryStringParameters.query) {
    return callback("Missing event param 'query'");
  }
  let esClient = new elasticsearch.Client({
    host: esDomain,
    apiVersion: "6.3",
    connectionClass: httpAwsEs
  });

  (async () => {
    try {
      let results = await performSearch(esClient, event.queryStringParameters.query);
      callback(null, {
        statusCode: 200,
        headers: {
          "ContentType": "application/json",
          "X-Requested-With": '*',
          "Access-Control-Allow-Headers": 'Content-Type,X-Amz-Date,Authorization,X-Api-Key,x-requested-with',
          "Access-Control-Allow-Origin": '*',
          "Access-Control-Allow-Methods": 'POST,GET,OPTIONS'
        },
        body: JSON.stringify(results)
      });
    } catch (error) {
      console.log("Search error:", error);
      callback(error);
    }
  })();
};
