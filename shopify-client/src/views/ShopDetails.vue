<template>
    <div>
        <p v-if="error">{{ error }}</p>
        <div v-if="shop" class="text-center">
            <b-container id="shop-intro">
                <b-row>
                    <b-col sm="6">
                        <b-row>
                            <h1>{{ shop.name }}</h1>
                        </b-row>
                        <b-row>
                            <h4>{{ shop.description }}</h4>
                        </b-row>
                        <b-row>
                            <b-button>
                                Browse Products
                            </b-button>
                        </b-row>
                    </b-col>
                    <b-col sm="6">
                        <b-row>
                            <b-col sm="12">
                                <b-img v-bind:src="shop.image"></b-img>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </b-container>
            <b-container id="product-catalog">
                <b-col v-for="product in shop.products" :key="product.name">
                    <b-row>
                        {{ product.name }}
                    </b-row>
                    <b-row>
                        {{ product.description}}
                    </b-row>
                    <b-row>
                        {{ product.price }}
                    </b-row>
                </b-col>
            </b-container>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ShopDetails",
        data: function () {
            return {
                shop: null,
                error: "",
            };
        },
        props: ["id"],
        created: function () {
            const vm = this;
            fetch(`/api/shops/${vm.id}`)
                .then((response) => response.json())
                .then((response) => {
                    if (response.status == 404) {
                        throw new Error(response.message);
                    }

                    return response;
                })
                .then((data) => {
                    console.log(data);
                    vm.shop = data
                })
                .catch((error) => {
                    vm.error = error;
                });
        },
    };
</script>

<style scoped></style>
