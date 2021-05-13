<template>
  <q-page class="home">
    <AddStore class="addStore" />
  </q-page>
</template>

<script>
import AddStore from "@/components/admin/AddStore";
export default {
  name: "AddStoreView",
  components: { AddStore },
  props: {
    msg: String
  },
  data() {
    return {};
  }
};
</script>

<style scoped lang="scss">
.addStore {
  width: 70%;
  height: 30%;
  position: sticky;
  top: 30%;
  margin-left: 15%;
}
</style>
