<template>
  <div>
    <p v-if="error">{{ error }}</p>
    <div class="text-left ml-3">
      <button v-on:click="shopDetails()" type="button" class="btn btn-link">
        &lt; Back to Shop
      </button>
    </div>
    <div v-if="product" class="text-center">
      <b-container>
        <b-row>
          <b-col sm="6">
            <b-row>
              <b-col sm="12">
                <b-img v-bind:src="product.image" fluid></b-img>
              </b-col>
            </b-row>
          </b-col>
          <b-col sm="6">
            <b-row class="text-left">
              <b-col sm="12">
                <h1>{{ product.name }}</h1>
              </b-col>
              <b-col sm="12">
                <p>{{ product.description }}</p>
              </b-col>
              <b-col sm="12">
                <p>Price: {{ product.price }}</p>
              </b-col>
              <b-col sm="12">
                <p>Quantity: {{ product.quantity }}</p>
              </b-col>
              <b-col sm="12">
                <b-button
                  v-bind:to="'/app/shops/' + shopId + '/products/update/' + id"
                  variant="primary"
                  >Update</b-button
                >
              </b-col>
            </b-row>
          </b-col>
        </b-row>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  name: "ProductDetails",
  data: function() {
    return {
      product: null,
      error: "",
    };
  },
  props: ["shopId", "id"],
  created: function() {
    const vm = this;
    fetch(`/api/products/${vm.id}`)
      .then((response) => response.json())
      .then((response) => {
        if (response.status == 404) {
          throw new Error(response.message);
        }

        return response;
      })
      .then((data) => (vm.product = data))
      .catch((error) => {
        vm.error = error;
      });
  },
  methods: {
    shopDetails() {
      this.$router.push(`/app/shops/${this.shopId}`);
    },
  },
};
</script>

<style scoped></style>
