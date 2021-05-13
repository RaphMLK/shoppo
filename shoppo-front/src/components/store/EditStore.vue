<template>
  <q-card class="window">
    <q-card-section class="title">{{ titleCard }}</q-card-section>
    <q-card-section class="form">
      <q-form class="edit-store" ref="editStoreForm" greedy>
        <q-input
          v-model="commerce.name"
          :label="nameLabel"
          type="text"
          :rules="[val => (val != null && val.length !== 0) || '']"
          ref="nameInput"
          class="inputStore"
          lazy-rules="ondemand"
        >
          <template v-slot:prepend>
            <q-icon name="subject" />
          </template>
        </q-input>

        <q-img
          v-if="commerce.lienPhoto != null"
          :src="'data:image/png;base64,' + commerce.lienPhoto"
          class="imageStore"
        />

        <br v-if="commerce.lienPhoto != null" />

        <q-uploader
          label="Logo"
          accept=".jpg, image/*"
          @rejected="onRejected"
          @added="uploadFiles"
          class="inputImage"
          auto-upload
        />

        <br />

        <q-input
          v-model="commerce.description"
          type="textarea"
          :label="descriptionLabel"
          class="inputStore"
          ref="descriptionInput"
          outlined
          :rules="[val => testDescription(val) || '']"
        />
      </q-form>

      <q-expansion-item switch-toggle-side expand-separator :label="timerLabel">
        <q-list separator>
          <DayTime
            dayTimer="Lundi"
            ref="lundi"
            :horaire="this.commerce.horaire.filter(h => h.jour == 'lundi')[0]"
          />
          <DayTime
            dayTimer="Mardi"
            ref="mardi"
            :horaire="this.commerce.horaire.filter(h => h.jour == 'mardi')[0]"
          />
          <DayTime
            dayTimer="Mercredi"
            ref="mercredi"
            :horaire="
              this.commerce.horaire.filter(h => h.jour == 'mercredi')[0]
            "
          />
          <DayTime
            dayTimer="Jeudi"
            ref="jeudi"
            :horaire="this.commerce.horaire.filter(h => h.jour == 'jeudi')[0]"
          />
          <DayTime
            dayTimer="Vendredi"
            ref="vendredi"
            :horaire="
              this.commerce.horaire.filter(h => h.jour == 'vendredi')[0]
            "
          />
          <DayTime
            dayTimer="Samedi"
            ref="samedi"
            :horaire="this.commerce.horaire.filter(h => h.jour == 'samedi')[0]"
          />
          <DayTime
            dayTimer="Dimanche"
            ref="dimanche"
            :horaire="
              this.commerce.horaire.filter(h => h.jour == 'dimanche')[0]
            "
          />
        </q-list>
      </q-expansion-item>

      <q-expansion-item
        switch-toggle-side
        expand-separator
        :label="addressLabel"
      >
        <q-form class="edit-address" ref="editAddressForm" greedy>
          <div class="rue row">
            <q-input
              v-model="commerce.adresse.numeroRue"
              :label="numeroRueLabel"
              type="text"
              :rules="[val => (val != null && val.length !== 0) || '']"
              ref="numeroRueInput"
              class="inputStore col-2 q-pr-md q-pl-md"
              lazy-rules="ondemand"
            >
            </q-input>

            <q-input
              v-model="commerce.adresse.libelleRue"
              :label="rueLabel"
              type="text"
              :rules="[val => (val != null && val.length !== 0) || '']"
              ref="rueInput"
              class="inputStore col-10 q-pr-md"
              lazy-rules="ondemand"
            >
            </q-input>
          </div>

          <div class="ville row">
            <q-input
              v-model="commerce.adresse.codePostal"
              :label="codePostalLabel"
              type="text"
              :rules="[val => (val != null && val.length !== 0) || '']"
              ref="codePostalInput"
              class="inputStore col-2 q-pr-md q-pl-md"
              lazy-rules="ondemand"
              :mask="maskCodePostal"
            >
            </q-input>

            <q-input
              v-model="commerce.adresse.ville"
              :label="villeLabel"
              type="text"
              :rules="[val => (val != null && val.length !== 0) || '']"
              ref="villeInput"
              class="inputStore col-10 q-pr-md"
              lazy-rules="ondemand"
            >
            </q-input>
          </div>
        </q-form>
      </q-expansion-item>

      <q-expansion-item switch-toggle-side expand-separator :label="staffLabel">
        <div class="row">
          <q-input
            v-model="staffToAdd"
            type="text"
            :label="staffToAddLabel"
            class="staffToAdd"
            ref="staffToAddInput"
            :rules="[
              val =>
                val != null &&
                /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(
                  val
                )
            ]"
          >
            <template v-slot:prepend>
              <q-icon name="person" />
            </template>
            <template v-slot:append>
              <q-icon name="add" @click="addStaff()" class="iconHover" />
            </template>
          </q-input>
        </div>

        <q-list separator>
          <q-item
            v-for="staff in staffsData"
            v-bind:key="staff"
            ref="rm_{{staff}}"
          >
            <q-item-section avatar>
              <q-icon name="remove" @click="rmStaff(staff)" class="iconHover" />
            </q-item-section>
            <q-item-section>{{ staff }}</q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
    </q-card-section>
  </q-card>
