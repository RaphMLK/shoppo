<template>
  <q-card class="card" @click="goToCommercePage">
    <q-card-section class="title text-h5">{{
      commerce.enseigne
    }}</q-card-section>
    <img src="@/assets/cropped-boulangerie.png" class="imgStore" />
    <p class="text-body2 q-mt-sm q-ml-sm" v-if="commerce.description != ''">
      {{ commerce.description }}
    </p>
    <p
      class="text-body2 q-ml-sm q-mt-sm noDescription"
      v-if="commerce.description == ''"
    >
      {{ noDescription }}
    </p>
  </q-card>
</template>

<script>
export default {
  name: "StoreCard",
  props: {
    commerce: Object
  },
  data() {
    return {
      noDescription: "Pas de description"
    };
  },
  methods: {
    goToCommercePage() {
      this.$router.push({
        path: "/client/commerce/view",
        query: { siretCodeParam: this.commerce.siretCode }
      });
    }
  }
};
</script>

<style scoped lang="scss">
.card {
  justify-content: center;
  .imgStore {
    height: 150px;
  }
  .description {
    margin-top: 8px;
  }
  .noDescription {
    font-style: italic;
  }
}
</style>
