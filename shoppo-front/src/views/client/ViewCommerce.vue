<template>
  <q-page class="home" v-if="loaded">
    <div class="storeHeader">
      <p class="storeName">
        <q-icon
          name="arrow_back"
          color="orange"
          class="backIcon"
          @click="goBack"
        />
        {{ commerce.enseigne }}
      </p>
      <q-icon
        :name="fav ? 'star' : 'star_border'"
        @click="changeFav"
        class="favStore"
      />
    </div>
    <img
      src="@/assets/cropped-boulangerie.png"
      class="imgStore"
      :width="screenWidth"
      :height="screenHeight / 4"
    />
    <p class="titleBlock">
      {{ descriptionLabel }}
    </p>
    <p class="descriptionStore" v-if="commerce.description != null">
      {{ commerce.description }}
    </p>
    <p class="descriptionStore" v-if="commerce.adresse">
      Adresse : {{ commerce.adresse.numeroRue }}
      {{ commerce.adresse.libelleRue }} {{ commerce.adresse.codePostal }}
      {{ commerce.adresse.ville }}
    </p>
    <HoraireStore
      day="Lundi"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'lundi')[0]"
    />
    <HoraireStore
      day="Mardi"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'mardi')[0]"
    />
    <HoraireStore
      day="Mercredi"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'mercredi')[0]"
    />
    <HoraireStore
      day="Jeudi"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'jeudi')[0]"
    />
    <HoraireStore
      day="Vendredi"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'vendredi')[0]"
    />
    <HoraireStore
      day="Samedi"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'samedi')[0]"
    />
    <HoraireStore
      day="Dimanche"
      class="descriptionStore"
      :horaire="this.commerce.horaire.filter(h => h.jour == 'dimanche')[0]"
    />
    <p class="titleBlock" v-if="commerce.products.length != 0">
      {{ articleTitle }}
    </p>
    <ArticleInCommerce
      v-for="product in commerce.products"
      :key="product.id"
      :id="product.id"
      :name="product.name"
      :prix="product.prix"
      :stock="product.stock"
      :description="product.description"
    />
    <p class="titleBlock" v-if="commerce.products.length == 0">
      {{ noProducts }}
    </p>

    <p
      class="titleBlock q-mt-sm"
      v-if="commerce.advantages != undefined && commerce.advantages.length != 0"
    >
      {{ vfpTitle }}
    </p>
    <AdvantageVFPCommerce
      v-for="advantage in commerce.advantages"
      :key="advantage.id"
      :advantage="advantage"
    />
    <p
      class="titleBlock q-mt-sm"
      v-if="commerce.advantages == undefined || commerce.advantages.length == 0"
    >
      {{ noVfp }}
    </p>
  </q-page>
</template>

<script>
import ArticleInCommerce from "@/components/client/ArticleInCommerce";
import axios from "axios";
import HoraireStore from "@/components/client/store/HoraireStore";
import AdvantageVFPCommerce from "@/components/client/vfp/AdvantageVFPCommerce";
export default {
  name: "ViewCommerce",
  components: { AdvantageVFPCommerce, HoraireStore, ArticleInCommerce },
  props: {
    siretCodeParam: String
  },
  data() {
    return {
      commerce: Object,
      loaded: false,
      fav: false,
      screenHeight: window.innerHeight,
      screenWidth: window.innerWidth,
      descriptionLabel: "Description",
      articleTitle: "Articles proposés",
      noProducts: "Pas d'articles proposés...",
      vfpTitle: "Avantages VFP",
      noVfp: "Pas d'avantages VFP"
    };
  },
  methods: {
    changeFav() {
      this.fav = !this.fav;
    },
    /**
     Notify if a field is not valid
     */
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [
          {
            label: "Fermer",
            color: "white",
            handler: () => {}
          }
        ]
      });
    },
    goBack() {
      this.$router.back();
    }
  },
  beforeMount() {
    var colormsg, msgRetour;
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    axios
      .get(
        "/rest/client-auth/client/commerce/" + this.$route.query.siretCodeParam
      )
      .then(response => {
        msgRetour = "Le magasin a été récupéré";
        colormsg = "green";
        const commerceData = response.data.commerce;
        this.commerce.enseigne = commerceData.enseigne;
        this.commerce.description = commerceData.description;
        this.commerce.products = response.data.productList;
        this.commerce.adresse = commerceData.adresse;
        this.commerce.horaire = commerceData.horaire;
        this.commerce.advantages = response.data.advantages;
        this.loaded = true;
        return response;
      })
      .catch(e => {
        console.log(e);
        if (e.response && e.response.data) {
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
};
</script>

<style scoped lang="scss">
.home {
  .storeHeader {
    display: flex;
    font-size: 30px;
    font-weight: bold;
    margin-left: 1%;
    margin-top: 0.5%;
    .storeName {
      .backIcon:hover {
        font-weight: bold;
      }
      width: 90%;
      margin-bottom: 2px;
      overflow: hidden; /* make sure it hides the content that overflows */
      white-space: nowrap; /* don't break the line */
      text-overflow: ellipsis; /* give the beautiful '...' effect */
    }
    .favStore {
      color: var(--q-color-primary);
      position: absolute;
      top: 0;
      right: 0;
      font-size: 50px;
      &:hover {
        font-weight: bold;
      }
    }
  }
  .descriptionStore {
    margin-left: 1%;
    font-size: 15px;
  }
  .titleBlock {
    font-size: 20px;
    margin-left: 1%;
    text-decoration: underline var(--q-color-primary);
  }
}

@media (max-width: 640px) {
  .home {
    .storeHeader {
      .storeName {
        font-size: 20px;
      }
      .favStore {
        font-size: 30px;
      }
    }
  }
}
</style>
