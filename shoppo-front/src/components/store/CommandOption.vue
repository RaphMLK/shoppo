<template>
  <q-item>
    <q-item-section avatar>
      <q-avatar rounded>
        <img
          src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
        />
      </q-avatar>
    </q-item-section>
    <q-item-section>
      <span>{{ product.attribute.name }}</span>
    </q-item-section>
    <q-item-section side>
      <q-toggle v-model="selected" @input="updateList(product, selected)" />
    </q-item-section>
  </q-item>
</template>

<script>
export default {
  name: "CommandOption",
  props: {
    product: Object,
    updateList: Function,
    selectedParam: Boolean
  },
  data() {
    return {
      selected: this.selectedParam
    };
  }
};
</script>

<style scoped></style>
