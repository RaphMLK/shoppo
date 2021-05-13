<template>
  <q-page class="home">
    <p class="title">{{ title }}</p>
    <TwoLineChart
      :data="dataCa"
      :data2="dataCaCnC"
      :legende="legendeCa"
      :title="caTitle"
      :serie-label="caLabel"
      :serie-label2="caCncLabel"
      v-if="render"
    />
    <TwoLineChart
      :data="dataNbClient"
      :data2="dataNbClientCnC"
      :legende="legendeCa"
      :title="nbClientTitle"
      :serie-label="nbClientLabel"
      :serie-label2="nbClientCnCLabel"
      v-if="render"
    />
    <TwoLineChart
      :data="dataNbCommande"
      :data2="dataNbCommandeCnC"
      :legende="legendeCa"
      :title="nbCommandeTitle"
      :serie-label="nbCommandeLabel"
      :serie-label2="nbCommandeCnCLabel"
      v-if="render"
    />
  </q-page>
</template>

<script>
import axios from "axios";
import moment from "moment";
import { mapActions } from "vuex";
import TwoLineChart from "@/components/statistics/TwoLineChart";
export default {
  name: "StatisticsView",
  components: { TwoLineChart },
  data() {
    return {
      title: "Mes statistiques (30 jours)",
      siretCode: "",
      render: false,
      nbRequestApi: 0,
      nbRequestApiDone: 0,
      legendeCa: [],
      dataCa: [],
      dataCaCnC: [],
      caTitle: "Chiffre d'affaire (en €)",
      caLabel: "CA total",
      caCncLabel: "CA Click&Collect",
      dataNbClient: [],
      dataNbClientCnC: [],
      nbClientTitle: "Nombre de client",
      nbClientLabel: "Nb client",
      nbClientCnCLabel: "Nb client Click&Collect",
      dataNbCommande: [],
      dataNbCommandeCnC: [],
      nbCommandeTitle: "Nombre de commande",
      nbCommandeLabel: "Nb commande",
      nbCommandeCnCLabel: "Nb commande Click&Collect"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    this.getUserStore().then(user => {
      this.siretCode = user.user.commerce.siretCode;
      this.getCa();
      this.getCaCnc();
      this.getNbClient();
      this.getNbClientCnC();
      this.getNbCommande();
      this.getNbCommandeCnC();
    });
  },
  methods: {
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
    },
    getCa() {
      this.nbRequestApi++;
      axios
        .post("/rest/commercant-auth/commerce/commercant/stat/read", {
          label: "CA#" + this.siretCode
        })
        .then(response => {
          const data = response.data;
          for (const date in data) {
            this.legendeCa.push(moment(String(date)).format("DD/MM/YYYY"));
            if (data[date].content == null) {
              this.dataCa.push(0);
            } else {
              this.dataCa.push(data[date].content.value);
            }
          }
          this.handleRequestApiEnd();
        })
        .catch(e => {
          var msgRetour;
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Impossible de récupérer votre solde";
          }
          this.notifyForm(msgRetour, "red");
        });
    },
    getCaCnc() {
      this.nbRequestApi++;
      axios
        .post("/rest/commercant-auth/commerce/commercant/stat/read", {
          label: "CnC#CA#" + this.siretCode
        })
        .then(response => {
          const data = response.data;
          for (const date in data) {
            if (data[date].content == null) {
              this.dataCaCnC.push(0);
            } else {
              this.dataCaCnC.push(data[date].content.value);
            }
          }
          this.handleRequestApiEnd();
        })
        .catch(e => {
          var msgRetour;
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Impossible de récupérer votre solde";
          }
          this.notifyForm(msgRetour, "red");
        });
    },
    getNbClient() {
      this.nbRequestApi++;
      axios
        .post("/rest/commercant-auth/commerce/commercant/stat/read", {
          label: "NB CLIENT#" + this.siretCode
        })
        .then(response => {
          const data = response.data;
          for (const date in data) {
            if (data[date].content == null) {
              this.dataNbClient.push(0);
            } else {
              this.dataNbClient.push(data[date].content.value);
            }
          }
          this.handleRequestApiEnd();
        })
        .catch(e => {
          var msgRetour;
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Impossible de récupérer votre solde";
          }
          this.notifyForm(msgRetour, "red");
        });
    },
    getNbClientCnC() {
      this.nbRequestApi++;
      axios
        .post("/rest/commercant-auth/commerce/commercant/stat/read", {
          label: "CnC#NB CLIENT#" + this.siretCode
        })
        .then(response => {
          const data = response.data;
          for (const date in data) {
            if (data[date].content == null) {
              this.dataNbClientCnC.push(0);
            } else {
              this.dataNbClientCnC.push(data[date].content.value);
            }
          }
          this.handleRequestApiEnd();
        })
        .catch(e => {
          var msgRetour;
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Impossible de récupérer votre solde";
          }
          this.notifyForm(msgRetour, "red");
        });
    },
    getNbCommande() {
      this.nbRequestApi++;
      axios
        .post("/rest/commercant-auth/commerce/commercant/stat/read", {
          label: "NB CMD#" + this.siretCode
        })
        .then(response => {
          const data = response.data;
          for (const date in data) {
            if (data[date].content == null) {
              this.dataNbCommande.push(0);
            } else {
              this.dataNbCommande.push(data[date].content.value);
            }
          }
          this.handleRequestApiEnd();
        })
        .catch(e => {
          var msgRetour;
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Impossible de récupérer votre solde";
          }
          this.notifyForm(msgRetour, "red");
        });
    },
    getNbCommandeCnC() {
      this.nbRequestApi++;
      axios
        .post("/rest/commercant-auth/commerce/commercant/stat/read", {
          label: "CnC#NB CMD#" + this.siretCode
        })
        .then(response => {
          const data = response.data;
          for (const date in data) {
            if (data[date].content == null) {
              this.dataNbCommandeCnC.push(0);
            } else {
              this.dataNbCommandeCnC.push(data[date].content.value);
            }
          }
          this.handleRequestApiEnd();
        })
        .catch(e => {
          var msgRetour;
          if (e.response.data) {
            msgRetour = e.response.data;
          } else {
            msgRetour = "Impossible de récupérer votre solde";
          }
          this.notifyForm(msgRetour, "red");
        });
    },
    handleRequestApiEnd() {
      this.nbRequestApiDone++;
      if (this.nbRequestApi == this.nbRequestApiDone) {
        this.render = true;
        this.$q.loading.hide();
      }
    },
    ...mapActions(["getUserStore"])
  }
};
</script>

<style scoped lang="scss">
.home {
  padding-left: 1%;
  padding-right: 1%;
  .title {
    text-align: center;
    font-weight: bold;
    font-size: 35px;
    color: cadetblue;
  }
}
</style>
