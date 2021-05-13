<template>
  <q-card class="card" v-if="commerceData.products.length > 0">
    <q-card-section class="title text-h5">{{
      commerce.enseigne
    }}</q-card-section>
    <q-card-section>
      <q-list>
        <ArticleCommerceCart
          v-for="product in commerceData.products"
          :key="product.id"
          :product-param="product"
          :delete-product="deleteProduct"
          :update-product="modifyQuantity"
          ref="articleCommerceCarts"
        />
        <AdvantageVFPCart
          :advantage="advantage"
          :delete-advantage="deleteAdvantage"
          v-if="advantage != undefined"
        />
      </q-list>
      <p class="price text-h6">Total: {{ getTotalPrice }}€</p>
    </q-card-section>
  </q-card>
</template>

<script>
import ArticleCommerceCart from "@/components/client/cart/ArticleCommerceCart";
import axios from "axios";
import AdvantageVFPCart from "@/components/client/cart/AdvantageVFPCart";
export default {
  name: "CommerceCart",
  components: { AdvantageVFPCart, ArticleCommerceCart },
  props: {
    commerce: Object,
    deleteCommerceCart: Function,
    modifyQuantity: Function,
    mountEnd: Function,
    advantage: Object
  },
  data() {
    return {
      isMounted: false,
      commerceData: this.commerce
    };
  },
  methods: {
    deleteProduct(product) {
      var colormsg, msgRetour;
      axios
        .post(
          "/rest/client-auth/panier/updatePanier?idProduit=" +
            product.id +
            "&quantite=0"
        )
        .then(() => {
          msgRetour = "Le produit a été retiré";
          colormsg = "green";
          this.commerceData.products = this.commerceData.products.filter(
            x => x.id != product.id
          );
          if (this.commerceData.products == 0) {
            this.deleteCommerceCart(this.commerceData.id);
          }
        })
        .catch(e => {
          console.log(e.response);
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenu";
          }
          colormsg = "red";
        })
        .finally(() => {
          this.notifyForm(msgRetour, colormsg);
        });
    },
    deleteAdvantage() {
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      var msgRetour, colormsg;
      axios
        .post("/rest/client-auth/panier/delete-vfp?idVfp=" + this.advantage.id)
        .then(() => {
          msgRetour = "L'avantage a été supprimé";
          colormsg = "green";
          this.advantage = undefined;
        })
        .catch(e => {
          console.log(e.response);
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenu";
          }
          colormsg = "red";
        })
        .finally(() => {
          this.notifyForm(msgRetour, colormsg);
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
  computed: {
    getTotalPrice() {
      if (this.isMounted) {
        var total = 0;
        this.$refs.articleCommerceCarts.forEach(item => {
          total += Number.parseFloat(item.getPrice);
        });
        return Number.parseFloat(total).toFixed(2);
      }
      return "";
    }
  },
  mounted() {
    this.isMounted = true;
    this.mountEnd();
  }
};
</script>

<style scoped lang="scss">
.card {
  padding-bottom: 20px;
  .price {
    float: right;
    margin-right: 14px;
  }
}
</style>
