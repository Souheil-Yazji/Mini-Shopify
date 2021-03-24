<template>
    <div class="center">
        <ShopForm
            v-bind:handleSubmit="handleSubmit"
            v-bind:initialValues="shop"
        />
    </div>
</template>

<script>
    import ShopForm from "@/components/ShopForm.vue";

    export default {
        name: "ShopCreate",
        components: {
            ShopForm,
        },
        data: function() {
            return {
                shop: {
                    name: "",
                    description: "",
                    image: null,
                    owner: { name: "TEST_USER", email: "TEST_EMAIL", password: "123" }, /* TODO - replace with current user */
                },
            };
        },
        methods: {
            handleSubmit: function handleSubmit(shop, handler) {
                fetch(`/api/shops/create`, {
                    method: "POST",
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
