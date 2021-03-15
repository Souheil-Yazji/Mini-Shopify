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
      <h4 id="searchKeyword">Search keyword: "{{keyword}}"</h4>
      <div v-if="filteredShops.length < 1">
        No results found
      </div>
      <div
          v-else
          v-for="shop in filteredShops"
          v-bind:key="'shop' + shop.id"
          class="shopTile card"
      >
        <img class="card-img-top" v-bind:src="shop.image"/> <!--Update when root image location is known -->
        <div class="card-body">
          <h5 class="card-title">
            <router-link :to="'/app/shops/'+shop.id" exact>
              {{shop.name}}
            </router-link>
          </h5>
          <p class="card-text">{{shop.description}}</p>
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
        </div>
      </div>
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
    }
  },
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
  margin: 0 5px;
}

.shopCategory{
  background: aquamarine;
}

.shopTag{
  background: azure;
}

.card{
  height: 300px;
  width: 300px;
  display: inline-block;
  margin: 5px;
  vertical-align: top;
}

.card-img-top{
  width: auto;
  height: 50%;
}
</style>