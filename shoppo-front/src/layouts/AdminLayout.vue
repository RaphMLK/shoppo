<template>
  <q-layout view="hHh lpR fFf" class="layout">
    <header-admin-view v-if="$q.screen.width > 640" />
    <header-view v-if="$q.screen.width <= 640" />
    <drawer :menu="ADMIN_MENU" v-if="$q.screen.width > 640" />
    <q-page-container v-if="$q.screen.width > 640" class="container">
      <router-view />
    </q-page-container>
    <q-page-container v-if="$q.screen.width <= 640" class="containerMobile">
      <q-page class="page">
        <p style="white-space: pre-line">{{ msg }}</p>
      </q-page>
    </q-page-container>
    <q-form class="login" ref="connexionForm" greedy>
      <q-btn
        color="primary"
        class="submit buttonForm"
        label="test"
        @click="testRoute"
      />
    </q-form>
  </q-layout>
</template>

<script>
import HeaderAdminView from "../views/headers/HeaderAdminView.vue";
import ADMIN_MENU from "../constants/drawers/AdminMenu";
import Drawer from "./Drawer.vue";
import HeaderView from "@/views/headers/HeaderView";
import axios from "axios";

export default {
  name: "AdminLayout",

  components: {
    HeaderView,
    HeaderAdminView,
    Drawer
  },

  data() {
    return {
      ADMIN_MENU,
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)"
    };
  },
  methods: {
    /**
      Login the user
    */
    testRoute() {
      var colormsg, msgRetour;
      axios
        .get("/rest/admin-auth/")
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
    }
  }
};
</script>

<style scoped lang="scss">
.layout {
  .q-page-container {
    background-color: var(--q-color-secondary);
    height: 100%;
  }
  .containerMobile {
    height: 100%;
    .page {
      background-color: darkorange;
      color: white;
      height: 100%;
      text-align: center;
      padding-top: 5%;
      font-size: 25px;
    }
  }
}
</style>
