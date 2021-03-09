<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light">

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-form>
            <b-form-input placeholder="Search"></b-form-input>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>

        <b-navbar-nav class="ml-auto">
          <b-navbar-brand to="/app">MiniShopify</b-navbar-brand>
        </b-navbar-nav>

      <b-collapse id="nav-collapse" is-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item to="/app/about">About</b-nav-item>
          <b-nav-item to="/app/login">Login</b-nav-item>
          <b-nav-item to="/app/signup">Sign Up</b-nav-item>
        </b-navbar-nav>

      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
export default {
  name: "Header"
}
</script>

<style scoped>
</style>