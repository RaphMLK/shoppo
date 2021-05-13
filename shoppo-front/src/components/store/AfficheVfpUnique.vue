<template>
  <q-card class="cardProduct">
    <q-dialog v-model="deleteShow" class="dialogDeleteProduct">
      <q-card>
        <q-card-section class="row items-center q-pb-none">
          <q-avatar icon="clear" color="primary" text-color="white" />
          <span class="q-ml-sm"
            >Voulez-vous vraiment retirer le produit "{{ name }}" des vfp
            ?</span
          >
        </q-card-section>
        <q-card-actions align="right">
          <q-btn flat :label="cancelLabel" color="primary" v-close-popup />
          <q-btn
            flat
            :label="validLabel"
            color="primary"
            v-close-popup
            @click="removeVfp"
          />
        </q-card-actions>
      </q-card>
    </q-dialog>
    <img
      src="https://upload.wikimedia.org/wikipedia/commons/5/5f/Pain_au_chocolat_Luc_Viatour.jpg"
      class="imageProduct"
    />
    <div class="infoProduct">
      <p class="nameProduct">{{ name }}</p>
      <input
        class="descriptionVfp"
        v-if="isAlreadyAdded == false"
        :disabled="selected"
        type="text"
        v-model="descriptionVfp"
        placeholder="Description..."
      />
      <input
        class="descriptionVfp"
        v-if="isAlreadyAdded == true"
        :disabled="true"
        type="text"
        v-model="description"
      />
    </div>
    <div class="actions">
      <q-btn
        v-if="isAlreadyAdded == true"
        round
        color="primary"
        icon="clear"
        class="actionIcons"
        @click="deleteShow = true"
      />
      <label v-if="isAlreadyAdded == false" class="containercheckbox">
        <input
          type="checkbox"
          v-model="selected"
          @change="changeSelected(id, descriptionVfp)"
        />
        <span class="checkmark"></span>
      </label>
    </div>
  </q-card>
</template>

<script>
export default {
  name: "AfficheVfpUnique",
  components: {},
  props: {
    id: Number,
    name: String,
    prix: Number,
    stock: Number,
    image: String,
    description: String,
    isAlreadyAdded: Boolean
  },
  data() {
    return {
      stockComponent: this.stock,
      deleteShow: false,
      titleDialog: "Retirer des VFP",
      cancelLabel: "Annuler",
      validLabel: "Confirmer",
      selected: false,
      descriptionVfp: ""
    };
  },
  methods: {
    removeVfp() {
      this.$parent.$parent.removeVfp(this.id);
    },
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
    },
    changeSelected(id, descriptionVfp) {
      console.log(id);
      this.$parent.$parent.addOrDeleteMapVfp(id, descriptionVfp);
    }
  }
};
</script>

<style scoped lang="scss">
/* The containercheckbox */
.containercheckbox {
  display: block;
  position: relative;
  padding-left: 35px;
  margin-bottom: 12px;
  cursor: pointer;
  font-size: 22px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  margin: auto;
  text-align: center;
  height: 10%;
  width: 10%;
}

/* Hide the browser's default checkbox */
.containercheckbox input {
  position: absolute;
  opacity: 0;
  cursor: pointer;
  height: 0;
  width: 0;
}

/* Create a custom checkbox */
.checkmark {
  position: absolute;
  top: 0;
  left: 0;
  height: 25px;
  width: 25px;
  background-color: white;
  border: 2px solid orange;
}

/* On mouse-over, add a grey background color */
.containercheckbox:hover input ~ .checkmark {
  background-color: orange;
}

/* When the checkbox is checked, add a blue background */
.containercheckbox input:checked ~ .checkmark {
  background-color: orange;
}

/* Create the checkmark/indicator (hidden when not checked) */
.checkmark:after {
  content: "";
  position: absolute;
  display: none;
}

/* Show the checkmark when checked */
.containercheckbox input:checked ~ .checkmark:after {
  display: block;
}

/* Style the checkmark/indicator */
.containercheckbox .checkmark:after {
  left: 9px;
  top: 5px;
  width: 5px;
  height: 10px;
  border: solid white;
  border-width: 0 3px 3px 0;
  -webkit-transform: rotate(45deg);
  -ms-transform: rotate(45deg);
  transform: rotate(45deg);
}

.window {
  .title {
    font-size: 2em;
    text-align: center;
    padding-bottom: 0;
  }

  .center {
    text-align: center;
  }
}

.cardProduct {
  margin-top: 10px;
  height: 100px;
  display: flex;

  .descriptionVfp {
    display: block;
    margin: 0px;
    width: 100%;
    height: 40%;
  }

  .imageProduct {
    width: 20%;
    height: 100%;
    object-fit: contain;
    float: left;
  }

  .infoProduct {
    width: 70%;
    margin-left: 1em;

    .nameProduct {
      font-weight: bold;
      font-size: 25px;
    }

    .noDescription {
      font-style: italic;
    }
  }

  .quantityStock {
    width: 20%;
  }

  .actions {
    margin: auto;
    width: 30%;
    text-align: center;

    .actionIcons {
      height: 50%;
      margin-left: 10px;
    }
  }
}
</style>
