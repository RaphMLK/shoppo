<template>
  <q-card class="window" full-height>
    <q-card-section class="title">{{ inscription }}</q-card-section>
    <q-card-section class="form">
      <q-form class="inscription" ref="inscriptionForm" greedy>
        <q-input
          v-model="email"
          :label="emailLabel"
          type="email"
          :rules="[val => (val.length !== 0 && reg.test(val)) || '']"
          ref="emailInput"
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
          :rules="[val => strongRegex.test(val) || '']"
          ref="passwordInput"
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

        <q-input
          v-model="confirmPassword"
          :type="isConfirmPwd ? 'password' : 'text'"
          :label="confirmPasswordLabel"
          :rules="[val => (strongRegex.test(val) && val === password) || '']"
          ref="confirmPasswordInput"
          lazy-rules="ondemand"
        >
          <template v-slot:append>
            <q-icon
              :name="isConfirmPwd ? 'visibility_off' : 'visibility'"
              class="cursor-pointer"
              @click="isConfirmPwd = !isConfirmPwd"
            />
          </template>
        </q-input>

        <q-btn
          color="primary"
          class="submit"
          icon-right="send"
          :label="validLabel"
          @click="register"
        />

        <q-btn
          color="primary"
          class="submit"
          icon="arrow_back"
          :label="comeBack"
          @click="goToLogin"
        />
      </q-form>
    </q-card-section>
  </q-card>
</template>

<script>
import router from "@/router";
import axios from "axios";

export default {
  name: "HeaderDisconnect",
  data() {
    return {
      email: "",
      password: "",
      confirmPassword: "",
      inscription: "S'inscrire",
      emailLabel: "Adresse mail",
      passwordLabel: "Mot de passe",
      confirmPasswordLabel: "Confimer mot de passe",
      validLabel: "Valider",
      isPwd: true,
      isConfirmPwd: true,
      emailRequired: "L'adresse email est obligatoire",
      passwordRequired:
        "Le mot de passe doit au moins contenir 8 caractères, une majuscule, une minuscule et un chiffre",
      confirmPasswordRequired: "Le deux mots de passe doivent être identiques",
      reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/, //eslint-disable-line
      strongRegex: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/, //eslint-disable-line
      //le eslint-disable-line est nécessaire pour éviter la non compilation alors que c'est bon
      comeBack: "Retour"
    };
  },
  methods: {
    /**
      Register the new user
    */
    register() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          var colormsg, msgRetour;
          axios
            .post("/rest/client/", {
              email: this.email,
              password: this.password
            })
            .then(() => {
              msgRetour = "Vous êtes maintenant inscrit !";
              colormsg = "green";
              this.goToLogin();
            })
            .catch(e => {
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

    /**
      Check if the fields are valid
      Send notify if this is not the case
    */
    checkValidators() {
      return this.$refs.inscriptionForm.validate().then(success => {
        if (!success) {
          if (!this.$refs.emailInput.validate()) {
            this.notifyForm(this.emailRequired, "red");
          } else if (!this.$refs.passwordInput.validate()) {
            this.notifyForm(this.passwordRequired, "red");
          } else {
            this.notifyForm(this.confirmPasswordRequired, "red");
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

    goToLogin() {
      router.push("login");
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
  .inscription {
    .submit {
      margin-top: 3%;
      width: 100%;
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
    .inscription {
      .comeBack {
        color: orange;
      }
      .submit {
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
}
</style>
