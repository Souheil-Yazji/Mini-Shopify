import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

import ProductDetails from "../views/ProductDetails.vue";

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
    path: "/app/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
