<template>
  <q-card class="cardCommande" v-if="isMounted">
    <div class="infoCommande">
      <p class="reference">Référence : {{ id }}</p>
      <p class="info">
        Date de commande : {{ date_creation }}
        <span v-if="date_reprise">à récupérer le : {{ date_reprise }}</span>
      </p>
      <p class="info">Prix : {{ prix }}€</p>
      <ProductInDetailCommand
        v-for="product in produits"
        :key="product.id"
        :product="product"
      />
      <AdvantageInDetailCommand :advantage="vfp" v-if="vfp != null" />
    </div>
  </q-card>
</template>

<script>
import ProductInDetailCommand from "@/components/common/commande/ProductInDetailCommand";
import AdvantageInDetailCommand from "@/components/common/commande/AdvantageInDetailCommand";
export default {
  name: "DetailCommande",
  components: { AdvantageInDetailCommand, ProductInDetailCommand },
  props: {
    id: Number,
    date_creation: String,
    date_reprise: String,
    traitee: Boolean,
    prix: Number,
    produits: Array,
    vfp: Object
  },
  data() {
    return {
      traiterLabel: "Traiter",
      isMounted: false
    };
  },
  methods: {
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
  },
  mounted() {
    console.log(this.vfp);
    this.isMounted = true;
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
  margin: 0.5em auto 0 auto;
  height: 100%;
  padding-bottom: 0.2em;
  width: 90%;
  display: block;

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
    .produit {
      width: 100%;
    }
    .infoProduit {
      display: inline-block;
      margin: 0 1em 0 1em;
    }
    .infoProduitPrix {
      display: inline-block;
      position: absolute;
      right: 1em;
      text-align: right;
    }
  }
}
</style>
