<template>
    <div class="center">
        <p v-if="error">{{ error }}</p>
        <ShopForm
            v-if="shop"
            v-bind:handleSubmit="handleSubmit"
            v-bind:initialValues="shop"
        />
    </div>
</template>

<script>
    import ShopForm from "@/components/ShopForm.vue";

    export default {
        name: "ShopUpdate",
        props: ["shopId"],
        components: {
            ShopForm,
        },
        data: function() {
            return {
                shop: null,
                error: null,
            };
        },
        created: function() {
            fetch(`/api/shops/${this.shopId}`)
            .then((response) => response.json())
            .then((response) => {
                if (response.status == 404) {
                    throw new Error(response.message);
                }
                return response;
            })
            .then((data) => (
                    this.shop = data
                )
            )
            .catch((error) => {
                this.error = error;
            });
        },
        methods: {
            handleSubmit: function handleSubmit(shop, handler) {
                fetch(
                    `/api/shops/update/${this.shopId}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                    body: JSON.stringify(shop),
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
        margin-left: 10%;
        margin-right: 10%;
    }
</style>
