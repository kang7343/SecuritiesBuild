// DBの情報
// const { Sequelize } from '@sequelize/core';

const { DataTypes } = require("sequelize");
const sequelize = require("../util/config.js");

sequelize
    .sync({ force: false })
    .then(() => {
        console.log("**********create table**********");
    })
    .catch((error) => {
        console.error("********Error creating tables********:", error);
    });

const Stock = sequelize.define(
    "stock_info",
    {
        code: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        name: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        eng_name: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        sector_code: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        sector_name: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        sub_sector_code: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        sub_sector_name: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        category: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        market_code: {
            type: DataTypes.STRING,
            allowNull: false,
        },

        market_name: {
            type: DataTypes.STRING,
            allowNull: false,
        },
    },
    {
        tableName: "stock_info",
        timestamps: false,
    }
);

module.exports = Stock;