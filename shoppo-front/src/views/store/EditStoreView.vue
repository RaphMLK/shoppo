<template>
  <q-page class="home">
    <div v-if="$q.screen.width <= 640" class="containerMobile">
      <p style="white-space: pre-line">{{ msg }}</p>
    </div>
    <div class="center" v-if="$q.screen.width > 640">
      <EditStore
        ref="children"
        title-card="Modifier Commerce"
        :staffs="staffs"
        :commerce="commerce"
      />
      <q-btn
        color="primary"
        class="editStore"
        icon-right="send"
        :label="valid"
        @click="editStore"
      />
    </div>
  </q-page>
</template>

<script>
import EditStore from "@/components/store/EditStore";
import axios from "axios";
//import axios from "axios";

export default {
  name: "AddProductView",
  components: { EditStore },
  data() {
    return {
      valid: "Modifier",
      rmStaffLabel: "Supprimer",
      nameRequired: "Le nom du magasin est requis",
      descriptionError: "La description ne doit pas dépasser 1500 caractères",
      msg:
        "Cette partie de l'application est bloquée pour les petits écrans (comme les téléphones) :/\nUtilisez un ordinateur pour accéder à cette partie :)",
      commerce: undefined,
      staffs: [],
      numeroRueRequired: "Le numéro de rue est requis",
      rueRequired: "Le nom de rue est requis",
      codePostalRequired: "Le code postal est requis",
      villeRequired: "La ville est requise"
    };
  },
  methods: {
    formatCommerce() {
      const commerceFromChild = this.$refs.children.commerce;
      this.commerce.name = commerceFromChild.name;
      this.commerce.description = commerceFromChild.description;
      this.commerce.lienPhoto = commerceFromChild.lienPhoto;
      const refsFromChild = this.$refs.children.$refs;
      const arrayHoraire = [];
      arrayHoraire.push(refsFromChild.lundi.getHoraire());
      arrayHoraire.push(refsFromChild.mardi.getHoraire());
      arrayHoraire.push(refsFromChild.mercredi.getHoraire());
      arrayHoraire.push(refsFromChild.jeudi.getHoraire());
      arrayHoraire.push(refsFromChild.vendredi.getHoraire());
      arrayHoraire.push(refsFromChild.samedi.getHoraire());
      arrayHoraire.push(refsFromChild.dimanche.getHoraire());
      this.commerce.horaire = arrayHoraire;
    },
    editStore() {
      this.checkValidators().then(success => {
        if (success) {
          this.$q.loading.show({
            spinnerColor: "orange"
          });
          var colormsg, msgRetour;
          this.formatCommerce();
          if (this.commerce.horaire.includes(false)) {
            return;
          }
          axios
            .post("/rest/commercant-auth/commerce/commercant", {
              enseigne: this.commerce.name,
              sirenCode: this.commerce.sirenCode,
              siretCode: this.commerce.siretCode,
              description: this.commerce.description,
              horaire: this.commerce.horaire,
              adresse: this.commerce.adresse
            })
            .then(() => {
              msgRetour = "Le magasin a été modifié";
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
    getStore() {
      var colormsg, msgRetour;
      axios
        .get("/rest/commercant-auth/commerce/commercant", {})
        .then(response => {
          msgRetour = "Le magasin a été récupéré";
          colormsg = "green";
          this.commerce = response.data.commerce;
          if (this.commerce.adresse == null) {
            let adresse = {
              numeroRue: null,
              libelleRue: null,
              codePostal: null,
              ville: null
            };
            this.commerce.adresse = adresse;
          }
          this.staffs = this.commerce.commercants;
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
    },
    checkValidators() {
      const refChildren = this.$refs.children.$refs;
      return refChildren.editStoreForm.validate().then(success => {
        if (!success) {
          if (!refChildren.nameInput.validate()) {
            this.notifyForm(this.nameRequired, "red");
          } else if (!refChildren.descriptionInput.validate()) {
            this.notifyForm(this.descriptionError, "red");
          }
          return success;
        } else {
          return refChildren.editAddressForm.validate().then(success2 => {
            if (!success2) {
              if (!refChildren.numeroRueInput.validate()) {
                this.notifyForm(this.numeroRueRequired, "red");
              } else if (!refChildren.rueInput.validate()) {
                this.notifyForm(this.rueRequired, "red");
              } else if (!refChildren.codePostalInput.validate()) {
                this.notifyForm(this.codePostalRequired, "red");
              } else if (!refChildren.villeInput.validate()) {
                this.notifyForm(this.villeRequired, "red");
              }
            }
            return success2;
          });
        }
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
  },
  beforeMount() {
    this.getStore();
  }
};
</script>

<style scoped lang="scss">
.home {
  display: flex;
  .center {
    width: 96%;
    height: 30%;
    position: sticky;
    top: 30%;
    margin-left: auto;
    margin-right: auto;
    .editStore {
      width: 80%;
      margin-top: 2%;
      margin-left: 10%;
      margin-right: 10%;
    }
  }
  .containerMobile {
    background-color: darkorange;
    color: white;
    text-align: center;
    padding-top: 5%;
    font-size: 25px;
  }
}
</style>
