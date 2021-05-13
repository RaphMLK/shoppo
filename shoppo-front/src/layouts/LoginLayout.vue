<template>
  <q-layout view="hHh lpR fFf" class="layout">
    <HeaderView />
    <q-page-container class="container">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import HeaderView from "@/views/headers/HeaderView.vue";

export default {
  name: "LayoutDefault",

  components: {
    HeaderView
  },

  data() {
    return {};
  }
};
</script>

<style scoped lang="scss">
.layout {
  .q-page-container {
    height: 100%;
  }
}

@media (max-width: 640px) {
  .q-page-container {
    background-color: darkorange;
  }
}
</style>
