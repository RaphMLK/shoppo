<template>
  <q-page>
    <p class="title">{{ title }}</p>
    <CommandeToPickUp
      v-for="commande in commandes"
      :key="commande.id"
      :commande="commande"
      :select-command="selectCommand"
      :select-active="true"
    />
    <q-btn
      class="submit"
      :label="validLabel"
      color="primary"
      icon-right="check"
      @click="validCommands"
    />
  </q-page>
</template>

<script>
import axios from "axios";
import CommandeToPickUp from "@/components/client/commande/CommandeToPickUp";

export default {
  name: "PickUpCommandesView",
  components: { CommandeToPickUp },
  data() {
    return {
      title: "Mes commandes à récupérer",
      commandes: [],
      selectedCommands: [],
      validLabel: "Valider"
    };
  },
  methods: {
    selectCommand(selected, commandId) {
      if (selected) {
        this.selectedCommands.push(commandId);
      } else {
        this.selectedCommands = this.selectedCommands.filter(
          id => id != commandId
        );
      }
    },
    validCommands() {
      this.$router.push({
        name: "PickUpCommandesMap",
        params: {
          commandes: this.commandes.filter(commande =>
            this.selectedCommands.includes(commande.id)
          )
        }
      });
    },
    /**
     Notify if a field is not valid
     */
    notifyForm(msg, colormsg) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: colormsg,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    }
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get("/rest/client-auth/client/get-commandes-client")
      .then(response => {
        this.commandes = response.data.filter(commande => !commande.traitee);
      })
      .catch(e => {
        var colormsg, msgRetour;
        console.log(e);
        if (e.response && e.response.data) {
          msgRetour = e.response.data;
        } else {
          msgRetour = "Une erreur est survenu";
        }
        colormsg = "red";
        this.notifyForm(msgRetour, colormsg);
      })
      .finally(() => {
        this.$q.loading.hide();
      });
  }
};
</script>

<style scoped lang="scss">
.title {
  text-align: center;
  font-weight: bold;
  font-size: 35px;
  color: cadetblue;
}
.submit {
  width: 98%;
  margin-right: 1%;
  margin-left: 1%;
}
</style>
