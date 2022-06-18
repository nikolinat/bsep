import Vue from "vue";
import Vuex from "vuex";
import certificates from "./modules/certificates.js";
import csr from "./modules/csr.js";
import authentication from "./modules/authetication.js"
import log from "./modules/log.js"

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    certificates,
    csr,
    authentication,
    log
  },
});
