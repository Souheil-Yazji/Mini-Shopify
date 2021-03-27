import Vue from 'vue'
import Vuex from 'vuex'
import Cookies from 'js-cookie';

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
      Cookies.set('cart', state.cart);
    },
    removeProduct: (state, productId) => {
      const cartClone = Object.assign({}, state.cart);
      delete cartClone[productId];

      state.cart = cartClone;
      Cookies.set('cart', state.cart);
    },
    editProductQuantity: (state, { productId, newQuantityNumber }) => {
      state.cart = Object.assign({}, state.cart, {
        [productId]: newQuantityNumber,
      });
      Cookies.set('cart', state.cart);
    },
    clearCart: (state) => {
      state.cart = {};
      Cookies.set('cart', state.cart);
    },
    setCart: (state, cart) => {
      state.cart = cart;
      Cookies.set('cart', state.cart);
    },
  },
  actions: {},
  modules: {},
});
