<template>
  <q-card class="window">
    <q-card-section class="title">{{ title }}</q-card-section>
    <q-card-section class="form">
      <q-form class="addStoreForm" ref="addStoreForm" greedy>
        <q-input
          v-model="email"
          :label="emailLabel"
          type="email"
          :rules="[val => (val != null && reg.test(val)) || '']"
          ref="emailInput"
          lazy-rules="ondemand"
          class="inputs"
        >
          <template v-slot:prepend>
            <q-icon name="alternate_email" />
          </template>
        </q-input>

        <q-input
          v-model="siretcode"
          :label="siretLabel"
          :mask="siretMask"
          :rules="[val => (val != null && val.length === 14) || '']"
          ref="siretInput"
          unmasked-value
          lazy-rules="ondemand"
          class="inputs"
        >
          <template v-slot:prepend>
            <q-icon name="store" />
          </template>
        </q-input>

        <q-btn
          color="primary"
          class="submit"
          icon-right="send"
          :label="valid"
          @click="addStore"
        />
      </q-form>
    </q-card-section>
  </q-card>
</template>

<script>
import axios from "axios";

export default {
  name: "AddStore",
  data() {
    return {
      title: "Ajouter un commerce",
      email: null,
      emailLabel: "Adresse mail",
      siretcode: null,
      siretLabel: "Code Siret",
      siretMask: "### ### ### #####",
      valid: "Valider",
      reg: /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,24}))$/, //eslint-disable-line
      emailRequired:
        "L'adresse email est obligatoire ou n'a pas la forme correcte",
      siretRequired: "Le code siret doit être composé de 14 chiffres"
    };
  },
  methods: {
    addStore() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          let colormsg, msgRetour;
          axios
            .post("/rest/admin/commerce/", {
              email: this.email,
              siret: this.siretcode
            })
            .then(() => {
              msgRetour = "Le commerce a été créé";
              colormsg = "green";
            })
            .catch(e => {
              console.log(e.response);
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
     * Check validators and display notification if they aren't valid
     */
    checkValidators() {
      return this.$refs.addStoreForm.validate().then(success => {
        if (!success) {
          if (!this.$refs.emailInput.validate()) {
            this.notifyForm(this.emailRequired, "red");
          } else {
            this.notifyForm(this.siretRequired, "red");
          }
        }
        return success;
      });
    },

    /**
      Notify if a field is not valid
     */
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    }
  }
};
</script>

<style scoped lang="scss">
.window {
  .title {
    font-size: 2em;
    text-align: center;
  }
  .form {
    margin-top: 0;
    .addStoreForm {
      .inputs {
        font-size: 20px;
      }
      .submit {
        width: 100%;
        margin-top: 1%;
      }
    }
  }
}
</style>
