<template>
  <q-page class="home">
    <select-payment
      :open-payment-dialog="openPaymentDialog"
      :cancel-payment="cancelPayment"
      :valid-payment="validPayment"
      :disable-online="clientAccount < totalPrice"
    />
    <div v-if="!isMounted || (isMounted && cart.length > 0)">
      <CommerceCart
        v-for="commerce in cart"
        :key="commerce.id"
        :commerce="commerce"
        :delete-commerce-cart="deleteCommerceCart"
        class="commerceCart marginClass"
        ref="commerceCarts"
        :mount-end="calculTotalPrice"
        :advantage="
          advantage != undefined && advantage.commerce == commerce.id
            ? advantage
            : undefined
        "
        :modify-quantity="calculTotalPrice"
      />
      <q-btn
        color="primary"
        icon-right="check"
        :label="validCartLabel"
        class="submit marginClass"
        @click="validCart"
      />
    </div>
    <div v-if="isMounted && cart.length == 0" class="noCart">
      <p class="title">{{ noCartLabel }}</p>
    </div>
  </q-page>
</template>

<script>
import CommerceCart from "@/components/client/cart/CommerceCart";
import axios from "axios";
import router from "@/router";
import SelectPayment from "@/components/common/commande/SelectPayment";
export default {
  name: "ShoppingCartView",
  components: { SelectPayment, CommerceCart },
  data() {
    return {
      isMounted: false,
      validLabel: "Valider panier",
      cart: Array,
      noCartLabel: "Votre panier est vide.",
      totalPrice: "",
      advantage: undefined,
      openPaymentDialog: false,
      clientAccount: 0,
      validCartLabel: ""
    };
  },
  methods: {
    validCart() {
      this.openPaymentDialog = true;
    },
    deleteCommerceCart(commerceId) {
      this.cart = this.cart.filter(x => x.id != commerceId);
    },
    calculTotalPrice() {
      if (this.isMounted && this.$refs.commerceCarts != undefined) {
        var total = 0;
        this.$refs.commerceCarts.forEach(item => {
          total += Number.parseFloat(item.getTotalPrice);
        });
        this.totalPrice = total.toFixed(2);
        this.validCartLabel = this.validLabel + " - " + this.totalPrice + "€";
        return;
      }
      this.totalPrice = 0;
      this.validCartLabel = this.validLabel;
    },
    validPayment(onlinePayment) {
      console.log(onlinePayment);
      console.log(this.$refs.commerceCarts);
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      var colormsg, msgRetour;
      const typeCommand = onlinePayment ? "ONLINE" : "ONPLACE";
      axios
        .post("/rest/client-auth/client/panier/valider?type=" + typeCommand)
        .then(() => {
          msgRetour = "Le panier a bien été validé !";
          colormsg = "green";
          router.push("/client/commerce");
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
      this.openPaymentDialog = false;
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
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    var colormsg, msgRetour;
    axios
      .get("/rest/client-auth/client/solde")
      .then(response => {
        this.clientAccount = response.data;
      })
      .catch(e => {
        console.log(e);
        if (e.response.data) {
          msgRetour = e.response.data;
        } else {
          msgRetour = "Une erreur est survenu";
        }
        colormsg = "red";
        this.notifyForm(msgRetour, colormsg);
      });
    axios
      .get("/rest/client-auth/panier/getPanier")
      .then(response => {
        msgRetour = "Panier récupéré !";
        colormsg = "green";
        this.cart = response.data.panier;
        this.advantage = response.data.advantage;
      })
      .catch(e => {
        console.log(e);
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
  mounted() {
    this.isMounted = true;
  }
};
</script>

<style scoped lang="scss">
.marginClass {
  margin-left: 1%;
  margin-right: 1%;
  margin-top: 1%;
}
.submit {
  width: 98%;
}
.noCart {
  .title {
    text-align: center;
    font-weight: bold;
    font-size: 35px;
    color: var(--q-color-accent);
  }
}
</style>
