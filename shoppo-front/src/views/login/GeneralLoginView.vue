<template>
  <q-page class="home">
    <router-view class="left-window"/>
  </q-page>
</template>

<script>
export default {
  name: "GeneralLoginView",
};
</script>

<style scoped lang="scss">
@media (min-width: 640px){
  body {
    overflow-x: hidden;
    overflow-y: hidden;
  }
  .home {
    background: url("../../assets/home_background.jpg") no-repeat;
    background-size: 100% 100%;
    .left-window {
      position: sticky;
      left: 5%;
      top: 30%;
    }
  }
}
</style>
