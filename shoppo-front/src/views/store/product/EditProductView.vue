<template>
  <q-page class="home">
    <div class="center">
      <EditProduct
        ref="children"
        title-card="Éditer un produit"
        :nameParam="produit.name"
        :prixParam="produit.prix"
        :stockParam="produit.stock"
        :descriptionParam="produit.description"
        :imageParam="produit.image"
      />
      <q-btn
        color="primary"
        class="editProduct"
        icon-right="send"
        :label="valid"
        @click="editProduct"
      />
    </div>
  </q-page>
</template>

<script>
import EditProduct from "@/components/store/product/EditProduct";
import axios from "axios";

export default {
  name: "editProductView",
  components: { EditProduct },
  produit: "",
  data() {
    return {
      valid: "Modifier",
      nameRequired: "Le nom du produit est requis",
      priceRequired: "Le prix doit être renseigné et ne peut pas être négatif",
      stockRequired:
        "Le stock doit être renseigné, ne peut pas être négatif et doit être un entier",
        produit: undefined
    };
  },
  methods: {
    getProduct() {
      var colormsg, msgRetour;
            axios
                .post("/rest/commercant-auth/commerce/product/get-product", {
                id: this.$route.query.id,
                })
                .then((response) => {
                msgRetour = "Le produit a été récupéré";
                colormsg = "green";
                console.log(response.data);
                this.produit = response.data;
                return response;
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
    editProduct() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          var colormsg, msgRetour;
          axios
            .post("/rest/commercant-auth/commerce/product/edit-product", {
              id: this.produit.id,
              name: this.$refs.children.name,
              prix: this.$refs.children.prix,
              stock: this.$refs.children.stock,
              description: this.$refs.children.description,
              image: this.$refs.children.imageFile
            })
            .then(() => {
              msgRetour = "Le produit a été modifié";
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
  },
  beforeMount(){
    this.getProduct()
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
    .editProduct {
      width: 80%;
      margin-top: 2%;
      margin-left: 10%;
      margin-right: 10%;
    }
  }
}
</style>
