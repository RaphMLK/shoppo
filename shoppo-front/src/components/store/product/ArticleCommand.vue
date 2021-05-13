<template>
  <q-card class="item">
    <div class="article">
      <img
        src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        class="productImage"
      />
      <p class="productName text-h6 q-mt-auto q-mb-auto">
        {{ product.attribute.name }} - {{ product.attribute.prix }}€
      </p>
    </div>
    <div class="actions q-mt-auto q-mb-auto ">
      <ChooseQuantity
        :view-button="true"
        :max="product.attribute.stock"
        :stock-param="quantity"
        class="q-mt-auto q-mb-auto chooseQuantity"
        ref="chooseQuantity"
        :modify-quantity="modifyQuantity"
      />
      <div class="deleteButton q-mt-auto q-mb-auto">
        <q-btn
          round
          color="primary"
          icon="clear"
          class="actionIcons"
          @click="deleteProduct(product)"
        />
      </div>
      <div class="totalPrice q-mt-auto q-mb-auto q-ml-md">
        <p class="text-h6 q-mt-auto q-mb-auto">{{ getPrice }}€</p>
      </div>
    </div>
  </q-card>
</template>

<script>
import ChooseQuantity from "@/components/common/ChooseQuantity";
export default {
  name: "ArticleCommand",
  components: { ChooseQuantity },
  props: {
    product: Object,
    deleteProduct: Function
  },
  data() {
    return {
      quantity: 1
    };
  },
  methods: {
    modifyQuantity(newValue) {
      this.quantity = newValue;
      return true;
    }
  },
  computed: {
    getPrice() {
      return Number.parseFloat(
        this.quantity * this.product.attribute.prix
      ).toFixed(2);
    }
  },
  watch: {
    quantity: function(newVal) {
      if (newVal == 0) this.deleteProduct(this.product);
      this.quantity = newVal;
    }
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
