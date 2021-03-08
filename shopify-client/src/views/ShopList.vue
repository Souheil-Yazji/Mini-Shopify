<template>
  <div class="shopList">
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
            :key="category.id"
        >
          <label :for="category.name">{{category.name}}</label>
          <input
              type="checkbox"
              :id="category.name"
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
            :key="tag.id"
        >
          <label :for="tag.name">{{tag.name}}</label>
          <input
              type="checkbox"
              :id="tag.name"
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
      <div v-if="shops.length < 1">
        No results found
      </div>
      <div
          v-else
          v-for="shop in shops"
          :key="shop.id"
          class="shopTile"
      >
        <img :src="'/' + shop.image"/> <!--Update when root image location is known -->
        <h3>
          <a :href="'/shop/' + shop.id">
            {{shop.name.toLocaleUpperCase()}}
          </a>
        </h3>
        <p>
          {{shop.description}}
        </p>
        <div
            v-for="category in shop.categories"
            :key="category.id"
            :class="'shopAttribute shopCategory ' + category.name"
        >
          {{category.name}}
        </div>
        <div
            v-for="tag in shop.tags"
            :key="tag.id"
            :class="'shopAttribute shopTag ' + tag.name"
        >
          {{tag.name}}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
//Placeholders
//import header from '@/components/header.vue'
//import footer from '@/components/footer.vue'

let addDiff = (newAttributes, target) =>
    newAttributes.forEach(function (attribute){
      if(target.every(element => attribute.name !== element.name)){
        target.push(attribute)
      }
    });

//checker if the shop has the selected tag and categories
let checker = (shop, target) =>
    target.every(attribute => shop.getElementsByClassName(attribute).length > 0);

export default {
  name: "stopList",
  data(){
    return{
      shops:[],
      categories: [],
      tags: [],
      keyword: '',
    }
  },
  created: function(){
    const vm = this;
    vm.keyword = new URLSearchParams(window.location.search).get('keyword');

    if(vm.keyword === null){
      vm.keyword = ''
    }

    fetch('/api/shops/shopList?keyword=' + vm.keyword)
        .then((response) => response.json())
        .then((response) => {
          if (response.status == 404) {
            throw new Error(response.message);
          }
          return response;
        })
        .then((data) => {
          vm.shops = data;

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
    filterSearch: function(event){
      if(event.target.tagName == 'INPUT'){
        let checkedBoxes = [];
        let boxes = document.getElementsByClassName("checkBox");
        let shops = document.getElementsByClassName("shopTile")

        //Obtain checked boxes
        boxes.forEach(function(box){
          if(box.checked){
            checkedBoxes.push(box.id) //box's id is the name of the tag/category
          }
        });

        if(checkedBoxes.length > 0){
          shops.forEach(function(shop){
            if(checker(shop, checkedBoxes)){
              shop.style.display = "inline-block";
            }
            else{
              shop.style.display = "none";
            }
          });
        }
        else{ //no selection
          shops.forEach(function(shop){
            shop.style.display = "inline-block"
          });
        }
      }
    }
  },
}
</script>

<style scoped>
h1{
  margin-left: 10px;
}

h4{
  margin-top: 0;
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

.shopTile{
  border: 1px solid #d6d6de;
  height: 300px;
  width: 300px;
  display: inline-block;
  vertical-align: top;
  overflow: hidden;
  padding: 10px;
  margin: 5px;
  text-align: center;
}

.shopTile h3{
  margin: 10px 0;
}

.shopTile img{
  width: 150px;
  height: 150px;
}

.shopTile p{
  margin: 10px;
}

</style>