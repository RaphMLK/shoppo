<template>
    <q-page class="home">
        <p class="title">{{ title }}</p>
        <div class="solde">
            <p>
                {{ solde }}€
            </p>
        </div>
        <div class="montants">
            <div v-for="item in 7" :key="item" class="montant">
                <q-btn v-bind:class="{'selected': selected == item}" :label="montants[item]" @click="select(item)"/>
            </div>
            <div class="montant">
                <q-btn v-if="$q.screen.width > 740" v-bind:class="{'selected': selected == 8}" label="Autre montant" @click="select(8)"/>
                <q-btn v-if="$q.screen.width <= 740" v-bind:class="{'selected': selected == 8}" label="?" @click="select(8)"/>
            </div>
            <div id="saisielibre">
                <q-input v-model="montant" :hidden="hideAutreMontant" type="number" :min=0 placeholder="Autre montant"/>
            </div>
            <q-btn color="primary" id="recharger" :disabled="disabledButton" :label="rechargerLabel" @click="recharger"/>
        </div>
    </q-page>
</template>

<script>
import axios from "axios";
export default {
name: "RechargeSoldeView",
components: { },
props: {
    siretCodeParam: String
},
data() {
    return {
        title: "Rechargement du solde",
        solde: "0.00",
        rechargerLabel: "Recharger",
        selected: -1,
        disabledButton: true,
        hideAutreMontant: true,
        montant: 0,
        montants: [0,10,20,30,40,50,60,70]
    };
},
beforeMount() {
    this.$q.loading.show({
        spinnerColor: "orange"
    });
    this.getSolde();
},
methods: {
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
    recharger() {
        if(this.montant <= 0) {
            this.notifyForm("Veuillez entrer une somme positive", "red");
        } else {
            this.rechargementSolde();
        }
    },
    select(id) {
        this.selected = id;
        this.disabledButton = false;
        if(id == 8) {
            this.hideAutreMontant = false;
        } else {
            this.hideAutreMontant = true;
            this.montant = this.montants[id];
        }
    },
    rechargementSolde() {
        this.$q.loading.show({
            spinnerColor: "orange"
        });
        let colormsg, msgRetour;
        axios
            .post("/rest/client-auth/client/solde/update?amount=" + this.montant)
            .then(() => {
                msgRetour = "Le solde a été ajouté";
                colormsg = "green";
            })
            .catch(e => {
                if (e.response.data) {
                msgRetour = e.response.data;
                } else {
                msgRetour = "Une erreur est survenue";
                }
                colormsg = "red";
            })
            .finally(() => {
                this.notifyForm(msgRetour, colormsg);
                this.getSolde();
            });
    },
    getSolde() {
        this.$q.loading.show({
            spinnerColor: "orange"
        });
        let colormsg, msgRetour;
        axios
            .get("/rest/client-auth/client/solde")
            .then((response) => {
                this.solde = response.data;
            })
            .catch(e => {
                if (e.response.data) {
                msgRetour = e.response.data;
                } else {
                msgRetour = "Impossible de récupérer votre solde";
                }
                colormsg = "red";
                this.notifyForm(msgRetour, colormsg);
            })
            .finally(() => {
                this.$q.loading.hide();
            });
    }
}
};
</script>

<style scoped lang="scss">
.home {
    .title {
        text-align: center;
        font-weight: bold;
        font-size: 35px;
        color: cadetblue;
        margin-top: 0.5em;
    }

    .solde {
        border: 1px white solid;
        width: 35%;
        height: 13em;
        margin: 3em auto 0 auto;
        text-align: center;
        display: flex;
        align-items: center;
        border-radius: 15px;
        background-color: lightgray;
    }

    .solde p {
        margin: 0 auto 0 auto;
        font-size: 20px;
        font-weight: bold;
    }

    .montants {
        width: 70%;
        margin: 1em auto 0 auto;
        text-align: center;
    }

    .montant {
        border: 1px black solid;
        display: inline-block;
        border-radius: 15px;
        width: 12%;
        height: 4em;
        margin: 2em 1.5em 0 1.5em;
        text-align: center;
        align-items: center;
    }

    .montant button {
        width: 100%;
        height: 100%;
        padding: 0px;
        padding: auto 0 auto 0;
        border-radius: 15px;
    }

    #saisielibre {
        display: inline-block;
        width: 25%;
        margin: 2em 1em 0 1em;
    }

    #recharger {
        width: 20%;
    }

    .selected {
        background-color: orange;
    }

    @media (max-width: 1200px) {
        #recharger {
            width: 20%;
            display: block;
            margin: 1em auto 0 auto;
        }
    }

    @media (max-width: 767px) {
        .montant {
            width: 14%;
            height: 4em;
            margin: 2em 1em 0 1em;
        }
    }

    @media (max-width: 683px) {
        #recharger {
            width: 20%;
            display: inline-block;
            margin: 1em auto 0 auto;
        }

        .montant {
            width: 18%;
            height: 4em;
            margin: 1em 1em 0 1em;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
        }

        #saisielibre {
            display: block;
            width: 40%;
            margin: 0 auto 0 auto;
        }
    }

    @media (max-width: 589px) {
        .solde {
            height: 5em;
        }

        #recharger {
            width: 40%;
        }
    }

    @media (max-width: 427px) {
        .title {
            font-size: 30px;
        }
    }

}
</style>
