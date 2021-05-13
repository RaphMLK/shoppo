<template>
  <q-page class="home">
    <p class="title">{{ title }}</p>
    <OneLineChart
      :data="dataVfp"
      :legende="legendeVfp"
      :title="vfpTitle"
      :serie-label="vfpLabel"
      v-if="render"
    />
  </q-page>
</template>

<script>
import axios from "axios";
import moment from "moment";
import OneLineChart from "@/components/statistics/OneLineChart";

export default {
  name: "StatisticsAdminView",
  components: { OneLineChart },
  data() {
    return {
      render: false,
      title: "Statistiques (30 jours)",
      dataVfp: [],
      legendeVfp: [],
      vfpTitle: "Nombre de clients VFP",
      vfpLabel: "VFP"
    };
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .post("/rest/admin-auth/admin/stat/read", {
        label: "NB VFP"
      })
      .then(response => {
        const data = response.data;
        for (const date in data) {
          this.legendeVfp.push(moment(String(date)).format("DD/MM/YYYY"));
          if (data[date].content == null) {
            this.dataVfp.push(0);
          } else {
            this.dataVfp.push(data[date].content.value);
          }
        }
        this.render = true;
        this.$q.loading.hide();
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
