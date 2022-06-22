import axios from 'axios'
import toastr from 'toastr'
import { getIdFromToken } from './token.js'
      
var client = {}

export function tryConnecting() {
  let userId = getIdFromToken();
  if (userId) {
    axios.post('/sockets/notificationSocket/' + userId)
    .then(() => {
      client = window.io.connect(`http://localhost:9093/notificationSocket/` + userId), {
        forceNew: true,
        transports: ['polling', 'websocket']
      }
      client.on("event", data => {
        toastr.options = {
          "closeButton": true,
          "closeDuration": 3000,
          "position-class":'toast-top-full-width'
        }
        toastr.warning(data.message, data.title);
      })
    });

  }
}