

var webpack = require('webpack');
var is_debug = process.env.NODE_ENV !== "production";
var path = require('path');


module.exports = {
    devtool: is_debug ? "inline-sourcemap" : null,
    context: path.join(__dirname, "src"),
    entry: "./main_client.js" ,
    module: {
        rules: [
            {
                test: /\.jsx?$/,
                exclude: /(node_modules|bower_components)/,
                loader: 'babel-loader',
                query: {
                    presets: ['es2015', 'react', 'stage-0'],
                    plugins: ['react-html-attrs', 'transform-class-properties', 'transform-decorators-legacy'],
                }
            },
            {
                test: /\.css$/,
                loader: "style-loader!css-loader"
            },
        ]
    },
    output: {
        publicPath: __dirname + "/build/",
        path: __dirname + "/build/",
        filename: "client.min.js"
    },
};