<template>
  <q-card class="window" full-height>
    <q-card-section class="title">{{ resetPassword }}</q-card-section>
    <q-card-section class="msg" v-if="msg">{{ msg }}</q-card-section>
    <q-card-section class="form">
      <q-form class="resetPassword" @submit="checkandsendform" method="post">
        <q-input type="email" v-model="email" :label="emailLabel" required>
          <template v-slot:prepend>
            <q-icon name="alternate_email" />
          </template>
        </q-input>
        <q-btn
          color="primary"
          class="submit"
          icon-right="send"
          :label="validLabel"
          type="submit"
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
import axios from "axios";
import router from "@/router";
export default {
  name: "HeaderDisconnect",
  props: {},
  data() {
    return {
      email: null,
      msg: null,
      resetPassword: "Réinitialiser le mot de passe",
      emailLabel: "Adresse mail",
      validLabel: "Valider",
      comeBack: "Retour"
    };
  },
  methods: {
    checkandsendform: function(e) {
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      axios
        .post("/rest/manageUser/resetPassword", {
          value: this.email
        })
        .then(() => {
          this.msg = "Vous allez recevoir le nouveau mot de passe par email";
        })
        .catch(e => {
          if (e.response.data) {
            this.msg = e.response.data;
          } else {
            this.msg = "Une erreur est survenu";
          }
        })
        .finally(() => {
          this.$q.loading.hide();
        });
      e.preventDefault();
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
  .resetPassword {
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
    .resetPassword {
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
