<template>
  <q-dialog v-model="show">
    <q-card>
      <q-card-section class="row items-center q-pb-none">
        <div class="text-h6">{{ title }}</div>
        <q-space />
        <q-btn icon="close" flat round dense v-close-popup />
      </q-card-section>

      <q-card-section>
        <p>{{ msg }}</p>
        <q-form ref="changePasswordForm" greedy>
          <q-input
            v-model="password"
            :type="isPwd ? 'password' : 'text'"
            :label="passwordLabel"
            :rules="[val => strongRegex.test(val) || '']"
            ref="passwordInput"
            lazy-rules="ondemand"
          >
            <template v-slot:prepend>
              <q-icon name="vpn_key" />
            </template>
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
            <template v-slot:prepend>
              <q-icon name="vpn_key" />
            </template>
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
            @click="changePassword"
          />
        </q-form>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>

<script>
import axios from "axios";
import { mapActions } from "vuex";

export default {
  name: "DialogChangePassword",
  props: {
    show: Boolean,
    type: String
  },
  data() {
    return {
      title: "Changer votre mot de passe",
      msg:
        "Votre mot de passe a été envoyé par mail, cela n'est pas sûr, nous vous conseillons de changer votre mot de passe",
      password: "",
      confirmPassword: "",
      passwordLabel: "Mot de passe",
      confirmPasswordLabel: "Confimer mot de passe",
      isPwd: true,
      isConfirmPwd: true,
      validLabel: "Valider",
      passwordRequired:
        "Le mot de passe doit au moins contenir 8 caractères, une majuscule, une minuscule et un chiffre",
      confirmPasswordRequired: "Le deux mots de passe doivent être identiques",
      strongRegex: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/, //eslint-disable-line
    };
  },
  methods: {
    changePassword() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          var colormsg, msgRetour, url;
          console.log(this.type);
          switch (this.type) {
            case "client":
              url = "/rest/client-auth/client/password/change";
              break;
            case "commerce":
              url = "/rest/commercant-auth/commerce/commercant/password/change";
              break;
            default:
              colormsg = "red";
              msgRetour = "Erreur interne";
              this.notifyForm(msgRetour, colormsg);
              return;
          }
          axios
            .post(url, {
              password: this.password
            })
            .then(() => {
              msgRetour = "Votre mot de passe est maintenant changé !";
              colormsg = "green";
              this.show = false;
              this.getUserStore().then(user => {
                user.user.changePassword = false;
                this.handleUserStore(user);
              });
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
      return this.$refs.changePasswordForm.validate().then(success => {
        if (!success) {
          if (!this.$refs.passwordInput.validate()) {
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

    ...mapActions(["getUserStore", "handleUserStore"])
  }
};
</script>

<style scoped>
.submit {
  width: 100%;
}
</style>
