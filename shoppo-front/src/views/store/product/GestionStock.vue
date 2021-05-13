<template>
  <q-page class="home">
    <div v-if="$q.screen.width <= 640" class="containerMobile">
      <p style="white-space: pre-line">{{ msg }}</p>
    </div>
    <div class="center" v-if="$q.screen.width > 640">
      <p class="title">{{ title }}</p>
      <AfficheProduitSimple
        v-for="item in items"
        v-bind:id="item.id"
        v-bind:name="item.attribute.name"
        v-bind:prix="item.attribute.prix"
        v-bind:stock="item.attribute.stock"
        v-bind:key="item.attribute.id"
        v-bind:image="item.attribute.image"
        v-bind:description="item.attribute.description"
      />
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import AfficheProduitSimple from "@/components/store/product/AfficheProduitSimple";

export default {
  name: "GestionStock",
  components: { AfficheProduitSimple },
  data() {
    return {
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)",
      items: [],
      title: "Mes produits"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get("/rest/commercant-auth/commerce/product/get-products-commerce")
      .then(e => {
        this.items = e.data;
        this.$q.loading.hide();
      });
  },
  methods: {
    deleteProduct(id) {
      var colormsg, msgRetour;
      axios
        .post("/rest/commercant-auth/commerce/product/delete-product/", {
          id: id
        })
        .then(() => {
          this.items = this.items.filter(item => item.id !== id);
          msgRetour = "Le produit a été supprimé";
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

<style scoped>
.center {
  margin-left: auto;
  margin-right: auto;
  width: 98%;
}
.title {
  text-align: center;
  font-weight: bold;
  font-size: 35px;
  color: cadetblue;
}
</style>
