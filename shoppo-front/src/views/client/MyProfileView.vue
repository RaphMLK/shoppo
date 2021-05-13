<template>
  <q-page class="home">
    <p class="title q-mt-md">{{ title }}</p>
    <q-card class="plaqueCard q-mr-md q-ml-md">
      <q-card-section class="q-pb-none">
        <span class="text-h5">{{ plaqueTitle }}</span>
      </q-card-section>
      <q-card-section class="q-pb-none">
        <q-form class="editPlaque" ref="editPlaqueRef" greedy>
          <q-input
            v-model="plaque"
            :label="plaqueLabel"
            type="text"
            :rules="[val => (val != null && val.length == 9) || '']"
            ref="plaqueRef"
            lazy-rules="ondemand"
            mask="AA-###-AA"
          >
            <template v-slot:prepend>
              <q-icon name="directions_car" />
            </template>
          </q-input>
        </q-form>
      </q-card-section>
      <q-card-actions>
        <q-btn
          class="full-width"
          :label="validLabel"
          @click="validPlaque"
          icon-right="send"
          color="primary"
        />
      </q-card-actions>
    </q-card>
  </q-page>
</template>

<script>
export default {
  name: "MyProfileView",
  data() {
    return {
      title: "Éditer mon profil",
      plaqueTitle: "Ma plaque d'immatriculation",
      plaqueLabel: "Plaque d'immatriculation",
      plaque: undefined,
      validLabel: "Valider",
      plaqueRequired: "La plaque n'est pas valide."
    };
  },
  methods: {
    validPlaque() {
      this.$refs.editPlaqueRef.validate().then(success => {
        if (!success) {
          this.notifyForm(this.plaqueRequired, "red");
        } else {
          console.log(this.plaque);
        }
      });
    },
    /**
     Notify if a field is not valid
     */
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  .title {
    text-align: center;
    font-weight: bold;
    font-size: 35px;
    color: cadetblue;
  }
}
</style>
