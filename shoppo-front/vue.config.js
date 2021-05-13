const { GenerateSW } = require("workbox-webpack-plugin");

//http://localhost:8081

module.exports = {
  devServer: {
    //proxy: "http://172.28.100.26:30632"
    proxy: "http://localhost:8081"
    //proxy: "http://dev.shoppo.fr"
  },

  configureWebpack: {
    plugins: [new GenerateSW()]
  },

  pwa: {
    name: "shoppo-front",
    themeColor: "#000000",
    msTileColor: "#FFFFFF",
    appleMobileWebAppCapable: "yes",
    appleMobileWebAppStatusBarStyle: "black",
    manifestOptions: {
      display: "landscape",
      background_color: "#42B883"
      // ...
    },
    // configure the workbox plugin
    workboxPluginMode: "InjectManifest",
    workboxOptions: {
      // swSrc is required in InjectManifest mode.
      swSrc: "src/registerServiceWorker.js"
      // ...other Workbox options...
    }
  },

  pluginOptions: {
    quasar: {
      importStrategy: "kebab",
      rtlSupport: true
    }
  },

  transpileDependencies: ["quasar"]
};
