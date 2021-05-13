<template>
  <div>
    <q-dialog v-model="deleteShow" class="dialogDeleteAdvantage">
      <q-card>
        <q-card-section class="row items-center q-pb-none">
          <q-avatar icon="clear" color="primary" text-color="white" />
          <span class="q-ml-sm">{{ deleteLabel }} </span>
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat :label="cancelLabel" color="primary" v-close-popup />
          <q-btn
            flat
            :label="validLabel"
            color="primary"
            v-close-popup
            @click="deleteAdvantage"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <q-item class="item">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <span class="productName text-h6 q-mt-auto q-mb-auto">
        <q-icon
          color="primary"
          name="card_giftcard"
          size="50px"
          class="giftIcon"
        />
        {{ getAdvantageLabel }}
      </span>
      <div class="q-mt-auto q-mb-auto deleteButton">
        <q-btn
          round
          color="primary"
          icon="clear"
          class="actionIcons"
          @click="deleteShow = true"
        />
      </div>
    </q-item>
    <q-separator spaced />
  </div>
</template>

<script>
export default {
  name: "AdvantageVFPCart",
  props: {
    advantage: Object,
    deleteAdvantage: Function
  },
  data() {
    return {
      deleteShow: false,
      deleteLabel: "Voulez-vous supprimer cet avantage VFP ?",
      cancelLabel: "Annuler",
      validLabel: "Valider",
      advantageLabel: "Avantage VFP :"
    };
  },
  computed: {
    getAdvantageLabel() {
      return this.advantageLabel + " " + this.advantage.libelle;
    }
  }
};
</script>

<style scoped lang="scss">
.item {
  .productImage {
    width: 5%;
  }
  .productName {
    margin-left: 1%;
    width: 95%;
  }
}

@media (max-width: 640px) {
  .item {
    padding-left: 0;
    .productImage {
      display: none;
      width: 0;
    }
    .productName {
      width: 90%;
      padding-right: 2%;
      .giftIcon {
        display: none;
      }
    }
  }
}
</style>
