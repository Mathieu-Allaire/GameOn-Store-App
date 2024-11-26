import { createStore } from "vuex";

const store = createStore({
  state: {
    sharedData: null, // Define the shared state
  },
  mutations: {
    setSharedData(state, payload) {
      state.sharedData = payload; // Mutation to update state
    },
  },
  actions: {
    updateSharedData({ commit }, payload) {
      commit("setSharedData", payload); // Action to commit mutation
      console.log("Store updated:");
      console.log(payload);
    },
  },
  getters: {
    getSharedData: (state) => state.sharedData, // Getter to access state
  },
});

export default store;
