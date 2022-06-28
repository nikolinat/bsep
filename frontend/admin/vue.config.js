const fs = require('fs')
module.exports = {
    devServer: {
        https: {
        key: fs.readFileSync('./certs/private.pem', "utf-8"),
          cert: fs.readFileSync('./certs/root.cer', "utf-8"),
        },
        public: 'https://localhost:8080/',
        port: 8080
    }
  }
