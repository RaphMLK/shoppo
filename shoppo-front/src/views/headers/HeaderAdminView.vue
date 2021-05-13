<template>
  <q-header elevated class="header">
    <q-toolbar class="toolbar">
      <q-btn dense flat round icon="menu" @click="handleDrawer" />
      <img alt="Shoppo Logo" src="@/assets/shoppo_logo.png" class="logo" />
      <q-btn
          class="action"
          icon="power_settings_new"
          @click="disconnect"
      />
    </q-toolbar>
  </q-header>
</template>

<script>
import { mapActions } from "vuex"
import axios from "axios";
import router from "@/router";

export default {
  name: "HeaderAdminView",
  components: {},
  data() {
    return {
      left: false,
    };
  },

  methods:{
    ...mapActions(['handleDrawer']),
    ...mapActions(["getUserStore"]),
    ...mapActions(["logout"]),
    disconnect() {
      this.getUserStore().then(
        data => {
          this.callDisconnect(data.token);
        }
      )
    },
    goToLogin() {
      router.push("/login");
    },
    callDisconnect(token) {
      axios
        .post("/rest/manageUser/disconnect", { value: token })
        .then(() => {
          this.logout().then(
            () => {
              this.goToLogin();
            }
          )
        })
        .catch(error => {
            console.log(error);
        });
    }
  }
};
</script>

<style scoped lang="scss">
.layout {
  .q-header {
    background-color: orange;
    height: 10%;
    .toolbar {
      height: 100%;
      .access-icon {
        text-align: right;
        font-size: 200%;
      }
      .logo {
        height: 100%;
      }
    }
  }
  .q-page-container {
    height: 100%;
  }
  .action {
      position: absolute;
      right: 2.5em;
  }
}


@media (max-width: 640px) {
// TODO blocage page
}
</style>