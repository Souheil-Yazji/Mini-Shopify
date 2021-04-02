<template>
  <div class="shopList">
    <div
        v-if="error.length > 0"
        class="alert alert-danger"
        role="alert">
      {{error}}
    </div>
    <h1>Shop List</h1> <!--Temp header-->
    <div id="filters">
      <div class="filterTile">
        <h4>Order by:</h4>
        <b-form-select
            v-model="selected"
            v-bind:options="options"
            @change="orderBy()"
        ></b-form-select>
      </div>
      <div class="filterTile">
        <h4>Categories</h4>
        <div v-if="categories.length < 1">
          <p>No available categories</p>
        </div>
        <div
            v-else
            v-for="category in categories"
            v-bind:key="'category' + category.id"
        >
          <label :for="category.name">{{category.name}}</label>
          <input
              type="checkbox"
              v-bind:id="'category' + category.id"
              class="checkBox"
          >
        </div>
      </div>
      <div class="filterTile">
        <h4>Tags</h4>
        <div v-if="tags.length < 1">
          <p>No available tags</p>
        </div>
        <div
            v-else
            v-for="tag in tags"
            v-bind:key="'tag' + tag.id"
        >
          <label :for="tag.name">{{tag.name}}</label>
          <input
              type="checkbox"
              v-bind:id="'tag' + tag.id"
              class="checkBox"
          >
        </div>
      </div>
      <input
          id="filterButton"
          type="button"
          value="Filter Search"
          v-on:click="filterSearch"
      />
    </div>
    <div id="filteredList">
      <h4
          id="searchKeyword"
          v-if="keyword.length > 0"
      >Search keyword: "{{keyword}}"</h4>
      <div v-if="filteredShops.length < 1">
        No results found
      </div>
      <b-card
          v-else
          v-for="shop in filteredShops"
          v-bind:key="'shop' + shop.id"
          v-bind:title="shop.name"
          v-bind:sub-title="shop.description"
          v-bind:img-src="shop.image"
          img-top
          class="shopTile mb-2"
      >
        <b-card-text class="shopAttributeBlock">
          <div
              v-for="category in shop.categories"
              v-bind:key="'shop' + shop.id + '_category' + category.id"
              v-bind:class="'shopAttribute shopCategory ' + category.name"
          >
            {{category.name}}
          </div>
          <div
              v-for="tag in shop.tags"
              v-bind:key="'shop' + shop.id + '_tag' + tag.id"
              v-bind:class="'shopAttribute shopTag ' + tag.name"
          >
            {{tag.name}}
          </div>
        </b-card-text>
        <b-card-text>
          <a v-bind:href="'/app/shops/'+shop.id" class="card-link">Go to Shop</a>
        </b-card-text>
      </b-card>
    </div>
  </div>
</template>

<script>

let addDiff = (newAttributes, target) =>
    newAttributes.forEach(function (attribute){
      if(target.every(element => attribute.name !== element.name)){
        target.push(attribute)
      }
    });

let categoryChecker = (shopCategories, attrId) =>
    shopCategories.some(category =>
        category.id == parseInt(attrId));

let tagChecker = (shopTags, attrId) =>
    shopTags.some(tag =>
        tag.id === parseInt(attrId))

let attributeFilter = (shops, selectedAttributes) =>
    shops.filter(shop =>
        selectedAttributes.every(attribute => {
          if(attribute.includes('tag')){
            return tagChecker(shop.tags, attribute.substring(3));
          }
          else if(attribute.includes('category')){
            return categoryChecker(shop.categories, attribute.substring(8));
          }
          return false;
        }));

export default {
  name: "shopList",
  data(){
    return{
      shops:[],
      filteredShops:[],
      categories: [],
      tags: [],
      keyword: '',
      error: '',
      selected: 'shopId',
      options: [
        {value: "shopId", text: 'Default' },
        {value: "alpha", text: 'Alphabetical' },
      ],
    }
  },
  created: function(){
    const vm = this;
    vm.keyword = new URLSearchParams(window.location.search).get('keyword');

    if(vm.keyword === null){
      vm.keyword = ''
    }

    fetch('/api/shops/list?keyword=' + vm.keyword)
        .then((response) => response.json())
        .then((response) => {
          if (response.status == 404) {
            throw new Error(response.message);
          }
          return response;
        })
        .then((data) => {
          vm.shops = data;
          vm.filteredShops = data;

          data.forEach(function(shop){
            addDiff(shop.categories, vm.categories);
            addDiff(shop.tags, vm.tags);
          });
        })
        .catch((error) => {
          vm.error = error;
        });
  },
  methods: {
    filterSearch: function(){
      const vm = this;
      let checkedBoxes = [];
      let boxes = document.getElementsByClassName("checkBox");

      //Obtain checked boxes
      boxes.forEach(function(box){
        if(box.checked){
          checkedBoxes.push(box.id) //box's id is tag/category + id
        }
      });

      vm.filteredShops = attributeFilter(vm.shops, checkedBoxes)
    },
    orderBy: function () {
      const vm = this;

      function compareId(shop1, shop2) {
        if (shop1.id < shop2.id) {
          return -1;
        }
        return 1;
      }

      function compareAlpha(shop1, shop2) {
        if (shop1.name < shop2.name) {
          return -1;
        }
        else if (shop1.name > shop2.name) {
          return 1;
        }
        return 0;
      }

      if (vm.selected === "shopId") {
        vm.filteredShops = vm.filteredShops.sort(compareId);
      } else if (vm.selected === "alpha") {
        vm.filteredShops = vm.filteredShops.sort(compareAlpha);
      }
    },
  }
}
</script>

<style scoped>
h1{
  margin-left: 10px;
}

input{
  float: right;
  margin:0;
}

#filters{
  width: 15%;
  padding: 10px;
  border: #d6d6de 1px solid;
  text-align: left;
  display: inline-block;
  float: left;
  margin-left: 10px;
}

#filterButton{
  width: 100%;
}

#filteredList{
  width: 80%;
  display: inline-block;
  margin-left: 10px;
}

#searchKeyword{
  text-align: left;
  margin: 10px;
}

.filterTile{
  padding-bottom: 10px;
  border-bottom: #d6d6de 1px solid;
  margin-bottom: 10px;
}

.shopAttribute{
  display: inline-block;
  border: #d6d6de solid 1px;
  padding: 5px;
  margin: 0 10px 0 0;
}

.shopCategory{
  background: aquamarine;
}

.shopTag{
  background: azure;
}

.card{
  height: 350px;
  width: 300px;
  display: inline-block;
  margin: 5px;
  vertical-align: top;
}

.card-img-top{
  height: 40%;
}
</style>