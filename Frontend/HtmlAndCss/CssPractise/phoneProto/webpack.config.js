const path = require('path');
const webpack = require('webpack');
const ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    entry: './src/index.js',
    output: {
        filename: 'index.js',
        path: path.resolve(__dirname, 'dist'),
    },
    mode: 'development',
    devServer: {
        contentBase: './dist',
    },
    module: {
        rules: [{
            test: /\.js$/,
            exclude: /node_modules/
        }, {
            test: /\.s[ac]ss$/i,
            use: [
                // Creates `style` nodes from JS strings
                'style-loader',
                // Translates CSS into CommonJS
                'css-loader',
                // Compiles Sass to CSS
                'sass-loader',
            ],
        }, {
            test: /\.(png|jp(e*)g)$/,
            loader: 'url-loader',
            options: {
                limit: 8000,
                name: 'images/[hash]-[name].[ext]'
            }
        }]
    }
};
