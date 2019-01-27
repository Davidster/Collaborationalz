const API_URL = "https://v2e3iircr2.execute-api.us-east-1.amazonaws.com/dev/search"
const PAGES_INDEX = "pages";
const RESULT_PAGE_SIZE = 10;
const TEXT_PREVIEW_CHAR_LIMIT = 1000;

let performSearch = async (query, offset = 0) => {
  try {
    let searchResponse = await $.get(`https://v2e3iircr2.execute-api.us-east-1.amazonaws.com/dev/search?query=${query}`);
    console.log(searchResponse);
    return searchResponse;
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
    let pageText = result.highlightedPageText;
    let textSliced = false;
    // if(pageText.length > TEXT_PREVIEW_CHAR_LIMIT) {
    //   textSliced = true;
    //   let firstSearchTermIndex = pageText.indexOf(`<span class="highlighted">`);
    //   let firstVisibleCharacterIndex = Math.max(firstSearchTermIndex - (TEXT_PREVIEW_CHAR_LIMIT / 2), 0);
    //   pageText = pageText.slice(firstVisibleCharacterIndex, firstVisibleCharacterIndex + TEXT_PREVIEW_CHAR_LIMIT);
    // }
    $outputContainer.append(`
      <div>
        <a target="_blank" href="${result.url}" class="urlContainer">${result.url}</a>
        <div class="pageTextContainer ${textSliced ? "textSliced" : ""}">
          ${pageText}
        </div>
      </div>
    `);
  });
};

$(document).ready(() => {
  (async () => {
    $("#inputQuery").on('keyup', function (e) {
        if (e.keyCode == 13) {
            handleSearch();
        }
    });
  })();
});
