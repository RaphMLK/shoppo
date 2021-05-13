import axios from "axios";
import store from "../store/index.js";

var routes = [
  "/rest/manageUser/login",
  "/rest/manageUser/resetPassword",
  "/rest/client/"
];

export default function setup() {
  axios.interceptors.request.use(
    function(config) {
      if (routes.includes(config.url)) {
        return config;
      }
      const token = store.state.userStore.token;
      if (token) {
        config.headers.Authorization = `${token}`;
      }
      return config;
    },
    function(err) {
      return Promise.reject(err);
    }
  );
}
