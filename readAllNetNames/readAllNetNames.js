"use strict";

const parentFolder = "/nfs/home/";
const fs = require("promise-fs");
const path = require("path");

(async () => {
  try {
    let allNetNames = [];
    for (var i = 97; i <= 122; i++) {
      let dirInfo = await fs.readdir(path.join(parentFolder, String.fromCharCode(i)));
      console.log(`dir: ${String.fromCharCode(i)}, regular files: ${dirInfo.filter(dir => (dir.split(".").length > 1))}`);
      allNetNames = allNetNames.concat(dirInfo);
    }
    await fs.writeFile("netnames.txt", JSON.stringify(allNetNames,null,2));
    console.log("Success");
  } catch(error) {
    console.log("error", error);
  }
})();
