<template>
  <q-page class="home" v-if="isMounted">
    <div>
      <p class="title">{{ title }}</p>
    </div>
    <div class="center">
      <AfficheDetailCommande
        v-bind:id="commande.id"
        v-bind:date_creation="commande.dateCreation"
        v-bind:date_reprise="commande.dateRepriseClient"
        v-bind:traitee="commande.traitee"
        v-bind:prix="commande.prix"
        v-bind:produits="commande.products"
        :vfp="commande.productVfpDTO"
        v-bind:key="commande.id"
      />
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import AfficheDetailCommande from "@/components/common/commande/AfficheDetailCommande";

export default {
  name: "DetailCommande",
  components: { AfficheDetailCommande },
  produit: "",
  data() {
    return {
      commande: Object,
      title: "Commande",
      traiterLabel: "Traiter",
      isMounted: false
    };
  },
  beforeMount() {
    console.log(this.$route.query.idCommande);
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get(
        "/rest/client-auth/client/get-commande/" + this.$route.query.idCommande
      )
      .then(e => {
        this.commande = e.data;
        this.commande["dateCreation"] = this.commande["dateCreation"].split(
          "T"
        )[0];
        if (this.commande["dateRepriseClient"])
          this.commande["dateRepriseClient"] = this.commande[
            "dateRepriseClient"
          ].split("T")[0];
        this.$q.loading.hide();
      });
  },
  methods: {
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
    },
    traiter() {
      console.log("traiter");
    }
  },
  mounted() {
    this.isMounted = true;
  }
};
</script>

<style scoped lang="scss">
.home {
  display: block;
  .center {
    margin-left: auto;
    margin-right: auto;
    width: 98%;
    display: block;
  }
  .title {
    text-align: center;
    font-weight: bold;
    font-size: 35px;
    color: cadetblue;
    margin: 1em auto 1em auto;
    display: block;
    width: 100%;
  }
  .traiter {
    width: 80%;
    margin-top: 2%;
    margin-left: 10%;
    margin-right: 10%;
    text-align: center;
  }
}
</style>
