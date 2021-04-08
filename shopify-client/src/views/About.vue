<template>
  <div id="about">
    <h1>
      <div id="Logo">
        <img alt="MiniShopify logo" src="../assets/MiniShopLogo.png">
      </div>
    </h1>
    <h3>About Us</h3>
    <div>
      <p>We are a group of 5 Software Engineering Students that have designed and implemented this system for SYSC 4806
        at Carleton University.</p>
      <p>This system allows merchants to create a new shop by filling in a form containing the name of the shop and the
        categories/tags relevant to the shop. The merchant can upload products to populate the shop along with a
        description, picture, and inventory number. Customers can find a shop by looking up the name of the shop or
        searching by category/tag. Once they find the shop the want, they can browse through the product catalog of the
        shop, and can then decide to purchase one or many products by putting them in the Shopping Cart and proceeding
        to Checkout.</p>
      <p>The purchase is only simulated. This system is not meant to host real stores and is presented "as is".</p>
    </div>
    <h3>Statistics</h3>
    <ul>
      <p>We host:</p>
      <li>{{ numShops }} Shops</li>
      <li>{{ numProducts }} Products</li>
      <li>{{ numUsers }} Merchants</li>
    </ul>
    <h3>Authors</h3>
    <ul>
      <li>Hoang Bui</li>
      <li>Michael Fan</li>
      <li>Derek Shao</li>
      <li>David Wang</li>
      <li>Souheil Yazji</li>
    </ul>
    <ul>
      <p>Powered by Vue.Js</p>
    </ul>
  </div>
</template>

<script>
  export default {
    name: "About",
    data: function() {
        return {
            numShops: 0,
            numProducts: 0,
            numUsers: 0,
        };
    },
    created: function() {
      fetch(`/api/stats`)
      .then((response) => response.json())
      .then((response) => {
          if (response.status == 404) {
            throw new Error(response.message);
          }

          return response;
      })
      .then((data) => {
          this.numShops = data.numShops;
          this.numProducts = data.numProducts;
          this.numUsers = data.numUsers;
      });
    },
  };
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#about {
  margin: auto;
  width: 50%;
  padding: 10px;
}

#Logo {
  margin: auto;
  width: 50%;
  text-align: center;
}

h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
