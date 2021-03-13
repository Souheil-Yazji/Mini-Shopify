import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    cart: [

    ]
  },
  mutations: {
    addProduct: (state, product) => {
      state.cart.push(product);
      console.log(state.cart);
    }
  },
  actions: {
  },
  modules: {
  }
})
