<template>
  <q-page class="home">
    <div class="q-mt-md q-ml-md q-mb-md">
      <span class="text-h3">{{ titleLabel }}</span>
      <q-icon name="help" class="text-primary cursor-pointer text-h3 help-icon">
        <q-popup-proxy>
          <q-banner class="bg-primary text-white">
            <template v-slot:avatar>
              <q-icon name="savings" />
            </template>
            {{ helpLabel }}
          </q-banner>
        </q-popup-proxy>
      </q-icon>
    </div>
    <div class="q-pl-md q-pr-md">
      <q-linear-progress :value="progressVFP / 5" color="primary" size="30px">
        <div class="absolute-full flex flex-center">
          <q-badge
            color="white"
            text-color="primary"
            :label="getProgressLabel"
          />
        </div>
      </q-linear-progress>
    </div>
    <div class="q-mr-md q-ml-md q-mt-md">
      <p class="text-h5 q-mb-sm">{{ globalAdvantageLabel }}</p>
      <q-card>
        <q-list padding>
          <GlobalAdvantageVFP
            v-for="advantage of advantages"
            :key="advantage.id"
            :advantage="advantage"
            :disable="progressVFP < 5 || client.avantage == 'USED'"
          />
        </q-list>
      </q-card>
    </div>
  </q-page>
</template>

<script>
import GlobalAdvantageVFP from "@/components/client/vfp/GlobalAdvantageVFP";
import axios from "axios";
export default {
  name: "VFPClientView",
  components: { GlobalAdvantageVFP },
  data() {
    return {
      advantages: [
        {
          id: 15151,
          libelle: "Ticket de transport en commun (bus, métro...)"
        },
        {
          id: 15151515,
          libelle: "Place de parking gratuit (20 minutes)"
        }
      ],
      titleLabel: "Statut VFP",
      helpLabel:
        "Votre statut VFP vous permet d'obtenir des bonus globaux ou chez des commerçants.\n" +
        "Pour obtenir ce statut VFP, il faut commander chez nos commerçants 5 jours sur les 7 derniers jours \n",
      globalAdvantageLabel: "Avantages Globaux",
      client: null,
      progressVFP: 0
    };
  },
  methods: {
    notifyForm(msg, colormsg) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: colormsg,
        actions: [
          {
            label: "Fermer",
            color: "white",
            handler: () => {}
          }
        ]
      });
    }
  },
  computed: {
    getProgressLabel() {
      const date = new Date();
      const now = date.toLocaleDateString();
      date.setDate(date.getDate() - 7);
      const minusSeven = date.toLocaleDateString();
      return (
        "Du " + minusSeven + " au " + now + " : " + this.progressVFP + " / 5"
      );
    }
  },
  beforeMount() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    var msgRetour, colormsg;
    axios
      .get("/rest/client-auth/client/infos")
      .then(response => {
        this.client = response.data;
        this.progressVFP = this.client.pointsVFP.match(
          new RegExp("#", "gi")
        ).length;
        axios.get("/rest/client-auth/client/avantage").then(response => {
          console.log(response);
          this.advantages = response.data;
        });
      })
      .catch(e => {
        console.log(e);
        if (e.response.data) {
          msgRetour = e.response.data;
        } else {
          msgRetour = "Une erreur est survenu";
        }
        colormsg = "red";
        this.notifyForm(msgRetour, colormsg);
      })
      .finally(() => {
        this.$q.loading.hide();
      });
  }
};
</script>

<style scoped lang="scss">
.home {
  .help-icon {
    vertical-align: bottom;
  }
}
</style>
