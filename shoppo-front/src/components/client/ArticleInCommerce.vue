<template>
  <div>
    <q-dialog v-model="addProduct" class="dialogAddProduct">
      <q-card>
        <q-card-section class="row items-center q-pb-none">
          <div class="text-h6">{{ titleDialog }}</div>
          <q-space />
          <q-btn icon="close" flat round dense v-close-popup />
        </q-card-section>
        <q-card-section>
          <ChooseQuantity
            :max="stock"
            :stock-param="1"
            :view-button="false"
            ref="chooseQuantityForm"
          />
          <q-btn
            color="primary"
            class="submit"
            icon-right="send"
            :label="validLabel"
            @click="validProduct"
          />
        </q-card-section>
      </q-card>
    </q-dialog>
    <q-card class="articleCard">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="imageProduct"
      />
      <div class="infoProduct">
        <p class="nameProduct">{{ name }} - {{ prix }}€</p>
        <p class="descriptionProduct" v-if="description != 'null'">
          {{ description }}
        </p>
        <p class="noDescription" v-if="description == 'null'">
          Pas de description
        </p>
      </div>
      <div class="actions">
        <q-btn
          round
          color="primary"
          icon="add_shopping_cart"
          class="actionIcons"
          @click="openDialog"
        />
      </div>
    </q-card>
  </div>
</template>

<script>
import ChooseQuantity from "@/components/common/ChooseQuantity";
import axios from "axios";

export default {
  name: "ArticleInCommerce",
  components: { ChooseQuantity },
  props: {
    id: Number,
    name: String,
    prix: Number,
    stock: Number,
    description: String
  },
  data() {
    return {
      addProduct: false,
      titleDialog: "Combien ?",
      validLabel: "Valider"
    };
  },
  methods: {
    openDialog() {
      this.addProduct = true;
    },
    validProduct() {
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      var msgRetour, colormsg;
      axios
        .post(
          "/rest/client-auth/panier/updatePanier?idProduit=" +
            this.id +
            "&quantite=" +
            this.$refs.chooseQuantityForm.stock
        )
        .then(() => {
          msgRetour = "Le produit a bien été ajouté !";
          colormsg = "green";
          this.addProduct = false;
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
.submit {
  width: 100%;
}

.articleCard {
  margin-top: 10px;
  margin-left: 1%;
  margin-right: 1%;
  height: 100px;
  display: flex;
  .imageProduct {
    width: 20%;
    height: 100%;
    object-fit: contain;
    float: left;
  }
  .infoProduct {
    width: 60%;
    .nameProduct {
      font-weight: bold;
      font-size: 25px;
    }
    .noDescription {
      font-style: italic;
    }
  }
  .actions {
    position: absolute;
    right: 1%;
    top: 25%;
    .actionIcons {
      height: 50%;
      margin-left: 10px;
    }
  }
}

@media (max-width: 640px) {
  .articleCard {
    .infoProduct {
      .nameProduct {
        font-weight: bold;
        font-size: 15px;
      }
      .noDescription {
        font-style: italic;
      }
    }
  }
}
</style>
