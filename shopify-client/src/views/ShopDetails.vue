<template>
    <div>
        <p v-if="error">{{ error }}</p>
        <div v-if="shop">
            <div id="shop-details-wrapper">
                <b-container id="shop-details">
                    <b-row class="text-left">
                        <b-col sm="6">
                            <b-col sm="12">
                                <h1>{{ shop.name }}</h1>
                            </b-col>
                            <b-col sm="12">
                                <h4>{{ shop.description }}</h4>
                            </b-col>
                            <b-col sm="12">
                                <b-button v-on:click="updateShop()" variant="primary">
                                    Update Shop
                                </b-button>
                            </b-col>
                            <b-col sm="12">
                                <b-button v-on:click="scrollToProductCatalog({behavior: 'smooth'})" variant="primary">
                                    Browse Products
                                </b-button>
                            </b-col>
                        </b-col>
                        <b-col sm="6">
                            <b-col sm="12">
                                <b-img id="shop-image" v-bind:src="shop.image"></b-img>
                            </b-col>
                        </b-col>
                    </b-row>
                </b-container>
            </div>
            <div id="productCatalogWrapper">
                <b-container id="productCatalog" ref="productCatalog">
                    <h2>Product Catalog</h2>
                    <div v-if="shop.products.length">
                        <b-row cols="4">
                            <b-col v-for="product in shop.products" :key="product.name">
                                <b-card
                                        v-bind:title="product.name"
                                        v-bind:img-src="product.image"
                                        v-bind:img-alt="product.name"
                                        img-top
                                        tag="article"
                                        style="max-width: 20rem;"
                                        class="mb-2"
                                >
                                    <b-card-text>
                                        {{ product.description }}
                                    </b-card-text>

                                    <b-button v-bind:href="'/app/products/' + product.id" variant="info" class="card-button">Details</b-button>
                                    <b-button href="#" variant="primary" class="card-button">Add to Cart</b-button>
                                </b-card>
                            </b-col>
                        </b-row>
                    </div>
                    <p v-else>No products in shop</p>
                </b-container>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ShopDetails",
        data: function () {
            return {
                shop: null,
                error: ""
            };
        },
        methods: {
            scrollToProductCatalog(options) {
                const el = this.$refs.productCatalog;

                if (el) {
                    el.scrollIntoView(options);
                }
            },
            updateShop() {
                // Redirect to Update Store Page
                this.$router.push(`/app/shops/${this.id}/update`);
            }
        },
      props:["id"],
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
                .then((data) => (vm.shop = data))
                .catch((error) => {
                    vm.error = error;
                });
        },
    };
</script>

<style scoped>

    #shop-details-wrapper {
        background-color: #fbf7ed;
    }

    #shop-details {
        height: 680px;
    }

    #shop-image {
        width: 100%;
    }

    .card-button {
        margin-right: 0.5em;
    }
</style>
