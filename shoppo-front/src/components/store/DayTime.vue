<template>
  <q-item class="info" :id="dayTimer">
    <q-item-section>
      <label class="labelPlacement">{{ dayTimer }}</label>
      <q-checkbox
        v-model="horaireData.fermetureExceptionnelle"
        :label="closeLabel"
      />
    </q-item-section>
    <q-item-section v-if="!horaireData.fermetureExceptionnelle">
      <q-input
        filled
        v-model="horaireData.heureDebut"
        mask="time"
        :rules="[val => (val != null) | '']"
        class="inputInfo inputPlacement"
        ref="heureDebutInput"
      >
        <template v-slot:append>
          <q-icon name="access_time" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-time
                v-model="horaireData.heureDebut"
                :minute-options="[0, 15, 30, 45]"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-time>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </q-item-section>
    <q-item-section v-if="!horaireData.fermetureExceptionnelle">
      <q-input
        filled
        v-model="horaireData.heureFin"
        mask="time"
        :rules="[val => (val != null) | '']"
        class="inputInfo inputPlacement"
        ref="heureFinInput"
      >
        <template v-slot:append>
          <q-icon name="access_time" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-time
                v-model="horaireData.heureFin"
                :minute-options="[0, 15, 30, 45]"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-time>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </q-item-section>
    <q-item-section v-if="!horaireData.fermetureExceptionnelle">
      <q-checkbox v-model="interruption" :label="interruptionLabel" />
    </q-item-section>
    <q-item-section v-if="!horaireData.fermetureExceptionnelle & interruption">
      <q-input
        filled
        v-model="horaireData.heureDebut2"
        mask="time"
        :rules="['time']"
        class="inputInfo inputPlacement"
        ref="heureDebut2Input"
      >
        <template v-slot:append>
          <q-icon name="access_time" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-time
                v-model="horaireData.heureDebut2"
                :minute-options="[0, 15, 30, 45]"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-time>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </q-item-section>
    <q-item-section v-if="!horaireData.fermetureExceptionnelle & interruption">
      <q-input
        filled
        v-model="horaireData.heureFin2"
        mask="time"
        :rules="['time']"
        class="inputInfo inputPlacement"
        ref="heureFin2Input"
      >
        <template v-slot:append>
          <q-icon name="access_time" class="cursor-pointer">
            <q-popup-proxy transition-show="scale" transition-hide="scale">
              <q-time
                v-model="horaireData.heureFin2"
                :minute-options="[0, 15, 30, 45]"
              >
                <div class="row items-center justify-end">
                  <q-btn v-close-popup label="Close" color="primary" flat />
                </div>
              </q-time>
            </q-popup-proxy>
          </q-icon>
        </template>
      </q-input>
    </q-item-section>
  </q-item>
</template>

<script>
export default {
  name: "DayTime",
  props: {
    dayTimer: String,
    horaire: Object
  },
  data() {
    return {
      horaireData: this.horaire,
      day: this.dayTimer,
      open: true,
      closeLabel: "Fermé",
      interruption:
        this.horaire.heureDebut2 != "" && this.horaire.heureFin2 != "",
      interruptionLabel: "Interruption",
      emptyError: "Au moins un des champs est vide"
    };
  },
  methods: {
    getHoraire() {
      if (
        this.horaireData.fermetureExceptionnelle != undefined &&
        !this.horaireData.fermetureExceptionnelle
      ) {
        if (
          !this.$refs.heureDebutInput.validate() ||
          !this.$refs.heureFinInput.validate()
        ) {
          this.notifyForm(this.emptyError, "red");
          return false;
        }
        if (this.interruption) {
          if (
            !this.$refs.heureDebut2Input.validate() ||
            !this.$refs.heureFin2Input.validate()
          ) {
            this.notifyForm(this.emptyError, "red");
            return false;
          }
        }
      }
      if (!this.interruption) {
        this.horaireData.heureDebut2 = "";
        this.horaireData.heureFin2 = "";
      }
      return this.horaireData;
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
    }
  }
};
</script>

<style scoped lang="scss">
.info {
  display: flex;
  .inputInfo {
    width: 100%;
    margin-left: 1%;
    margin-right: 1%;
  }
  .inputPlacement {
    width: 33%;
  }
}
</style>
