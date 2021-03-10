import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

import ProductDetails from "../views/ProductDetails.vue";
import ProductCreate from "../views/ProductCreate.vue";
import ProductUpdate from "../views/ProductUpdate";
import ShopList from "../views/ShopList.vue";

import ShopDetails from "../views/ShopDetails.vue";

import ShopCreate from "../views/ShopCreate.vue";
import ShopUpdate from "../views/ShopUpdate.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/app",
    name: "Home",
    component: Home,
  },
  {
    path: "/app/products/:id",
    name: "ProductDetails",
    component: ProductDetails,
    props: true,
  },
  {
    path: "/app/shop/:shopId/products/create",
    name: "ProductCreate",
    component: ProductCreate,
    props: true,
  },
  {
    path: "/app/products/update/:id",
    name: "ProductUpdate",
    component: ProductUpdate,
    props: true,
  },
  {
    path: "/app/shops/create",
    name: "ShopCreate",
    component: ShopCreate,
  },
  {
    path: "/app/shops/:shopId/update",
    name: "ShopUpdate",
    component: ShopUpdate,
    props: true,
  },
  {
    path: "/app/shops",
    name: "ShopDetails",
    component: ShopDetails,
    props: route => ({ query: route.query.id }),
  },
  {
    path: '/app/shops/list',
    name: 'ShopList',
    component: ShopList,
  },
  {
    path: "/app/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
