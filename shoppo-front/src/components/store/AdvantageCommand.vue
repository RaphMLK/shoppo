<template>
  <q-card class="item">
    <div class="article">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <p class="productName text-h6 q-mt-auto q-mb-auto">
        <q-icon
          color="primary"
          name="card_giftcard"
          size="50px"
          class="giftIcon"
        />
        {{ advantage.libelle }}
      </p>
    </div>
    <div class="actions q-mt-auto q-mb-auto ">
      <q-btn
        round
        color="primary"
        icon="clear"
        class="actionIcons q-mr-md"
        @click="deleteAdvantage"
      />
    </div>
  </q-card>
</template>

<script>
export default {
  name: "AdvantageCommand",
  props: {
    advantage: Object,
    deleteAdvantage: Function
  }
};
</script>

<style scoped lang="scss">
.item {
  display: flex;
  margin-left: auto;
  margin-right: auto;
  width: 98%;
  .article {
    float: left;
    display: flex;
    .productImage {
      width: 10%;
    }
  }
  .actions {
    float: right;
    display: flex;
    width: 100%;
    justify-content: flex-end;
  }
}
</style>
