import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [createPersistedState({
    storage: window.sessionStorage,
  })],
  state: {
    openDrawer: false,
    userStore: null
  },
  getters: {
    getUserStore: state => {
        return state.userStore
    }
  },
  mutations: {
    HANDLE_DRAWER(state){
      state.openDrawer = !state.openDrawer;
    },
    HANDLE_USER_STORE:(state, userStore) => (
      state.userStore = userStore
    ),
    LOGOUT: (state) => {
      sessionStorage.clear();
      state.userStore = null;
    }
  },
  actions: {
    handleDrawer(context){
      context.commit('HANDLE_DRAWER');
    },
    handleUserStore(context, userStore){
      context.commit("HANDLE_USER_STORE", userStore);
    },
    getUserStore() {
      return this.state.userStore;
    },
    logout: (context) => {
      context.commit("LOGOUT");
  }
  },
  modules: {}
});
