<template>
  <q-drawer v-model="openDrawer" side="left" bordered overlay>
    <q-scroll-area class="fit">
      <q-list>
        <template v-for="(menu, index) in menu">
          <q-item
            :key="index"
            clickable
            :active="menu.label === 'Outbox'"
            v-ripple
            @click="goToPage(menu.direction)"
          >
            <q-item-section avatar>
              <q-icon :name="menu.icon" />
            </q-item-section>
            <q-item-section>
              {{ menu.label }}
            </q-item-section>
          </q-item>
          <q-separator :key="'sep' + index" v-if="menu.separator" />
        </template>
      </q-list>
    </q-scroll-area>
  </q-drawer>
</template>

<script>
import router from "@/router";

export default {
  name: "Drawer",

  props: ["menu"],

  methods: {
    // This is not a good way !
    // Use mapAction from "vuex" when you can, in this case, it's not possible
    handleDrawer() {
      this.$store.dispatch("handleDrawer");
    },

    goToPage(redirection) {
      router.push(redirection);
    }
  },

  computed: {
    // This is not a good way !
    // Use mapState from "vuex" when you can, in this case, it's not possible
    openDrawer: {
      get: function() {
        return this.$store.state.openDrawer;
      },
      set: function() {
        this.handleDrawer();
      }
    }
  }
};
</script>
