<template>
  <q-page class="home">
    <q-card class="card" v-if="isMounted">
      <q-card-section class="title">{{ title }}</q-card-section>
      <q-card-section class="qrCodeSection">
        <qrcode-vue
          :value="qrCodeValue"
          level="H"
          class="qrCode q-ml-auto q-mr-auto"
          :size="screenWidth / 3"
        />
      </q-card-section>
    </q-card>
  </q-page>
</template>

<script>
import QrcodeVue from "qrcode.vue";
import { mapActions } from "vuex";

export default {
  name: "QrCodeCardView",
  components: {
    QrcodeVue
  },
  data() {
    return {
      isMounted: false,
      qrCodeValue: String,
      title: "Mon QR Code",
      screenWidth: this.$q.screen.height
    };
  },
  methods: {
    ...mapActions(["getUserStore"])
  },
  beforeMount() {
    this.getUserStore().then(user => {
      this.qrCodeValue = user.user.qrCode;
      this.isMounted = true;
    });
  }
};
</script>

<style scoped lang="scss">
.card {
  position: sticky;
  top: 25%;
  margin-right: auto;
  margin-left: auto;
  width: fit-content;
  .title {
    font-size: 1.5em;
    text-align: center;
  }
  .qrCode {
    width: fit-content;
  }
}
</style>
