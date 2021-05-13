<template>
  <q-page class="home">
    <div v-if="$q.screen.width <= 640" class="containerMobile">
      <p style="white-space: pre-line">{{ msg }}</p>
    </div>
    <div class="center" v-if="$q.screen.width > 640">
      <p class="title">{{ title }}</p>
      <q-btn
        color="primary"
        icon="add_circle_outline"
        class="full-width q-mb-md"
        label="Ajouter des vfp"
        @click="navigateAddVfpProducts"
      />
      <div>
        <AfficheVfpUnique
          v-for="item in items"
          v-bind:id="item.id"
          v-bind:name="item.product.name"
          v-bind:key="item.id"
          v-bind:isAlreadyAdded="true"
          v-bind:description="item.libelle"
        />
        <p class="info" v-if="items.length == 0">{{ noVfp }}</p>
      </div>
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import AfficheVfpUnique from "../../../components/store/AfficheVfpUnique.vue";

export default {
  name: "GestionVfp",
  components: { AfficheVfpUnique },
  data() {
    return {
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)",
      items: [],
      title: "Produits VFP",
      noVfp: "Pas de VFP"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    this.getVfp();
  },
  methods: {
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    },
    navigateAddVfpProducts() {
      this.$router.push({
        path: "/commerce/vfp/addvfp"
      });
    },
    removeVfp(id) {
      var msgRetour, colormsg;
      axios
        .post("/rest/commercant-auth/manage-vfp/delete-vfp?idVfp=" + id)
        .then(() => {
          this.getVfp();
          msgRetour = "Le vfp a été retiré";
          colormsg = "green";
        })
        .catch(e => {
          console.log(e.response);
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenu";
          }

          return false;
        })
        .finally(() => {
          this.$q.loading.hide();
          this.notifyForm(msgRetour, colormsg);
        });
    },
    getVfp() {
      axios
        .get("/rest/commercant-auth/manage-vfp/get-vfp")
        .then(e => {
          this.items = e.data;
        })
        .catch(error => {
          console.log(error);
        })
        .finally(this.$q.loading.hide());
    }
  }
};
</script>

<style scoped>
.center {
  margin-left: auto;
  margin-right: auto;
  width: 98%;
}
.title {
  text-align: center;
  font-weight: bold;
  font-size: 35px;
  color: cadetblue;
}
.info {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
  color: cadetblue;
}
</style>
