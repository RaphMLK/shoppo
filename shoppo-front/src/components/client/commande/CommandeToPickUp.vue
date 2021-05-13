<template>
  <q-card class="q-mr-md q-ml-md q-mb-md">
    <q-card-section class="q-pb-none">
      <div class="text-h6">
        {{ getTitleCard }}
        <q-checkbox
          v-model="checked"
          @input="selectCommand(checked, commande.id)"
          v-if="selectActive"
        />
      </div>
    </q-card-section>

    <q-card-section>
      <q-expansion-item
        switch-toggle-side
        expand-separator
        :label="horaireLabel"
      >
        <p class="descriptionStore q-mb-none" v-if="commande.commerce.adresse">
          {{ getAddress }}
        </p>
        <HoraireStore
          day="Lundi"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'lundi')[0]
          "
        />
        <HoraireStore
          day="Mardi"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'mardi')[0]
          "
        />
        <HoraireStore
          day="Mercredi"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'mercredi')[0]
          "
        />
        <HoraireStore
          day="Jeudi"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'jeudi')[0]
          "
        />
        <HoraireStore
          day="Vendredi"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'vendredi')[0]
          "
        />
        <HoraireStore
          day="Samedi"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'samedi')[0]
          "
        />
        <HoraireStore
          day="Dimanche"
          class="descriptionStore"
          :horaire="
            this.commande.commerce.horaire.filter(h => h.jour == 'dimanche')[0]
          "
        />
      </q-expansion-item>
      <q-expansion-item
        switch-toggle-side
        expand-separator
        :label="productsLabel"
      >
        <q-list>
          <ArticleInCommande
            v-for="product in commande.products"
            :key="product.id"
            :product="product"
          />
        </q-list>
      </q-expansion-item>
    </q-card-section>
  </q-card>
</template>

<script>
import moment from "moment";
import HoraireStore from "@/components/client/store/HoraireStore";
import ArticleInCommande from "@/components/client/commande/ArticleInCommande";

export default {
  name: "CommandeToPickUp",
  components: { ArticleInCommande, HoraireStore },
  props: {
    commande: Object,
    selectCommand: Function,
    selectActive: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      labelTitle: " faites le : ",
      horaireLabel: "Informations",
      productsLabel: "Articles",
      addressLabel: "Adresse : ",
      pickUpLabel: "à récupérer",
      checked: false
    };
  },
  computed: {
    getTitleCard() {
      return (
        this.commande.commerce.enseigne +
        this.labelTitle +
        moment(String(this.commande.dateCreation)).format("DD/MM/YYYY")
      );
    },
    getAddress() {
      const address = this.commande.commerce.adresse;
      return (
        this.addressLabel +
        address.numeroRue +
        " " +
        address.libelleRue +
        " " +
        address.codePostal +
        " " +
        address.ville
      );
    }
  }
};
</script>

<style scoped></style>
