<template>
  <q-card class="cardCommande">
    <div class="infoCommande">
      <p class="reference">Référence : {{ id }}</p>
      <div class="traitee">
        <p class="descriptionProduct" v-if="traitee == true">
          <q-icon name="check_circle_outline" color="orange" />
        </p>
        <p class="traitee" v-if="traitee == false">
          <q-icon name="cancel" color="orange" />
        </p>
      </div>
    </div>
    <div class="infoCommande">
      <p class="info">
        Date de commande : {{ date_creation }}
        <span v-if="date_reprise"> à récupérer le : {{ date_reprise }}</span>
      </p>
      <p class="option">
        <q-icon name="search" color="orange" @click="navigateDetailCommande" />
      </p>
      <p class="info">Prix : {{ prix }}€</p>
    </div>
  </q-card>
</template>

<script>
export default {
  name: "AfficheCommandeSimple",
  props: {
    id: Number,
    date_creation: String,
    date_reprise: String,
    traitee: Boolean,
    prix: Number
  },
  data() {
    return {
      stockComponent: this.stock,
      deleteShow: false,
      titleDialog: "Supprimer ce produit",
      cancelLabel: "Annuler",
      validLabel: "Confirmer"
    };
  },
  methods: {
    navigateDetailCommande() {
      this.$router.push({
        path: "commandes/detail-commande",
        query: { idCommande: this.id }
      });
    },
    notifyForm(msg, colormsg) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: colormsg,
        actions: [
          {
            label: "Fermer",
            color: "white",
            handler: () => {}
          }
        ]
      });
    }
  }
};
</script>

<style scoped lang="scss">
.window {
  .title {
    font-size: 2em;
    text-align: center;
    padding-bottom: 0;
  }
}

.cardCommande {
  margin-top: 0.5em;
  height: 100%;
  padding-bottom: 0.2em;

  .infoCommande {
    width: 100%;
    margin: 0.5em 0px 0px 0.5em;
    padding-right: 1em;

    .reference {
      font-weight: bold;
      font-size: 25px;
      display: inline-block;
    }
    .info {
      font-size: 18px;
    }

    .traitee {
      position: relative;
      float: right;
      font-weight: bold;
      font-size: 25px;
    }

    .option {
      font-weight: bold;
      font-size: 25px;
      position: relative;
      float: right;
    }
  }
}
</style>
