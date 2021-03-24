<template>
  <div id="home">
    <div>
      <b-jumbotron :fluid="true" :container-fluid="true">

        <template #header>
          <img alt="MiniShopify logo" src="../assets/MiniShopLogo.png">
        </template>

        <template #lead>
          Welcome to the one-stop-shop of many shops!
        </template>

        <hr class="my-4">

        <p>
          Looking for a particular shop or product?
          <b-button variant="outline-dark" :to="'/app/shops/list'">Browse Here</b-button>

        </p>
        <p>looking to open your own shop?
          <b-button variant="outline-dark" :to="'/app/shops/create'">Start Here</b-button>

        </p>
      </b-jumbotron>
    </div>

  </div>

</template>

<script>
</script>

<style>

#home {
  margin: auto;
  width: 50%;
  text-align: center;
}

</style>
