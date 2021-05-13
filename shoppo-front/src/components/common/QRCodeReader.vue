<template>
  <div class="row justify-center q-pt-lg">
    <div class="col-12 text-center">
      <span class="text-subtitle2 text-grey-9">
        {{ textInfo }}
      </span>
      <q-btn
        color="blue-grey-10"
        rounded
        icon="camera_alt"
        :label="readQr"
        class="full-width"
        size="lg"
        @click="turnCameraOn()"
        v-show="!showCamera"
      />
      <div v-if="showCamera">
        <qrcode-stream :camera="camera" @decode="onDecode"> </qrcode-stream>
      </div>
    </div>
  </div>
</template>

<script>
import { QrcodeStream } from "vue-qrcode-reader";
export default {
  name: "QRCodeReader",
  components: { QrcodeStream },
  props: {
    identify: Function
  },
  data() {
    return {
      isValid: undefined,
      camera: "auto",
      result: null,
      showCamera: false,
      readQr: "Lire le QR Code"
    };
  },
  computed: {
    textInfo() {
      return this.showCamera
        ? "Placez le QR code sur la caméra"
        : "Ouvrez la caméra via le bouton";
    }
  },
  methods: {
    async onDecode(content) {
      this.result = content;
      this.turnCameraOff();
      this.identify(content);
    },
    turnCameraOn() {
      this.camera = "auto";
      this.showCamera = true;
    },
    turnCameraOff() {
      this.camera = "off";
      this.showCamera = false;
    }
  }
};
</script>

<style scoped></style>
