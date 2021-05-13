<template>
  <div>
    <q-dialog v-model="dialogStartEnd">
      <q-card>
        <q-card-section class="q-pb-none">
          <div class="text-h6">
            {{ cardTitle }}
          </div>
        </q-card-section>
        <q-card-section class="q-pb-none">
          <q-form
            class="addressStartEndForm"
            ref="addressStartEndForm"
            greedy
            @submit="setRoad"
          >
            <q-input
              v-model="addressStart"
              :label="startLabel"
              :rules="[val => val != null || '']"
              ref="addressStartRef"
              lazy-rules="ondemand"
              class="addressFormItems"
            />
            <q-input
              v-model="addressEnd"
              :label="endLabel"
              :rules="[val => val != null || '']"
              ref="addressEndRef"
              lazy-rules="ondemand"
              class="addressFormItems"
            />
            <div>
              <q-btn
                :label="validLabel"
                type="submit"
                color="primary"
                class="addressFormItems q-mb-md"
              />
            </div>
          </q-form>
        </q-card-section>
      </q-card>
    </q-dialog>
    <div id="mapContainer" style="height:450px;width:100%" ref="hereMap"></div>
    <!--<q-btn color="primary" label="Ajouter mes bars" @click="setBeerRoad" />
    <q-btn
      color="primary"
      label="Route la plus courte des bars"
      @click="getAddress"
    />-->
    <div class="mapButton q-mb-sm q-mt-sm">
      <q-btn
        color="primary"
        label="Voir les commerces"
        @click="ajouterCommerce"
      />
      <q-btn
        color="primary"
        label="Route la plus courte"
        @click="dialogStartEnd = true"
      />
    </div>
  </div>
</template>

<script>
import { HERE_MAP_API_KEY, HERE_MAP_JAVASCRIPT_KEY } from "@/constants/config";
import axios from "axios";

