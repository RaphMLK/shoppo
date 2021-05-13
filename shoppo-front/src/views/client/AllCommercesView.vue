<template>
  <q-page class="home">
    <div class="row wrap justify-center">
      <StoreCard
        v-for="commerce in commerces"
        :key="commerce.id"
        :commerce="commerce"
        class="col-3.5 q-ma-md"
      />
    </div>
  </q-page>
</template>

<script>
import StoreCard from "@/components/client/store/StoreCard";
import axios from "axios";
export default {
  name: "AllCommercesView",
  components: { StoreCard },
  data() {
    return {
      commerces: Array
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get("/rest/client-auth/client/clientToCommerce/getAllCommerces")
      .then(response => {
        this.commerces = response.data;
        return response;
      })
      .catch(e => {
        var colormsg, msgRetour;
        console.log(e);
        if (e.response && e.response.data) {
          msgRetour = e.response.data;
        } else {
          msgRetour = "Une erreur est survenu";
        }
        colormsg = "red";
        this.notifyForm(msgRetour, colormsg);
      })
      .finally(() => {
        this.$q.loading.hide();
      });
  }
};
</script>

<style scoped lang="scss">
.storeCard {
  padding: 10px;
}
</style>
