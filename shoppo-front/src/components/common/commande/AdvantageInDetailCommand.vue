<template>
  <q-card class="item q-mb-sm">
    <div class="article">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <div class="productInfo text-h6 q-mb-auto q-mt-auto">
        <q-icon
          color="primary"
          name="card_giftcard"
          size="50px"
          class="giftIcon"
        />
        {{ advantage.libelle }}
      </div>
    </div>
  </q-card>
</template>

<script>
export default {
  name: "AdvantageInDetailCommand",
  props: {
    advantage: Object
  }
};
</script>

<style scoped lang="scss">
.item {
  display: flex;
  margin-left: auto;
  margin-right: auto;
  .article {
    float: left;
    display: flex;
    .productImage {
      width: 10%;
    }
  }
}
</style>
