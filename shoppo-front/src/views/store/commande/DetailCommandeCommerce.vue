<template>
  <q-page class="home">
    <div v-if="$q.screen.width <= 640" class="containerMobile">
      <p style="white-space: pre-line">{{ msg }}</p>
    </div>
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
    <div class="traiter">
      <p v-if="this.commande.traitee == false">
        <q-btn
          color="orange"
          class="submit"
          :label="traiterLabel"
          @click="traiter"
        />
      </p>
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
      traiterLabel: "Traiter"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get(
        "/rest/commercant-auth/commerce/commande/get-commande/" +
          this.$route.query.idCommande
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
      this.commande.traitee = true;
      let colormsg, msgRetour;
      axios
        .post(
          "/rest/commercant-auth/commerce/commande/update-commande-traitee",
          {
            id: this.commande.id
          }
        )
        .then(e => {
          msgRetour = e.data;
          colormsg = "green";
        })
        .catch(e => {
          console.log(e.response);
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenue";
          }
          colormsg = "red";
        })
        .finally(() => {
          this.notifyForm(msgRetour, colormsg);
        });
      console.log("traiter");
    }
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
