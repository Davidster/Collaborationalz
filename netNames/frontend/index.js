const esDomain = "search-flic-data-bttaxaq3fkwa5cppkdhru5oky4.us-east-1.es.amazonaws.com"; // David's ES domain
const PAGES_INDEX = "pages";
const RESULT_PAGE_SIZE = 10;
const TEXT_PREVIEW_CHAR_LIMIT = 1000;

const client = new $.es.Client({
  host: esDomain,
  apiVersion: "6.3"
});

String.prototype.replaceAll = function(search, replacement, flags = "g") {
  var target = this;
  return target.replace(new RegExp(search, flags), replacement);
};

let fixWhitespace = (string) => {
  return string.replaceAll("\t","").replaceAll(/\n\s*\n\s*\n/,"\n\n");
};

let performSearch = async (query, offset = 0) => {
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
          pageText: hit.highlight.page_text.map(pageText => fixWhitespace(pageText)).join("\n")
      }))
    };
  } catch (error) {
    console.log("Search error:", error);
  }
};

let handleSearch = async () => {
  let searchQuery = $("#inputQuery").val();
  let searchResponse = await performSearch(searchQuery);
  let results = searchResponse.items;
  let $outputContainer = $("#outputContainer");
  $outputContainer.empty();
  $outputContainer.append(`<div>Search returned ${searchResponse.total} results.</div>`);
  results.forEach(result => {
    let pageText = result.pageText;
    let textSliced = false;
    if(pageText.length > TEXT_PREVIEW_CHAR_LIMIT) {
      textSliced = true;
      let firstSearchTermIndex = pageText.indexOf(`<span class="highlighted">`);
      let firstVisibleCharacterIndex = Math.max(firstSearchTermIndex - (TEXT_PREVIEW_CHAR_LIMIT / 2), 0);
      pageText = pageText.slice(firstVisibleCharacterIndex, firstVisibleCharacterIndex + TEXT_PREVIEW_CHAR_LIMIT);
    }
    $outputContainer.append(`
      <div>
        <a href="${result.url}" class="urlContainer">${result.url}</a>
        <div class="pageTextContainer ${textSliced ? "textSliced" : ""}">
          ${pageText}
        </div>
      </div>
    `);
  });
};

$(document).ready(() => {
  (async () => {
    $("#searchButton").on("click", handleSearch);
    $("#inputQuery").on('keyup', function (e) {
        if (e.keyCode == 13) {
            handleSearch();
        }
    });
  })();
});
