<template>
  <div class="center">
    <p v-if="error">{{ error }}</p>
    <ProductForm
      v-if="product"
      v-bind:handleSubmit="handleSubmit"
      v-bind:initialValues="product"
      v-bind:shopId="shopId"
    />
  </div>
</template>

<script>
import ProductForm from "@/components/ProductForm.vue";

export default {
  name: "ProductUpdate",
  props: ["shopId", "id"],
  components: {
    ProductForm,
  },
  data: function() {
    return {
      product: null,
      error: null,
    };
  },
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
    handleSubmit: function handleSubmit(updatedProduct, handler) {
      if (!updatedProduct.image) {
        updatedProduct.image = this.product.image;
      }

      fetch(`/api/products/update/${this.id}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(updatedProduct),
      })
        .then((response) => response.json())
        .then((response) => {
          handler(response);
        });
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