</template>

<script>
import DayTime from "@/components/store/DayTime";
import axios from "axios";
export default {
  name: "EditStore",
  components: { DayTime },
  props: {
    titleCard: String,
    staffs: Array,
    commerce: Object
  },
  data() {
    return {
      staffToAdd: null,
      nameLabel: "Nom",
      imageLabel: "Sélectionner l'image pour représenter le magasin",
      descriptionLabel: "Description",
      timerLabel: "Horaire d'activités",
      staffLabel: "Staff",
      staffToAddLabel: "Ajouter un collab'",
      emailCollabRequired: "Le champs doit contenir une adresse mail",
      staffsData: this.staffs,
      addressLabel: "Adresse",
      numeroRueLabel: "Numero de rue",
      rueLabel: "Rue",
      codePostalLabel: "Code postal",
      villeLabel: "Ville",
      maskCodePostal: "#####"
    };
  },
  methods: {
    addStaff() {
      if (!this.$refs.staffToAddInput.validate()) {
        this.notifyForm(this.emailCollabRequired, "red");
        return;
      }
      var colormsg, msgRetour;
      console.log(this.commerce);
      axios
        .post("/rest/admin/commerce/addCommercant", {
          siret: this.commerce.siretCode,
          email: this.staffToAdd
        })
        .then(() => {
          msgRetour = this.staffToAdd + " a bien été ajouté";
          colormsg = "green";
          this.staffsData.push(this.staffToAdd);
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
    },
    rmStaff(staff) {
      var colormsg, msgRetour;
      axios
        .post("/rest/admin/commerce/rmCommercant", {
          siret: this.commerce.siretCode,
          email: staff
        })
        .then(() => {
          msgRetour = this.staffToAdd + " a bien été viré ! "; //le galopin !
          colormsg = "green";
          this.staffsData = this.staffsData.filter(collab => collab != staff);
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
    },
    uploadFiles(files) {
      this.imageParam = null;
      let component = this;
      const file = files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function() {
        const fileSrc = reader.result;
        component.imageFile = fileSrc.substr(fileSrc.indexOf(",") + 1);
      };
    },
    onRejected() {
      this.$q.notify({
        message: "Le fichier n'a pas le bon format",
        color: "red",
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    },
    notifyForm(msg, color) {
      this.$q.loading.hide();
      this.$q.notify({
        message: msg,
        color: color,
        actions: [{ label: "Fermer", color: "white", handler: () => {} }]
      });
    },
    testDescription(val) {
      return val == null || (val != null && val.length <= 1500);
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
        }
        return success;
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
    padding-bottom: 0;
  }
  .edit-store {
    width: 100%;
    height: 100%;
    align-content: center;
    .inputStore {
      width: 96%;
      margin-left: auto;
      margin-right: auto;
    }
    .imageStore {
      height: 20%;
      width: 20%;
      margin-right: auto;
      margin-left: auto;
      display: block;
    }
    .inputImage {
      width: 35%;
      margin-left: auto;
      margin-right: auto;
    }
  }
  .staffToAdd {
    width: 50%;
    margin-right: 1%;
  }
  .iconHover:hover {
    color: orange;
  }
  .rmStaff {
    height: 90%;
  }
}
</style>
