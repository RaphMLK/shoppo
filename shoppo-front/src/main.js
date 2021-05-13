import Vue from "vue";
import App from "./App.vue";
import "./registerServiceWorker";
import router from "./router";
import store from "./store";

import "@fortawesome/fontawesome-free/css/all.css";
import "@fortawesome/fontawesome-free/js/all.js";
import "./quasar";

Vue.config.productionTip = false;

import interceptorsSetup from "./config/axiosConfig.js";

interceptorsSetup();

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
