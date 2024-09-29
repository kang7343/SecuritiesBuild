require("dotenv").config(); // 環境変数

const { Sequelize } = require("sequelize");
const sequelize = new Sequelize(
  "stock",
  "postgres",
  process.env.POSTGRES_PASSWORD,
  {
    host: "127.0.0.1",
    dialect: "postgres",
  }
);

module.exports = sequelize;