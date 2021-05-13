<template>
  <q-card class="q-pa-md q-mb-md">
    <apexchart
      type="line"
      height="350"
      :options="chartOptions"
      :series="series"
    ></apexchart>
  </q-card>
</template>

<script>
export default {
  name: "TwoLineChart",
  props: {
    data: Array,
    data2: Array,
    legende: Array,
    serieLabel: String,
    serieLabel2: String,
    title: String
  },
  data() {
    return {
      series: [
        {
          name: this.serieLabel,
          data: this.data
        },
        {
          name: this.serieLabel2,
          data: this.data2
        }
      ],
      chartOptions: {
        chart: {
          height: 350,
          type: "area",
          zoom: {
            enabled: false
          }
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: "smooth"
        },
        title: {
          text: this.title,
          align: "left"
        },
        grid: {
          row: {
            colors: ["#f3f3f3", "transparent"],
            opacity: 0.5
          }
        },
        xaxis: {
          categories: this.legende
        },
        colors: ["#FFA500", "#3299D9"]
      }
    };
  }
};
</script>

<style scoped></style>
