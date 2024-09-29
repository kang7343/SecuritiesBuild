const fs = require("fs");
const Stock = require("../util/model.js", "utf8");

// 加工されたJsonファイルの読み込み
fs.readFile("../util/processedData.json", "utf8", async (err, data) => {
  if (err) {
    console.error("Error reading file:", err);
    return;
  }
  const stock_info = JSON.parse(data);

  try {
    // DBにデータinsert
    await Stock.bulkCreate(stock_info);
    console.log("**********insert data**********");
  } catch (error) {
    console.error("Error inserting data:", error);
  }
});