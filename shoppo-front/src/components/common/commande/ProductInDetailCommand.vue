<template>
  <q-card class="item q-mb-sm">
    <div class="article">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <div class="productInfo q-mb-auto q-mt-auto">
        <p class="productName text-h6 q-mb-none">
          {{ nameLabel + product.productQuantitePK.product.attribute.name }}
        </p>
        <p class="productQt text-h6 q-mb-none">
          {{ quantityLabel + product.quantite }}
        </p>
        <p class="productPrice text-h6">
          {{ priceLabel + product.productQuantitePK.product.attribute.prix }}€
        </p>
      </div>
    </div>
    <div class="totalPrice q-mt-auto q-mb-auto q-ml-md">
      <p class="text-h6 q-mt-auto q-mb-auto">{{ getPrice }}€</p>
    </div>
  </q-card>
</template>

<script>
export default {
  name: "ProductInDetailCommand",
  props: {
    product: Object
  },
  data() {
    return {
      nameLabel: "Nom : ",
      quantityLabel: "Quantité : ",
      priceLabel: "Prix : "
    };
  },
  computed: {
    getPrice() {
      return Number.parseFloat(
        this.product.quantite *
          this.product.productQuantitePK.product.attribute.prix
      ).toFixed(2);
    }
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
