<template>
  <q-page class="home">
    <div v-if="$q.screen.width <= 640" class="containerMobile">
      <p style="white-space: pre-line">{{ msg }}</p>
    </div>
    <div class="center" v-if="$q.screen.width > 640">
      <p class="title">{{ title }}</p>
      <q-btn
        color="primary"
        icon="list"
        class="full-width"
        label="Liste des vfp"
        @click="navigateListVfpProducts"
      />
      <AfficheVfpUnique
        v-for="item in items"
        v-bind:id="item.id"
        v-bind:name="item.attribute.name"
        v-bind:key="item.attribute.id"
        v-bind:image="item.attribute.image"
        v-bind:isAlreadyAdded="false"
        v-bind:descriptionVfp="''"
        v-bind:selected="false"
      />
      <p class="info" v-if="items.length == 0">{{ noProducts }}</p>
      <q-btn
        color="primary"
        icon="add_circle_outline"
        label="Ajouter"
        :disable="tailleVfp == 0"
        v-if="items.length != 0"
        @click="addProductsVfp"
        class="full-width q-mt-md"
      />
    </div>
  </q-page>
</template>

<script>
import axios from "axios";
import AfficheVfpUnique from "../../../components/store/AfficheVfpUnique.vue";

export default {
  name: "AddProductVfp",
  components: { AfficheVfpUnique },
  data() {
    return {
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)",
      items: [],
      title: "Ajouter produit VFP",
      vfpToAdd: new Map(),
      tailleVfp: 0,
      tabVfpToSend: [],
      noProducts: "Pas de produit disponible"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    this.getVfp();
  },
  methods: {
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    },
    addProductsVfp() {
      this.vfpToAdd.forEach(values => {
        this.tabVfpToSend.push(values);
      });
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      var colormsg, msgRetour;
      axios
        .post(
          "/rest/commercant-auth/manage-vfp/add-by-product",
          this.tabVfpToSend
        )
        .then(() => {
          this.getVfp();
          msgRetour = "Le vfp a été ajouté";
          colormsg = "green";
          this.navigateListVfpProducts();
        })
        .catch(e => {
          colormsg = "red";
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenue";
          }
        })
        .finally(() => {
          this.$q.loading.hide();
          this.tabVfpToSend = [];
          this.notifyForm(msgRetour, colormsg);
        });
    },
    deleteVfp(id) {
      this.vfpToAdd.delete(id);
      this.tailleVfp -= 1;
    },
    addVfp(id, libelle) {
      this.vfpToAdd.set(id, { id, libelle });
      this.tailleVfp += 1;
    },
    addOrDeleteMapVfp(id, libelle) {
      this.vfpToAdd.get(id) ? this.deleteVfp(id) : this.addVfp(id, libelle);
    },
    getVfp() {
      axios
        .get("/rest/commercant-auth/commerce/product/get-products-not-vfp")
        .then(e => {
          this.items = e.data;
          this.$q.loading.hide();
          console.log("this.items");
          console.log(this.items);
        })
        .catch(e => {
          var msgRetour;
          var colormsg = "red";
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenue";
          }
          this.notifyForm(msgRetour, colormsg);
        })
        .finally(this.$q.loading.hide());
    },
    navigateListVfpProducts() {
      this.$router.push({
        path: "/commerce/vfp"
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
.info {
  text-align: center;
  font-weight: bold;
  font-size: 25px;
  color: cadetblue;
}
</style>
