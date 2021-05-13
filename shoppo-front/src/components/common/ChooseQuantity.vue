<template>
  <div class="element">
    <q-input
      v-model.number="stock"
      type="number"
      :label="stockLabel"
      :rules="[
        val =>
          (val != null &&
            val >= 0 &&
            (this.max == null ? true : val <= this.max)) ||
          ''
      ]"
    >
      <template v-slot:prepend>
        <q-icon name="add" class="icons" @click="increment" />
      </template>
      <template v-slot:append>
        <q-icon name="remove" class="icons" @click="decrement" />
      </template>
    </q-input>
    <q-btn
      v-if="hasChanged && viewButton"
      round
      color="primary"
      icon="check"
      class="validQuantity"
      @click="validQuantity"
    />
  </div>
</template>

<script>
export default {
  name: "ChooseQuantity",
  props: {
    stockParam: Number,
    modifyQuantity: Function,
    max: {
      type: Number,
      default: null
    },
    viewButton: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      defaultStock: this.stockParam,
      stock: this.stockParam,
      stockLabel: "Stock",
      hasChanged: false
    };
  },
  methods: {
    increment() {
      if (this.max == null ? true : this.stock + 1 <= this.max) this.stock++;
      else this.notifyForm("Le stock n'est pas suffisant", "red");
    },
    decrement() {
      if (this.stock > 0) this.stock--;
    },
    validQuantity() {
      if (this.modifyQuantity(this.stock)) {
        this.defaultStock = this.stock;
        this.hasChanged = false;
      }
    },
    /**
     Notify if a field is not valid
     */
    notifyForm(msg, colormsg) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: colormsg,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    }
  },
  watch: {
    stock: function() {
      this.hasChanged = this.stock != this.defaultStock;
    }
  }
};
</script>

<style scoped lang="scss">
.element {
  margin-bottom: auto;
  margin-top: auto;
  display: flex;
  margin-right: 5%;
  .icons:hover {
    color: var(--q-color-primary);
  }
  .validQuantity {
    height: 50%;
    margin-left: 10px;
    margin-top: auto;
    margin-bottom: auto;
  }
}
</style>