export default {
  name: "HereMap",
  props: {
    center: Object,
    commandes: Array
  },
  data() {
    return {
      platform: null,
      mapJsKey: HERE_MAP_JAVASCRIPT_KEY,
      mapApiKey: HERE_MAP_API_KEY,
      map: null,
      H: null,
      ui: null,
      group: null,
      routingParameters: null,
      dialogStartEnd: false,
      cardTitle: "Paramétrer votre trajet",
      startLabel: "Point de départ",
      endLabel: "Point d'arrivée",
      addressStart: null,
      addressEnd: null,
      validLabel: "Valider"
    };
  },
  async mounted() {
    this.$q.loading.show({
      spinnerColor: "orange"
    });
    const platform = new window.H.service.Platform({
      apiKey: this.mapJsKey
    });
    this.platform = platform;
    this.initializeHereMap();
  },
  methods: {
    /*ajouterBar() {
      var service = this.platform.getSearchService();
      var mesBars = ["1"];

      mesBars.forEach(address => {
        service.geocode(
          {
            q: address
          },
          result => {
            result.items.forEach(item => {
              this.map.addObject(new this.H.map.Marker(item.position));
            });
          }
        );
      });
    },
    setBeerRoad() {
      var mesBars = [
        "12 rue simons 59000 Lille",
        "1 bis Rue des 3 Couronnes 59000 Lille",
        "10 Rue Royale 59800 Lille",
        "385 Rue Léon Gambetta 59000 Lille",
        "63 Boulevard Victor Hugo 59000 Lille"
      ];
      this.getAddress(mesBars);
    },*/
    addMarkerToGroup(group, coordinate, html) {
      var marker = new this.H.map.Marker(coordinate);
      // add custom data to the marker
      marker.setData(html);
      group.addObject(marker);
    },
    ajouterCommerce() {
      var service = this.platform.getSearchService();
      let address = [];
      let enseignes = [];
      this.commandes.forEach(commande => {
        address.push(
          commande.commerce.adresse.numeroRue +
            " " +
            commande.commerce.adresse.libelleRue +
            " " +
            commande.commerce.adresse.codePostal +
            " " +
            commande.commerce.adresse.ville
        );
        enseignes.push(commande.commerce.enseigne);
      });
      address = [...new Set(address)];
      enseignes = [...new Set(enseignes)];

      this.group = new this.H.map.Group();
      this.map.addObject(this.group);
      this.group.addEventListener(
        "tap",
        evt => {
          console.log(evt);
          var bubble = new this.H.ui.InfoBubble(evt.target.getGeometry(), {
            // read custom data
            content: evt.target.getData()
          });
          // show info bubble
          this.ui.addBubble(bubble);
        },
        false
      );

      address.forEach((address, index) => {
        service.geocode(
          {
            q: address
          },
          result => {
            this.map.addObject(new this.H.map.Marker(result.items[0].position));
            this.addMarkerToGroup(
              this.group,
              result.items[0].position,
              "<div>" + enseignes[index] + " </div>"
            );
          }
        );
      });
    },
    setRoad() {
      this.dialogStartEnd = false;
      let address = [];
      let enseignes = [];
      address.push(this.addressStart);
      enseignes.push("Départ");
      this.commandes.forEach(commande => {
        address.push(
          commande.commerce.adresse.numeroRue +
            " " +
            commande.commerce.adresse.libelleRue +
            " " +
            commande.commerce.adresse.codePostal +
            " " +
            commande.commerce.adresse.ville
        );
        enseignes.push(commande.commerce.enseigne);
      });
      address = [...new Set(address)];
      enseignes = [...new Set(enseignes)];
      address.push(this.addressEnd);
      enseignes.push("Arrivée");
      this.getAddress(address, enseignes);
    },
    getAddress(adresses, enseignes) {
      var service = this.platform.getSearchService();
      this.group = new this.H.map.Group();
      this.map.addObject(this.group);
      this.group.addEventListener(
        "tap",
        evt => {
          console.log(evt);
          var bubble = new this.H.ui.InfoBubble(evt.target.getGeometry(), {
            // read custom data
            content: evt.target.getData()
          });
          // show info bubble
          this.ui.addBubble(bubble);
        },
        false
      );

      let waypoints = {};
      for (const address of adresses) {
        let index = adresses.indexOf(address);
        service.geocode(
          {
            q: address
          },
          result => {
            result.items.forEach(item => {
              if (index == 0) {
                waypoints[
                  "start"
                ] = `${item.position.lat},${item.position.lng}`;
              } else if (index == adresses.length - 1) {
                waypoints["end"] = `${item.position.lat},${item.position.lng}`;
              } else {
                waypoints[
                  "destination" + index
                ] = `${item.position.lat},${item.position.lng}`;
              }
            });
            if (Object.keys(waypoints).length == adresses.length) {
              console.log(waypoints);
              return this.getShortestRoad(waypoints, enseignes);
            }
          }
        );
      }
    },
    getShortestRoad(waypoints, enseignes) {
      axios
        .get("https://wse.ls.hereapi.com/2/findsequence.json", {
          params: {
            ...waypoints,
            mode: "fastest;pedestrian",
            departure: "now",
            apiKey: this.mapJsKey
          }
        })
        .then(response => {
          let result = response.data.results[0];
          let via = [];
          result.waypoints
            .filter(wp => wp.id.includes("destination"))
            .forEach(wp => {
              via.push(`${wp.lat},${wp.lng}`);
            });
          let originWp = result.waypoints.filter(wp =>
            wp.id.includes("start")
          )[0];
          let endWp = result.waypoints.filter(wp => wp.id.includes("end"))[0];
          this.routingParameters = {
            origin: `${originWp.lat},${originWp.lng}`,
            destination: `${endWp.lat},${endWp.lng}`,
            via: new this.H.service.Url.MultiValueQueryParameter(via),

            routingMode: "fast",
            transportMode: "car",

            return: "polyline"
          };
        })
        .catch(() => {
          this.routingParameters = {
            origin: "50.61479,3.04887",
            destination: "50.61479,3.04887",
            via: new this.H.service.Url.MultiValueQueryParameter([
              "50.623,3.06432",
              "50.63725,3.06398",
              "50.63921,3.05859",
              "50.62615,3.04679",
              "50.61479,3.04887"
            ]),

            routingMode: "fast",
            transportMode: "pedestrian",

            return: "polyline"
          };
        })
        .finally(() => {
          console.log(this.routingParameters);
          var router = this.platform.getRoutingService(null, 8);
          // Define a callback function to process the routing response:
          router.calculateRoute(
            this.routingParameters,
            result => this.makeRoad(result, enseignes),
            function(error) {
              alert(error.message);
            }
          );
        });
    },
    makeRoad(result, enseignes) {
      console.log(result);
      if (result.routes.length) {
        result.routes[0].sections.forEach((section, index) => {
          let linestring = this.H.geo.LineString.fromFlexiblePolyline(
            section.polyline
          );

          // Create a polyline to display the route:
          let routeLine = new this.H.map.Polyline(linestring, {
            style: { strokeColor: "blue", lineWidth: 3 }
          });

          this.addMarkerToGroup(
            this.group,
            section.departure.place.location,
            "<div>" + enseignes[index] + " </div>"
          );

          this.addMarkerToGroup(
            this.group,
            section.arrival.place.location,
            "<div>" + enseignes[index + 1] + " </div>"
          );

          // Add the route polyline and the two markers to the map:
          this.map.addObjects([routeLine /*, startMarker, endMarker*/]);

          // Set the map's viewport to make the whole route visible:
          this.map
            .getViewModel()
            .setLookAtData({ bounds: routeLine.getBoundingBox() });
        });
      }
    },
    initializeHereMap() {
      const mapContainer = this.$refs.hereMap;
      this.H = window.H;
      var maptypes = this.platform.createDefaultLayers();
      this.map = new this.H.Map(mapContainer, maptypes.vector.normal.map, {
        zoom: 13,
        center: this.center
      });
      addEventListener("resize", () => this.map.getViewPort().resize());
      new this.H.mapevents.Behavior(new this.H.mapevents.MapEvents(this.map));
      this.ui = new this.H.ui.UI.createDefault(this.map, maptypes);
      this.$q.loading.hide();
    }
  }
};
</script>

<style scoped lang="scss">
.addressFormItems {
  width: 300px;
}

.mapButton {
  text-align: center;
}
</style>
