<template>
  <q-item :clickable="false">
    <q-item-section>
      <q-item-label>{{ advantage.libelle }}</q-item-label>
    </q-item-section>
    <q-item-section side>
      <q-btn
        color="primary"
        :label="activeLabel"
        icon-right="check"
        @click="openConfirmDialog"
        :disable="disable"
      />
    </q-item-section>

    <q-dialog v-model="confirm" persistent>
      <q-card>
        <q-card-section class="row items-center">
          <q-avatar icon="check" color="primary" text-color="white" />
          <span class="q-ml-sm">{{ confirmLabel }}</span>
        </q-card-section>

        <q-card-actions align="right">
          <q-btn flat :label="cancelLabel" color="primary" v-close-popup />
          <q-btn
            flat
            :label="validLabel"
            color="primary"
            @click="validAdvantage"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
  </q-item>
</template>

<script>
import axios from "axios";
import router from "@/router";

export default {
  name: "GlobalAdvantageVFP",
  props: {
    advantage: Object,
    disable: Boolean
  },
  data() {
    return {
      activeLabel: "Activer",
      confirm: false,
      confirmLabel:
        "Confirmer ce choix de cet advantage VFP pour aujourd'hui ?",
      cancelLabel: "Annuler",
      validLabel: "Valider",
      disableVFP: this.disable
    };
  },
  methods: {
    openConfirmDialog() {
      this.confirm = true;
    },
    validAdvantage() {
      this.confirm = false;
      this.$q.loading.show({
        spinnerColor: "orange"
      });
      var msgRetour, colormsg;
      axios
        .post("/rest/client-auth/client/avantage", {
          avantage: this.advantage.id
        })
        .then(() => {
          msgRetour = "L'avantage est activé !";
          colormsg = "green";
          router.push("/client");
        })
        .catch(e => {
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Une erreur est survenue";
          }
          colormsg = "red";
        })
        .finally(() => {
          this.notifyForm(msgRetour, colormsg);
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

<style scoped></style>
