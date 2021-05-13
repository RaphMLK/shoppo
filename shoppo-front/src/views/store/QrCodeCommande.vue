<template>
  <q-page class="home">
    <QRCodeReader :identify="identify" />
    <q-btn
      color="primary"
      icon-right="send"
      label="Simuler QR Code"
      class="button q-mt-md"
      @click="identify('df5ae280-6fe2-4ffd-9b3c-ab81511180bd')"
    />
  </q-page>
</template>

<script>
import QRCodeReader from "@/components/common/QRCodeReader";
export default {
  name: "QrCodeCommande",
  components: { QRCodeReader },
  methods: {
    identify(token) {
      this.$router.push({
        name: "createCommand",
        params: { qrCodeToken: token }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.button {
  width: 98%;
  margin-left: 1%;
  margin-right: 1%;
}
</style>
