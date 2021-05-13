import Vue from "vue";

import "./styles/quasar.scss";
import "quasar/dist/quasar.ie.polyfills";
import "@quasar/extras/roboto-font/roboto-font.css";
import "@quasar/extras/material-icons/material-icons.css";
import "@quasar/extras/fontawesome-v5/fontawesome-v5.css";
import "@quasar/extras/ionicons-v4/ionicons-v4.css";
import { Quasar } from "quasar";
import VueFusionCharts from "vue-fusioncharts";
import FusionCharts from "fusioncharts";
import Column2D from "fusioncharts/fusioncharts.charts";
import FusionTheme from "fusioncharts/themes/fusioncharts.theme.fusion";
import VueApexCharts from "vue-apexcharts";

import { Notify, Loading } from "quasar";

Vue.use(Quasar, {
  plugins: {
    Notify,
    Loading
  },
  config: {
    notify: {}
  },
  VueFusionCharts,
  FusionCharts,
  Column2D,
  FusionTheme,
  VueApexCharts
});

Vue.component("apexchart", VueApexCharts);
