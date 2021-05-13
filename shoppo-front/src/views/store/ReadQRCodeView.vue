<template>
  <q-page class="home">
    <p>Résultat: {{ result }}</p>
    <QRCodeReader :identify="identify" />
  </q-page>
</template>

<style></style>

<script>
import QRCodeReader from "@/components/common/QRCodeReader";
export default {
  name: "PageIndex",
  components: { QRCodeReader },
  data() {
    return {
      result: undefined
    };
  },
  methods: {
    identify(value) {
      this.result = value;
    }
  }
};
</script>

<style scoped></style>
