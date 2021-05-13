<template>
  <q-layout view="hHh lpR fFf" class="layout">
    <header-admin-view />
    <drawer :menu="CLIENT_MENU" />
    <DialogChangePassword :show="show" type="client" />
    <q-page-container class="container">
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import HeaderAdminView from "../views/headers/HeaderAdminView.vue";
import Drawer from "./Drawer.vue";
import CLIENT_MENU from "@/constants/drawers/ClientMenu";
import axios from "axios";
import DialogChangePassword from "@/layouts/DialogChangePassword";
import { mapActions } from "vuex";

export default {
  name: "ClientLayout",
  components: {
    DialogChangePassword,
    HeaderAdminView,
    Drawer
  },
  data() {
    return {
      CLIENT_MENU,
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
        .get("/rest/client-auth/")
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

<style scoped></style>
