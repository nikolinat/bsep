// const fs = require('fs')
// module.exports = {
//     // devServer: {
//     //   host: "localhost",
//     //   https: true
//     // }
//     devServer: {
//         https: {
//         key: fs.readFileSync('./certs/private.pem', "utf-8"),
//           cert: fs.readFileSync('./certs/root.cer', "utf-8"),
//         },
//         public: 'https://localhost:8081/'
//     }
//   }

module.exports = {
  // proxy all webpack dev-server requests starting with /api
  // to our Spring Boot backend (localhost:8088) using http-proxy-middleware
  // see https://cli.vuejs.org/config/#devserver-proxy
  devServer: {
    proxy: 'http://localhost:8080'
  },
  // Change build paths to make them Maven compatible
  // see https://cli.vuejs.org/config/
  outputDir: 'target/dist',
  assetsDir: 'static'
}