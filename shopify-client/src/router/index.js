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

import Cart from "../views/Cart.vue";
import Checkout from "../views/Checkout.vue";
import CheckoutSuccess from "../views/CheckoutSuccess.vue";

import Login from "../views/Login"
import SignUp from "../views/SignUp"

Vue.use(VueRouter);

const routes = [
  {
    path: "/app",
    name: "Home",
    component: Home,
  },
  {
    path: "/app/shops/:shopId/products/create",
    name: "ProductCreate",
    component: ProductCreate,
    props: true,
  },
  {
    path: "/app/shops/:shopId/products/:id",
    name: "ProductDetails",
    component: ProductDetails,
    props: true,
    exact: true,
  },
  {
    path: "/app/shops/:shopId/products/update/:id",
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
    path: "/app/shops/list",
    name: "ShopList",
    component: ShopList,
  },
  {
    path: "/app/shops/:id",
    name: "ShopDetails",
    component: ShopDetails,
    props: true,
  },
  {
    path: "/app/cart",
    name: "Cart",
    component: Cart,
  },
  {
    path: "/app/checkout",
    name: "Checkout",
    component: Checkout,
  },
  {
    path: "/app/checkout/success",
    name: "CheckoutSuccess",
    component: CheckoutSuccess,
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
  {
    path: "/app/login",
    name: "Login",
    component: Login
  },
  {
    path: "/app/signup",
    name: "signup",
    component: SignUp
  },
  // Redirect / to /app
  {
    path: "/",
    redirect: { name: "Home" },
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
