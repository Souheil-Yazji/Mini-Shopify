<template>
  <div class="center">
    <div v-if="error">
      <div
        class="alert alert-danger"
        role="alert"
        v-bind:key="name"
        v-for="(value, name) in error"
      >
        {{ value }}
      </div>
    </div>
    <b-container>
      <b-row class="border-bottom">
        <b-col class="p-0">
          <p>Product Name</p>
        </b-col>
        <b-col>
          <p>Quantity</p>
        </b-col>
        <b-col>
          <p>Price</p>
        </b-col>
      </b-row>
      <b-row v-for="product in products" :key="product.name">
        <b-col>
          <p>{{ product.name }}</p>
        </b-col>
        <b-col>
          <p>{{ cart[product.id] }}</p>
        </b-col>
        <b-col>
          <p>${{ cart[product.id] * product.price }}</p>
        </b-col>
      </b-row>
      <b-row class="border-top">
        <b-col class="p-0"></b-col>
        <b-col></b-col>
        <b-col> Total: ${{ this.getTotalPrice() }} </b-col>
      </b-row>
    </b-container>
    <div v-if="error">
      <div
        class="alert alert-danger"
        role="alert"
        v-bind:key="name"
        v-for="(value, name) in error"
      >
        {{ value }}
      </div>
    </div>
    <b-form @reset="onReset" @submit="onSubmit" v-if="show">
      <b-form-group
        description="First and last name"
        id="input-group-1"
        label-for="input-1"
        label="Name:"
      >
        <b-form-input
          id="input-1"
          placeholder="Enter name"
          required
          v-model="form.name"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        description="Enter an address"
        id="input-group-2"
        label-for="input-2"
        label="Address:"
      >
        <b-form-input
          id="input-2"
          placeholder="Enter address"
          required
          v-model="form.address"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        description="Enter an phone number"
        id="input-group-2"
        label-for="input-2"
        label="Phone Number:"
      >
        <b-form-input
          id="input-2"
          placeholder="Enter phone number"
          required
          v-model="form.phoneNumber"
        ></b-form-input>
      </b-form-group>

      <div class="mt-5">
        <b-button class="mr-5" type="submit" variant="primary">Submit</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </div>
    </b-form>
  </div>
</template>

<script>
export default {
  name: "Checkout",
  data: function() {
    return {
      form: {
        name: "",
        address: "",
        phoneNumber: "",
      },
      products: [],
      show: true,
      error: "",
    };
  },
  created: function() {
    this.cart = this.$store.state.cart;

    this.getCartProductData();
  },
  methods: {
    onSubmit(event) {
      event.preventDefault();
      try {
        fetch(`/api/checkout`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            checkouts: this.products.map((p) => ({
              id: p.id,
              quantity: this.cart[p.id],
            })),
          }),
        }).then((response) => {
          if (response.status !== 200) {
            this.error = response;
            return;
          }

          this.$store.commit("clearCart");
          this.$router.push("/app/checkout/success");
        });
      } catch (error) {
        this.error = error;
      }
    },
    getCartProductData() {
      fetch(
        `/api/products?ids=${Object.keys(this.$store.state.cart)
          .map(Number)
          .join(",")}`
      )
        .then((response) => response.json())
        .then((data) => (this.products = data))
        .catch((error) => {
          this.error = error;
        });
    },
    onReset(event) {
      event.preventDefault();
      // Reset our form values
      this.form.name = "";
      this.form.address = "";
      this.form.phoneNumber = 0;
      this.error = null;
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
    getTotalPrice() {
      return this.products.reduce(
        (acc, cur) => acc + this.cart[cur.id] * cur.price,
        0
      );
    },
  },
};
</script>

<style scoped>
.center {
  margin-left: 500px;
  margin-right: 500px;
}
</style>
