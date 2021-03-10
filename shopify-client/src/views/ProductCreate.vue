<template>
  <div class="center">
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
  },
};
</script>

<style scoped>
.center {
  margin-left: 500px;
  margin-right: 500px;
}
</style>
