<template>
  <div id="app">
    <div id="nav">
      <Header></Header>
      <router-view></router-view>
    </div>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: #2c3e50;
}



</style>

<script>
import Header from "@/components/Header";
import Cookies from 'js-cookie'

export default {
  components: {
    "Header": Header
  },
  created: function() {

    let cartCookieJson = Cookies.getJSON('cart');
    if (cartCookieJson) {
      this.$store.commit('setCart', cartCookieJson);
    } else {
      Cookies.set('cart', {});
    }
  }
}
</script>