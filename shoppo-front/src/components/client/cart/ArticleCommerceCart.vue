<template>
  <div>
    <q-dialog v-model="deleteShow" class="dialogDeleteProduct">
      <q-card>
        <q-card-section class="row items-center q-pb-none">
          <q-avatar icon="clear" color="primary" text-color="white" />
          <span class="q-ml-sm">{{ getDeleteLabel }} </span>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat :label="cancelLabel" color="primary" v-close-popup />
          <q-btn
            flat
            :label="validLabel"
            color="primary"
            v-close-popup
            @click="validDeleteProduct"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-item class="item">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <p class="productName text-h6 q-mt-auto q-mb-auto">
        {{ product.name }} - {{ product.prix }}€
      </p>
      <div class="actions q-mt-auto q-mb-auto ">
        <ChooseQuantity
          :view-button="true"
          :max="product.stock"
          :stock-param="product.quantity"
          :modify-quantity="modifyQuantity"
          class="q-mt-auto q-mb-auto chooseQuantity"
          ref="chooseQuantity"
        />
        <div class="deleteButton q-mt-auto q-mb-auto">
          <q-btn
            round
            color="primary"
            icon="clear"
            class="actionIcons"
            @click="deleteShow = true"
          />
        </div>
      </div>
      <div class="totalPrice q-mt-auto q-mb-auto q-ml-md">
        <p class="text-h6 q-mt-auto q-mb-auto">{{ getPrice }}€</p>
      </div>
    </q-item>
    <q-separator spaced />
  </div>
</template>

<script>
import ChooseQuantity from "@/components/common/ChooseQuantity";
import axios from "axios";
export default {
  name: "ArticleCommerceCart",
  components: { ChooseQuantity },
  props: {
    productParam: Object,
    deleteProduct: Function,
    updateProduct: Function
  },
  data() {
    return {
      product: this.productParam,
      isMounted: false,
      deleteShow: false,
      titleDialog: "Supprimer ce produit",
      cancelLabel: "Annuler",
      validLabel: "Confirmer",
      deleteLabel: "Voulez-vous vraiment supprimer :"
    };
  },
  methods: {
    modifyQuantity(newValue) {
      return axios
        .post(
          "/rest/client-auth/panier/updatePanier?idProduit=" +
            this.product.id +
            "&quantite=" +
            newValue
        )
        .then(() => {
          this.updateProduct();
          return true;
        })
        .catch(e => {
          var msgRetour;
          console.log(e.response);
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenu";
          }
          this.notifyForm(msgRetour, "red");
          return false;
        });
    },
    validDeleteProduct() {
      this.deleteProduct(this.product);
      this.updateProduct();
      this.deleteShow = false;
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
    getPrice() {
      if (this.isMounted) {
        return Number.parseFloat(
          this.$refs.chooseQuantity.defaultStock * this.product.prix
        ).toFixed(2);
      }
      return "";
    },
    getDeleteLabel() {
      return this.deleteLabel + " " + this.product.name + " ?";
    }
  },
  mounted() {
    this.isMounted = true;
  }
};
</script>

<style scoped lang="scss">
.item {
  .actions {
    display: flex;
  }
}

@media (min-width: 640px) {
  .item {
    .productImage {
      width: 5%;
    }
    .productName {
      margin-left: 1%;
      width: 80%;
    }
  }
}

@media (max-width: 640px) {
  .item {
    padding-left: 0;
    display: block;
    .productImage {
      display: none;
      width: 0;
    }
    .productName {
      width: 75%;
    }
    .actions {
      .chooseQuantity {
        width: 90%;
      }
      .deleteButton {
        position: relative;
        margin-top: auto;
        margin-bottom: auto;
      }
    }
    .totalPrice {
      display: none;
    }
  }
}
</style>
