const fs = require('fs')

module.exports = {
  devServer: {
    proxy: 'https://localhost:8081',
    https: {
              key: fs.readFileSync('./certs/root.pem', "utf-8"),
              cert: fs.readFileSync('./certs/root.cer', "utf-8"),
              },
    port: 8081,
  },

  outputDir: 'target/dist',
  assetsDir: 'static'
}