<template>
  <div class="component">
    <p class="day">{{ msgHoraire }}</p>
  </div>
</template>

<script>
export default {
  name: "HoraireStore",
  props: {
    day: String,
    horaire: Object
  },
  computed: {
    msgHoraire() {
      var heure;
      if (
        this.horaire.fermetureExceptionnelle ||
        this.horaire.heureDebut == "" ||
        this.horaire.heureFin == ""
      ) {
        heure = "Fermé";
      } else {
        heure = this.horaire.heureDebut + " - " + this.horaire.heureFin;
        if (this.horaire.heureDebut2 != "" && this.horaire.heureFin2 != "") {
          heure +=
            " | " + this.horaire.heureDebut2 + " - " + this.horaire.heureFin2;
        }
      }
      return this.day + " : " + heure;
    }
  }
};
</script>

<style scoped lang="scss">
.component {
  display: flex;
  p {
    margin-bottom: 0;
  }
}
</style>
