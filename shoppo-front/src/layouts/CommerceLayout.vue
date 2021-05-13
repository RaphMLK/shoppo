<template>
  <q-layout view="hHh lpR fFf" class="layout">
    <header-admin-view />
    <drawer :menu="COMMERCE_MENU" />
    <DialogChangePassword :show="show" type="commerce" />
    <q-page-container class="container">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import HeaderAdminView from "../views/headers/HeaderAdminView.vue";
import Drawer from "./Drawer.vue";
import COMMERCE_MENU from "@/constants/drawers/CommerceMenu";
import axios from "axios";
import DialogChangePassword from "@/layouts/DialogChangePassword";
import { mapActions } from "vuex";

export default {
  name: "CommerceLayout",
  components: {
    DialogChangePassword,
    HeaderAdminView,
    Drawer
  },
  data() {
    return {
      COMMERCE_MENU,
      show: false
    };
  },
  methods: {
    /**
      Login the user
    */
    testRoute() {
      var colormsg, msgRetour;
      axios
        .get("/rest/commercant-auth/")
        .then(response => {
          msgRetour = response.data;
          colormsg = "green";
        })
        .catch(e => {
          console.log(e);
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenu";
          }
          colormsg = "red";
        })
        .finally(() => {
          this.notifyForm(msgRetour, colormsg);
        });
    },
    notifyForm(msg, colormsg) {
      this.$q.notify({
        message: msg,
        color: colormsg,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    },

    ...mapActions(["getUserStore"])
  },
  beforeMount() {
    this.getUserStore().then(user => {
      this.show = user.user.changePassword;
    });
  }
};
</script>

<style scoped lang="scss">
.layout {
  .q-page-container {
    background-color: var(--q-color-secondary);
    height: 100%;
  }
}
</style>
