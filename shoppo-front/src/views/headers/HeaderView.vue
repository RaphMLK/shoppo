<template>
  <q-header elevated class="header">
    <q-toolbar class="toolbar">
      <img alt="Shoppo Logo" src="@/assets/shoppo_logo.png" class="logo" />
      <q-toolbar-title class="slogan">{{ slogan }}</q-toolbar-title>
    </q-toolbar>
  </q-header>
</template>

<script>
export default {
  name: "HeaderView",
  components: {},
  data() {
    return {
      leftDrawerOpen: false,
      slogan: "Le commerce local et agréable !"
    };
  }
};
</script>
<style scoped lang="scss">
.layout {
  .q-header {
    background-color: orange;
    .toolbar {
      .slogan {
        color: #d41c1c;
      }
    }
  }
  .q-page-container {
    height: 100%;
  }
}

@media (min-width: 640px) {
  .layout {
    .q-header {
      height: 10%;
      .toolbar {
        height: 100%;
        .logo {
          height: 100%;
        }
        .slogan {
          text-align: right;
          font-size: 200%;
        }
      }
    }
  }
}

@media (max-width: 640px) {
  .layout {
    .q-header {
      height: 20%;
      background-color: orange;
      .toolbar {
        height: 100%;
        align-content: center;
        display: block;
        .logo {
          height: 75%;
          margin-left: auto;
          margin-right: auto;
          display: block;
        }
        .slogan {
          text-align: center;
        }
      }
    }
  }
  .q-page-container {
    background-color: darkorange;
  }
}
</style>
