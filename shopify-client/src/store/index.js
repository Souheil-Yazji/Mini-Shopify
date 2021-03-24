import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    cart: {},
  },
  mutations: {
    addProduct: (state, productId) => {
      state.cart = Object.assign({}, state.cart, {
        [productId]: state.cart[productId] + 1 || 1,
      });
    },
    removeProduct: (state, productId) => {
      const cartClone = Object.assign({}, state.cart);
      delete cartClone[productId];

      state.cart = cartClone;
    },
    editProductQuantity: (state, { productId, newQuantityNumber }) => {
      state.cart = Object.assign({}, state.cart, {
        [productId]: newQuantityNumber,
      });
    },
    clearCart: (state) => {
      state.cart = {};
    },
  },
  actions: {},
  modules: {},
});
