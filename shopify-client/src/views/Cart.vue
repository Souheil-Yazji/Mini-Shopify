<template>
  <div>
    <p v-if="error">{{ error }}</p>
    <div v-else-if="products" class="text-left">
      <b-container>
        <b-row>
            <h2>Shopping Cart</h2>
        </b-row>
        <b-row>
          <b-col class="col-sm-2"></b-col>
          <b-col>
            <p>Product Name</p>
          </b-col>
          <b-col>
            <p>Price</p>
          </b-col>
        </b-row>
        <b-row v-for="product in products" :key="product.name" class="b-row-cart-item">
          <b-col class="col-sm-2">
            <div class="card-img-wrapper">
              <b-img class="card-img" v-bind:src="product.image"></b-img>
            </div>
          </b-col>
          <b-col>
            <h4>{{ product.name }}</h4>
            <p>{{ product.description }}</p>
            <p>Quantity:
              <select class="form-control form-control-sm"
                      v-on:change="editCartQuantityForProduct(product.id, $event.target.value)">
                <option v-for="quantity in product.quantity"
                        :key="product.name + product.id + quantity"
                        :selected="quantity === quantities[product.id]"
                        v-bind:value="quantity">{{ quantity }}</option>
              </select>
            </p>
            <button type="button" class="btn btn-outline-danger btn-sm" v-on:click="removeProductFromCart(product.id)">
              Remove
            </button>
          </b-col>
          <b-col>
            <p>${{ product.price }}</p>
          </b-col>
        </b-row>
        <b-row>
          <b-col>
            <button class="btn btn-success">Checkout</button>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "Cart",
  data: function() {
    return {
      products: [],
      quantities: {},
      error: "",
    };
  },
  created: function() {

    this.quantities = this.$store.state.cart;

    this.getCartProductData();
  },
  methods: {
    getCartProductData() {

      fetch(`/api/products?ids=${Object.keys(this.$store.state.cart).map(Number).join(",")}`)
        .then((response) => response.json())
        .then((data) => (this.products = data))
        .catch((error) => {
          this.error = error;
        });
    },
    removeProductFromCart(productId) {

      this.$store.commit('removeProduct', productId);
      this.getCartProductData();
    },
    editCartQuantityForProduct(productId, newQuantity) {

      this.$store.commit('editProductQuantity', {productId, newQuantity});
      this.quantities = this.$store.state.cart;
    }
  },
};
</script>

<style scoped>

.b-row-cart-item {
  border: 1px solid rgba(0, 0, 0, 0.125);
}

.card-img-wrapper {
  height: 160px;
  width: 160px;
}

</style>
