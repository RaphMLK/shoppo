<template>
  <q-page class="home">
    <p class="title">{{ title }}</p>
    <div class="center">
      <p class="subtitle" v-if="commandesATraiter.length > 0">
        Commandes à traiter
      </p>
      <p class="subtitle" v-if="commandesATraiter.length <= 0">
        Pas de commandes à traiter
      </p>
      <AfficheCommandeSimple
        v-for="item in commandesATraiter"
        v-bind:id="item.id"
        v-bind:date_creation="item.dateCreation"
        v-bind:date_reprise="item.dateRepriseClient"
        v-bind:traitee="item.traitee"
        v-bind:prix="item.prix"
        v-bind:key="item.id"
      />
    </div>
    <div class="center">
      <p class="subtitle" v-if="commandesTraitees.length > 0">
        Commandes traitées
      </p>
      <p class="subtitle" v-if="commandesTraitees.length <= 0">
        Pas de commandes traitées
      </p>
      <AfficheCommandeSimple
        v-for="item in commandesTraitees"
        v-bind:id="item.id"
        v-bind:date_creation="item.dateCreation"
        v-bind:date_reprise="item.dateRepriseClient"
        v-bind:traitee="item.traitee"
        v-bind:prix="item.prix"
        v-bind:key="item.id"
      />
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import AfficheCommandeSimple from "@/components/common/commande/AfficheCommandeSimple";

export default {
  name: "GestionCommandes",
  components: { AfficheCommandeSimple },
  data() {
    return {
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)",
      commandesTraitees: [],
      commandesATraiter: [],
      title: "Commandes du commerce"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get("/rest/commercant-auth/commerce/commande/get-commandes-commerce")
      .then(e => {
        var commandesTraiteesBis = [];
        var commandesATraiterBis = [];
        e.data.map(function(value) {
          value["dateCreation"] = value["dateCreation"].split("T")[0];
          if (value["dateRepriseClient"] !== null)
            value["dateRepriseClient"] = value["dateRepriseClient"].split(
              "T"
            )[0];
          if (value["traitee"] == true) {
            commandesTraiteesBis.push(value);
          } else {
            commandesATraiterBis.push(value);
          }
        });
        this.commandesTraitees = commandesTraiteesBis;
        this.commandesATraiter = commandesATraiterBis;
      })
      .catch(e => {
        var colormsg, msgRetour;
        console.log(e.response);
        if (e.response.data) {
          msgRetour = e.response.data;
        } else {
          msgRetour = "Une erreur est survenu";
        }
        colormsg = "red";
        this.notifyForm(msgRetour, colormsg);
      })
      .finally(
        this.$q.loading.hide()
      );
  },
  methods: {
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
.subtitle {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
  color: black;
}
</style>
