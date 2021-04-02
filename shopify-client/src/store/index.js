import Vue from 'vue'
import Vuex from 'vuex'
import Cookies from 'js-cookie';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    cart: {},
  },
  mutations: {
    addProduct: (state, product) => {
      if (state.cart[product.id] === product.quantity) {
        alert("Cannot Add Product to Cart - Insufficient Stock");
      } else {
        state.cart = Object.assign({}, state.cart, {
          [product.id]: state.cart[product.id] + 1 || 1,
        });
        Cookies.set('cart', state.cart);
      }
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
