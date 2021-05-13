<template>
  <q-dialog v-model="openPaymentDialog" persistent>
    <q-card class="card">
      <q-card-section class="q-pb-none">
        <p class="text-h6 q-mb-none">{{ title }}</p>
      </q-card-section>
      <q-card-section class="radios">
        <q-radio v-model="selectedPayment" :val="false" :label="physical" />
        <q-radio
          v-model="selectedPayment"
          :val="true"
          :label="accountLabel"
          :disable="disableOnline"
        />
      </q-card-section>
      <q-card-actions align="right">
        <q-btn flat :label="cancel" @click="cancelPayment" color="primary" />
        <q-btn
          flat
          :label="valid"
          @click="validPayment(selectedPayment)"
          color="primary"
        />
      </q-card-actions>
    </q-card>
  </q-dialog>
</template>

<script>
export default {
  name: "SelectPayment",
  props: {
    openPaymentDialog: Boolean,
    cancelPayment: Function,
    validPayment: Function,
    disableOnline: Boolean
  },
  data() {
    return {
      title: "Sélectionnez le moyen de paiement",
      account: "Carte de fidélité",
      physical: "Paiment physique",
      selectedPayment: false,
      cancel: "Annuler",
      valid: "Valider",
      invalidSolde: " - solde insuffisant"
    };
  },
  computed: {
    accountLabel() {
      let label = this.account;
      if (this.disableOnline) {
        label += this.invalidSolde;
      }
      return label;
    }
  }
};
</script>

<style scoped lang="scss">
.card {
  .radios {
    display: grid;
  }
}
</style>
