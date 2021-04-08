<template>
  <div class="center">
    <button v-on:click="shopDetails()"
            type="button"
            id="back-btn"
            class="btn btn-link">
      &lt; Back to Shop
    </button>
    <ProductForm
      v-bind:handleSubmit="handleSubmit"
      v-bind:initialValues="form"
      v-bind:shopId="shopId"
    />
  </div>
</template>

<script>
import ProductForm from "@/components/ProductForm.vue";

export default {
  name: "ProductCreate",
  props: ["shopId"],
  components: {
    ProductForm,
  },
  data: function() {
    return {
      form: {
        name: "",
        description: "",
        price: null,
        quantity: null,
        image: null,
      },
    };
  },
  methods: {
    handleSubmit: function handleSubmit(product, handler) {
      fetch(`/api/shop/${this.shopId}/products/create`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(product),
      })
        .then((response) => response.json())
        .then((response) => {
          handler(response);
        });
    },
    shopDetails() {
        // Navigate to the shop detail page
        this.$router.push(`/app/shops/${this.shopId}`);
    },
  },
};
</script>

<style scoped>

#back-btn {
  padding-left: 0;
}

.center {
  margin-left: 10%;
  margin-right: 10%;
}
</style>
