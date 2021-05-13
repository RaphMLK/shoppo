<template>
  <q-card class="window" full-height>
    <q-card-section class="title">{{ Login }}</q-card-section>
    <q-card-section class="form">
      <q-form class="login" ref="connexionForm" greedy>
        <q-input
          v-model="email"
          label="Adresse mail"
          type="email"
          :rules="[val => (val.length !== 0 && reg.test(val)) || '']"
          lazy-rules="ondemand"
        >
          <template v-slot:prepend>
            <q-icon name="alternate_email" />
          </template>
        </q-input>

        <q-input
          v-model="password"
          :type="isPwd ? 'password' : 'text'"
          :label="passwordLabel"
          lazy-rules="ondemand"
        >
          <template v-slot:append>
            <q-icon
              :name="isPwd ? 'visibility_off' : 'visibility'"
              class="cursor-pointer"
              @click="isPwd = !isPwd"
            />
          </template>
        </q-input>

        <q-btn
          color="primary"
          class="submit buttonForm"
          icon-right="send"
          :label="validLabel"
          @click="login"
        />

        <q-btn
          color="primary"
          class="buttonForm"
          icon="vpn_key"
          :label="forgotPassword"
          @click="goToResetPassword"
        />

        <q-btn
          color="primary"
          class="buttonForm"
          icon="person_add_alt_1"
          :label="register"
          @click="goToRegister"
        />
      </q-form>
    </q-card-section>
  </q-card>
</template>

<script>
import router from "@/router";
import axios from "axios";
import UserStore from "../../models/UserStore.js";
import { mapActions } from "vuex";
export default {
  name: "HeaderDisconnect",
  props: {
    msg: String
  },
  data() {
    return {
      email: "",
      password: "",
      Login: "Se connecter",
      isPwd: true,
      emailLabel: "Adresse mail",
      passwordLabel: "Mot de passe",
      forgotPassword: "Mot de passe oublié",
      register: "S'inscrire",
      validLabel: "Valider",
      reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/, //eslint-disable-line
    }    ;
  },
  beforeMount() {
      this.getUserStore().then(
        data => {
          console.log("data");
          console.log(data);
        }
      )
  },
  methods: {
    ...mapActions(["getUserStore"]),
    /**
      Login the user
    */
    login() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          var colormsg, msgRetour;
          axios
            .post("/rest/manageUser/login", {
              email: this.email,
              password: this.password
            })
            .then(response => {
              msgRetour = "Vous êtes maintenant connecté !";
              colormsg = "green";
              var user = new UserStore(
                response.data.token,
                response.data.role,
                response.data.connectedUser
              );
              this.handleUserStore(user);
              this.determineRoute(user.role);
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
        }
      });
    },
    ...mapActions(["handleUserStore"]),
    /**
      Check if the fields are valid
      Send notify if this is not the case
    */
    checkValidators() {
      return this.$refs.connexionForm.validate().then(success => {
        if (!success) {
          if (!this.$refs.emailInput.validate()) {
            this.notifyForm("email requis", "red");
          }
        }
        return success;
      });
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
    },
    determineRoute(role) {
      if (role.toUpperCase() == "ADMIN") {
        this.goTo("/admin");
      } else if (role.toUpperCase() == "COMMERCANT") {
        this.goTo("/commerce");
      } else if (role.toUpperCase() == "CLIENT") {
        this.goTo("/client");
      }
    },
    goTo(route) {
      router.push(route);
    },
    goToResetPassword() {
      router.push("resetPassword");
    },
    goToRegister() {
      router.push("inscription");
    }
  }
};
</script>

<style scoped lang="scss">
.window {
  background-color: floralwhite;
  .title {
    font-size: 2em;
    padding-bottom: 0;
  }
  .login {
    .buttonForm {
      margin-top: 3%;
      width: 100%;
    }
    a {
      color: orange;
    }
    .links {
      padding-top: 0.5em;
    }
    .reset {
      font-size: 1.1em;
    }
  }
}

@media (min-width: 640px) {
  .window {
    background-color: floralwhite;
    width: 25%;
    border: solid 5px;
    .title {
      font-size: 2em;
      padding-bottom: 0;
    }
    .login {
      .buttonForm {
        margin-top: 3%;
        width: 100%;
      }
    }
  }
}

@media (max-width: 640px) {
  .window {
    width: 90%;
    height: 100%;
    margin-top: 20%;
    margin-right: auto;
    margin-left: auto;
  }
  .links div {
    text-align: center;
  }
}
</style>
