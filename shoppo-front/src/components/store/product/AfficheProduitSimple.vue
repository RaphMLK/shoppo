<template>
  <q-card class="cardProduct">
    <q-dialog v-model="deleteShow" class="dialogDeleteProduct">
      <q-card>
        <q-card-section class="row items-center q-pb-none">
          <q-avatar icon="clear" color="primary" text-color="white" />
          <span class="q-ml-sm"
            >Voulez-vous vraiment supprimer {{ name }} ?</span
          >
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat :label="cancelLabel" color="primary" v-close-popup />
          <q-btn
            flat
            :label="validLabel"
            color="primary"
            v-close-popup
            @click="deleteProduct"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <img
      v-bind:src="'data:image/jpeg;base64,' + image"
      v-if="image != null"
      class="imageProduct"
    />
    <img
      src="https://upload.wikimedia.org/wikipedia/commons/thumb/e/e6/Pas_d%27image_disponible.svg/300px-Pas_d%27image_disponible.svg.png"
      v-if="image == ''"
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
    <ChooseQuantity
      :stockParam="stock"
      ref="quantityStock"
      class="quantityStock"
      :modifyQuantity="modifyQuantity"
    />
    <div class="actions">
      <q-btn
        round
        color="primary"
        icon="edit"
        class="actionIcons"
        @click="navigateEditProduct"
      />
      <q-btn
        round
        color="primary"
        icon="clear"
        class="actionIcons"
        @click="deleteShow = true"
      />
    </div>
  </q-card>
</template>

<script>
import axios from "axios";
import ChooseQuantity from "@/components/common/ChooseQuantity";

export default {
  name: "AfficheProduitSimple",
  components: { ChooseQuantity },
  props: {
    id: Number,
    name: String,
    prix: Number,
    stock: Number,
    image: String,
    description: String
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
    navigateEditProduct() {
      this.$router.push({
        path: "/commerce/product/edit",
        query: { id: this.id }
      });
    },
    deleteProduct() {
      this.$parent.$parent.deleteProduct(this.id);
    },
    modifyQuantity: async function(newStock) {
      let retour = false;
      let colormsg, msgRetour;
      await axios
        .post(
          "/rest/commercant-auth/commerce/product/update-quantity/" +
            this.id +
            "?quantity=" +
            newStock
        )
        .then(() => {
          retour = true;
          msgRetour = "Stock mis à jour !";
          colormsg = "green";
        })
        .catch(() => {
          msgRetour = "Une erreur est survenu";
          colormsg = "red";
        })
        .finally(() => {
          this.notifyForm(msgRetour, colormsg);
        });
      console.log(retour);
      return retour;
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

  .center {
    text-align: center;
  }
}

.cardProduct {
  margin-top: 10px;
  height: 100px;
  display: flex;

  .imageProduct {
    width: 20%;
    height: 100%;
    object-fit: contain;
    float: left;
  }

  .infoProduct {
    width: 45%;

    .nameProduct {
      font-weight: bold;
      font-size: 25px;
    }

    .noDescription {
      font-style: italic;
    }
  }

  .quantityStock {
    width: 20%;
  }

  .actions {
    margin-top: auto;
    margin-bottom: auto;

    .actionIcons {
      height: 50%;
      margin-left: 10px;
    }
  }
}
</style>
