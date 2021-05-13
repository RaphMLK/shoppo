<template>
  <q-card class="window">
    <q-card-section class="title">{{ titleCard }}</q-card-section>
    <q-card-section class="form">
      <q-form class="edit-product" ref="addProductForm" greedy>
        <div class="info">
          <q-input
            v-model="name"
            :label="nameLabel"
            type="text"
            :rules="[val => (val != null && val.length !== 0) || '']"
            ref="nameInput"
            class="nameProduct inputInfo"
            lazy-rules="ondemand"
          >
            <template v-slot:prepend>
              <q-icon name="subject" />
            </template>
          </q-input>

          <q-input
            v-model.number="prix"
            :label="priceLabel"
            type="number"
            :rules="[val => (val != null && val >= 0) || '']"
            ref="priceInput"
            class="priceProduct inputInfo"
            lazy-rules="ondemand"
          >
            <template v-slot:append>
              <q-icon name="euro" />
            </template>
          </q-input>

          <q-input
            v-model.number="stock"
            :label="stockLabel"
            type="number"
            :rules="[
              val => (val != null && val >= 0 && Number.isInteger(val)) || ''
            ]"
            ref="stockInput"
            class="stockProduct inputInfo"
            lazy-rules="ondemand"
          >
            <template v-slot:prepend>
              <q-icon name="category" />
            </template>
          </q-input>
        </div>

        <q-img
          v-if="imageURL != null"
          :src="'data:image/png;base64,' + imageURL"
          class="imageProduct"
        />

        <br v-if="imageParam != null" />

        <q-uploader
          label="Image du produit"
          accept=".jpg, image/*"
          @rejected="onRejected"
          @added="uploadFiles"
          class="inputImage"
          auto-upload
        />

        <br />

        <q-input
          v-model="description"
          type="textarea"
          :label="descriptionLabel"
          class="description"
          ref="descriptionInput"
          outlined
          :rules="[
            val => val == null || val.length <= 1500 || descriptionError
          ]"
        />
      </q-form>
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: "EditProduct",
  props: {
    titleCard: String,
    nameParam: {
      type: String,
      default: null
    },
    prixParam: {
      type: Number,
      default: null
    },
    stockParam: {
      type: Number,
      default: 0
    },
    descriptionParam: {
      type: String,
      default: null
    },
    imageParam: {
      type: String,
      default: null
    }
  },
  data() {
    return {
      name: this.nameParam,
      nameLabel: "Nom",
      prix: this.prixParam,
      priceLabel: "Prix",
      stock: this.stockParam,
      stockLabel: "Stock",
      imageFile: null,
      imageURL: this.imageParam,
      imageLabel: "Sélectionner l'image pour représenter le magasin",
      description: this.descriptionParam,
      descriptionLabel: "Description",
      descriptionError: "La description ne doit pas dépasser 1500 caractères",
      produit: this.$parent.produit
    };
  },
  methods: {
    uploadFiles(files) {
      this.imageParam = null;
      let component = this;
      const file = files[0];
      const reader = new FileReader();
      reader.readAsDataURL(file);
      //reader.onerror = err => this.onRejected(err);
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
  .edit-product {
    width: 100%;
    height: 100%;
    align-content: center;
    .info {
      display: flex;
      .inputInfo {
        margin-left: 1%;
        margin-right: 1%;
      }
      .nameProduct {
        width: 44%;
      }
      .priceProduct {
        width: 25%;
      }
      .stockProduct {
        width: 25%;
      }
    }
    .imageProduct {
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
    .description {
      width: 96%;
      margin-left: auto;
      margin-right: auto;
    }
  }
}
</style>
