import { Category } from "../dto/Category";
import { createStore } from "vuex";

const store = createStore({
  state: {
    sharedSearch: null, // Define the shared state
    sharedCategory: null,
  },
  mutations: {
    setSharedSearch(state, payload) {
      state.sharedSearch = payload; // Mutation to update state
    },
    setSharedCategory(state, payload) {
      state.sharedCategory = payload;
    },
  },
  actions: {
    updateSharedSearch({ commit }, payload) {
      commit("setSharedSearch", payload); // Action to commit mutation
      console.log("Search store updated:");
      console.log(payload);
    },
    updateSharedCategory({ commit }, payload) {
      commit("setSharedCategory", payload);
      console.log("Category Store updated:");
      console.log(payload);
    },
    resetCategory({ commit }) {
      console.log("Reset Category Store");
      commit("setSharedCategory", "");
    },
    resetSearch({ commit }) {
      console.log("Reset Search Store");
      commit("setSharedSearch", "");
    },
  },
  getters: {
    getSharedSearch: (state) => state.sharedSearch, // Getter to access state
    getSharedCategory: (state) => state.sharedCategory,
  },
});

export default store;
