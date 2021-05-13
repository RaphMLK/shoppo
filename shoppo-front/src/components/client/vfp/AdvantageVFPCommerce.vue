<template>
  <div>
    <q-dialog v-model="selectAdvantage" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="check" color="primary" text-color="white" />
          <span class="q-ml-sm">{{ titleDialog }}</span>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat :label="cancelLabel" color="primary" v-close-popup />
          <q-btn
            flat
            :label="validLabel"
            color="primary"
            v-close-popup
            @click="addAdvantage"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-card class="articleCard">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="imageProduct"
      />
      <div class="infoProduct">
        <p class="nameProduct">{{ advantage.libelle }}</p>
        <p class="noDescription">
          {{ productLabel + advantage.product.name }}
        </p>
      </div>
      <div class="actions">
        <q-btn
          round
          color="primary"
          icon="add_shopping_cart"
          class="actionIcons"
          @click="selectAdvantage = true"
        />
      </div>
    </q-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "AdvantageVFPCommerce",
  props: {
    advantage: Object
  },
  data() {
    return {
      selectAdvantage: false,
      titleDialog: "Sélectionner cet avantage ?",
      cancelLabel: "Annuler",
      validLabel: "Valider",
      productLabel: "Produit concerné : "
    };
  },
  methods: {
    addAdvantage() {
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      var msgRetour, colormsg;
      axios
        .post("/rest/client-auth/panier/add-vfp?idVfp=" + this.advantage.id)
        .then(() => {
          msgRetour = "L'avantage a été sélectionné";
          colormsg = "green";
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
