<template>
  <q-page class="full-height">
    <HereMap :center="center" :commandes="commandes" />
    <CommandeToPickUp
      v-for="commande in commandes"
      :key="commande.id"
      :commande="commande"
    />
  </q-page>
</template>

<script>
import HereMap from "@/components/common/HereMap";
import CommandeToPickUp from "@/components/client/commande/CommandeToPickUp";
export default {
  name: "app",
  components: {
    CommandeToPickUp,
    HereMap
  },
  data() {
    return {
      center: {
        lat: 50.63737,
        lng: 3.06386
      },
      commandes: this.$route.params.commandes
    };
  },
  mounted() {
    if (this.commandes == undefined || this.commandes.length == 0) {
      this.$router.push("/client/pickup");
    }
  }
};
</script>

<style scoped lang="scss">
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
}
</style>
