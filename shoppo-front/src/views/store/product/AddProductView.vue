<template>
  <q-page class="home">
    <div v-if="$q.screen.width <= 640" class="containerMobile">
      <p style="white-space: pre-line">{{ msg }}</p>
    </div>
    <div class="center" v-if="$q.screen.width > 640">
      <EditProduct ref="children" title-card="Ajouter un produit" />
      <q-btn
        color="primary"
        class="addProduct"
        icon-right="send"
        :label="valid"
        @click="addProduct"
      />
    </div>
  </q-page>
</template>

<script>
import EditProduct from "@/components/store/product/EditProduct";
import axios from "axios";

export default {
  name: "AddProductView",
  components: { EditProduct },
  data() {
    return {
      valid: "Ajouter",
      nameRequired: "Le nom du produit est requis",
      priceRequired: "Le prix doit être renseigné et ne peut pas être négatif",
      stockRequired:
        "Le stock doit être renseigné, ne peut pas être négatif et doit être un entier",
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)"
    };
  },
  methods: {
    addProduct() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          var colormsg, msgRetour;
          axios
            .post("/rest/commercant-auth/commerce/product", {
              name: this.$refs.children.name,
              prix: this.$refs.children.prix,
              stock: this.$refs.children.stock,
              description: this.$refs.children.description,
              image: this.$refs.children.imageFile
            })
            .then(() => {
              msgRetour = "Le produit a été créé";
              colormsg = "green";
            })
            .catch(e => {
              console.log(e.response);
              if (e.response.data) {
                msgRetour = e.response.data;
              } else {
                msgRetour = "Une erreur est survenue";
              }
              colormsg = "red";
            })
            .finally(() => {
              this.notifyForm(msgRetour, colormsg);
            });
        }
      });
    },

    checkValidators() {
      const refChildren = this.$refs.children.$refs;
      return refChildren.addProductForm.validate().then(success => {
        if (!success) {
          if (!refChildren.nameInput.validate()) {
            this.notifyForm(this.nameRequired, "red");
          } else if (!refChildren.priceInput.validate()) {
            this.notifyForm(this.priceRequired, "red");
          } else {
            this.notifyForm(this.stockRequired, "red");
          }
        }
        return success;
      });
    },

    /**
     Notify if a field is not valid
     */
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
.home {
  display: flex;
  .center {
    width: 96%;
    height: 30%;
    position: sticky;
    top: 30%;
    margin-left: auto;
    margin-right: auto;
    .addProduct {
      width: 80%;
      margin-top: 2%;
      margin-left: 10%;
      margin-right: 10%;
    }
  }
  .containerMobile {
    background-color: darkorange;
    color: white;
    text-align: center;
    padding-top: 5%;
    font-size: 25px;
  }
}
</style>
