<template>
  <q-page class="home" v-if="isMounted">
    <q-dialog v-model="dialogAdd" class="dialogADDProduct">
      <q-card>
        <q-toolbar>
          <q-avatar
            icon="add_shopping_cart"
            color="primary"
            text-color="white"
          />
          <q-toolbar-title>{{ addProductsLabel }}</q-toolbar-title>
          <q-btn flat round dense icon="close" v-close-popup />
        </q-toolbar>
        <q-card-actions align="right">
          <q-list bordered class="full-width">
            <CommandOption
              v-for="product in products"
              :key="product.id"
              :product="product"
              :selected-param="selectedProducts.includes(product)"
              :update-list="updateList"
            />
          </q-list>
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-dialog v-model="dialogAdvantage" class="dialogAddAdvantage">
      <q-card>
        <q-toolbar>
          <q-avatar
            icon="add_shopping_cart"
            color="primary"
            text-color="white"
          />
          <q-toolbar-title>{{ addProductsLabel }}</q-toolbar-title>
          <q-btn flat round dense icon="close" v-close-popup />
        </q-toolbar>
        <q-card-actions>
          <q-option-group
            v-model="selectedAdvantageId"
            :options="advantageOptions"
            color="primary"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <select-payment
      :open-payment-dialog="openDialogPayment"
      :valid-payment="validPayment"
      :cancel-payment="cancelPayment"
    />
    <p class="title">{{ productsLabel }}</p>
    <div class="actions-buttons" v-if="isVFP">
      <q-btn
        color="primary"
        class="select-product-button"
        icon-right="add"
        :label="addProductsButtonLabel"
        @click="openDialog"
      />
      <q-btn
        color="primary"
        class="select-advantage-button"
        icon-right="card_giftcard"
        @click="dialogAdvantage = true"
      />
    </div>
    <q-btn
      color="primary"
      class="valid-button"
      icon-right="add"
      :label="addProductsButtonLabel"
      @click="openDialog"
      v-if="!isVFP"
    />
    <ArticleCommand
      v-for="product in selectedProducts"
      :key="product.id"
      :product="product"
      class="q-mt-md"
      :delete-product="deleteProduct"
      ref="articles"
    />
    <AdvantageCommand
      :advantage="getSelectedAdvantage"
      :delete-advantage="deleteAdvantage"
      v-if="selectedAdvantageId != null"
      class="q-mt-md"
    />
    <q-btn
      color="primary"
      icon-right="send"
      :label="validLabel"
      class="valid-button q-mt-md"
      v-if="selectedProducts.length > 0"
      @click="validCommand"
    />
  </q-page>
</template>

<script>
import axios from "axios";
import ArticleCommand from "@/components/store/product/ArticleCommand";
import CommandOption from "@/components/store/CommandOption";
import router from "@/router";
import SelectPayment from "@/components/common/commande/SelectPayment";
import AdvantageCommand from "@/components/store/AdvantageCommand";

export default {
  name: "CreateCommandView",
  components: {
    AdvantageCommand,
    SelectPayment,
    CommandOption,
    ArticleCommand
  },
  data() {
    return {
      qrCodeToken: this.$route.params.qrCodeToken,
      isMounted: false,
      dialogAdd: false,
      products: Array,
      selectedProducts: [],
      productsLabel: "Produits proposés",
      addProductsLabel: "Liste des produits à ajouter",
      addProductsButtonLabel: "Ajouter des produits",
      validLabel: "Valider",
      openDialogPayment: false,
      dialogAdvantage: false,
      advantages: [],
      selectedAdvantageId: null,
      advantageOptions: [],
      isVFP: false
    };
  },
  methods: {
    openDialog() {
      this.dialogAdd = true;
    },
    updateList(product, value) {
      if (value) {
        this.selectedProducts.push(product);
      } else {
        this.deleteProduct(product);
      }
    },
    deleteProduct(product) {
      this.selectedProducts = this.selectedProducts.filter(
        x => x.id != product.id
      );
    },
    validCommand() {
      this.openDialogPayment = true;
    },
    validPayment(onlinePayment) {
      console.log(onlinePayment);
      let command = [];
      for (const component in this.$refs.articles) {
        command.push({
          id: this.$refs.articles[component].product.id,
          quantity: this.$refs.articles[component].quantity
        });
      }
      var colormsg, msgRetour;
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      console.log({
        client: this.qrCodeToken,
        command: command,
        onlinePayment: onlinePayment,
        advantage: this.selectedAdvantageId
      });
      axios
        .post("/rest/commercant-auth/commandeByCommerce", {
          client: this.qrCodeToken,
          command: command,
          onlinePayment: onlinePayment,
          advantage: this.selectedAdvantageId
        })
        .then(() => {
          msgRetour = "La commande a bien été créée !";
          colormsg = "green";
          router.push("/commerce/commandes");
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
    cancelPayment() {
      this.openDialogPayment = false;
    },
    deleteAdvantage() {
      this.selectedAdvantageId = null;
    },
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    }
  },
  computed: {
    getSelectedAdvantage() {
      if (this.selectedAdvantageId == null) return null;
      return this.advantages.filter(x => x.id == this.selectedAdvantageId)[0];
    }
  },
  beforeMount() {
    if (this.qrCodeToken == undefined) {
      router.push("/commerce/command/qrcode");
    }
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get("/rest/commercant-auth/commerce/product/get-products-commerce")
      .then(e => {
        this.products = e.data;
        axios
          .get("/rest/commercant-auth/manage-vfp/get-vfp")
          .then(e => {
            this.advantages = e.data;
            this.advantages.forEach(ad => {
              this.advantageOptions.push({ label: ad.libelle, value: ad.id });
            });
            axios
              .get(
                "/rest/commercant-auth/get-client-by-qrcode?qrCode=" +
                  this.qrCodeToken
              )
              .then(e => {
                this.isVFP = e.data.statusVFP;
                this.$q.loading.hide();
                this.isMounted = true;
              })
              .catch(e => {
                var msgRetour;
                console.log(e.response);
                if (e.response.data) {
                  msgRetour = e.response.data;
                } else {
                  msgRetour = "Une erreur est survenue";
                }
                this.notifyForm(msgRetour, "red");
              });
          })
          .catch(e => {
            var msgRetour;
            console.log(e.response);
            if (e.response.data) {
              msgRetour = e.response.data;
            } else {
              msgRetour = "Une erreur est survenue";
            }
            this.notifyForm(msgRetour, "red");
          });
      })
      .catch(e => {
        var msgRetour;
        console.log(e.response);
        if (e.response.data) {
          msgRetour = e.response.data;
        } else {
          msgRetour = "Une erreur est survenue";
        }
        this.notifyForm(msgRetour, "red");
      });
  }
};
</script>

<style scoped lang="scss">
.title {
  text-align: center;
  font-weight: bold;
  font-size: 35px;
  color: var(--q-color-accent);
}
.actions-buttons {
  .select-product-button {
    margin-left: 1%;
    margin-right: 1%;
    width: 74%;
  }
  .select-advantage-button {
    margin-right: 1%;
    width: 23%;
  }
}
.valid-button {
  margin-left: 1%;
  margin-right: 1%;
  width: 98%;
}
</style>
