<template>
  <span>
    <q-item class="item">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <p class="product q-mt-auto q-mb-auto q-ml-md">
        {{ getTitleCard }}
      </p>
    </q-item>
    <q-separator spaced />
  </span>
</template>

<script>
export default {
  name: "ArticleInCommande",
  props: {
    product: Object
  },
  computed: {
    getTitleCard() {
      return (
        this.product.quantite +
        " " +
        this.product.productQuantitePK.product.attribute.name +
        " - " +
        Number.parseFloat(
          this.product.productQuantitePK.product.attribute.prix *
            this.product.quantite
        ).toFixed(2) +
        " €"
      );
    }
  }
};
</script>

<style scoped lang="scss">
.item {
  .productImage {
    width: 5%;
  }
  .product {
    font-size: 1.15rem;
  }
}

@media (max-width: 640px) {
  .productImage {
    display: none;
  }
}
</style>
