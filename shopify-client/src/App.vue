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

#nav a {
  font-weight: bold;
  color: #2c3e50;
}


</style>

<script>
import Header from "@/components/Header";
export default {
  components: {
    "Header": Header}
}
</script>