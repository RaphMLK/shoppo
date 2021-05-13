<template>
  <q-page class="home">
    <p class="title">{{ title }}</p>
    <div class="center">
      <p class="subtitle" v-if="commandesATraiter.length > 0">
        Commandes non récupérées
      </p>
      <p class="subtitle" v-if="commandesATraiter.length <= 0">
        Pas de commandes non récupérées
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
        Commandes récupérées
      </p>
      <p class="subtitle" v-if="commandesTraitees.length <= 0">
        Pas de commandes récupérées
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
      title: "Commandes"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get("/rest/client-auth/client/get-commandes-client")
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
        this.$q.loading.hide();
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
      });
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

<style scoped lang="scss">
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
