const fs = require("fs");
let rawData = fs.readFileSync("../util/rawData.json", "utf8");
let jsonData = JSON.parse(rawData);

let processedData = jsonData.info.map((info) => {
  return {
    code: info.Code,
    name: info.CompanyName,
    eng_name: info.CompanyNameEnglish,
    sector_code: info.Sector17Code,
    sector_name: info.Sector17CodeName,
    sub_sector_code: info.Sector33Code,
    sub_sector_name: info.Sector33CodeName,
    category: info.ScaleCategory,
    market_code: info.MarketCode,
    market_name: info.MarketCodeName,
  };
});

fs.writeFileSync(
  "../util/processedData.json",
  JSON.stringify(processedData, null, 2)
);