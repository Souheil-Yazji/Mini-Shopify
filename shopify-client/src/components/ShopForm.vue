<template>
  <div>
    <div v-if="error">
      <div
        class="alert alert-danger"
        role="alert"
        v-bind:key="name"
        v-for="(value, name) in error"
      >
        {{ value }}
      </div>
    </div>
    <b-form @reset="onReset" @submit="onSubmit" v-if="show">
      <b-form-group
        description="Provide a Name for your Shop"
        id="input-group-1"
        label-for="input-1"
        label="Shop Name:"
      >
        <b-form-input
          id="input-1"
          placeholder="Enter Shop Name"
          required
          v-model="form.name"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        description="Provide a Description for your Shop"
        id="input-group-2"
        label-for="input-2"
        label="Shop Description:"
      >
        <b-form-input
          id="input-2"
          placeholder="Enter Shop Description"
          required
          v-model="form.description"
        ></b-form-input>
      </b-form-group>

      <b-form-group
        description="Pick an image for your shop"
        id="input-group-3"
        label-for="input-3"
        label="Shop Image:"
      >
        <b-form-file
          :state="Boolean(form.image)"
          accept=".png"
          drop-placeholder="Drop image here..."
          id="input-3"
          placeholder="Choose an image or drop it here..."
          v-model="form.image"
        ></b-form-file>
      </b-form-group>

      <b-form-group
              description="Shop Tags"
              id="input-group-4"
              label-for="input-4"
              label="Shop Tags:"
      >
        <div v-if="form.tags.length >= 1">
          <h4>
            <p v-for="tag in form.tags"
               v-bind:key="'form.tag' + tag.id">
                <b-badge variant="info"
                         small
                         :for="tag.name">
                  {{tag.name}}
                  <b-button class="ml-1"
                            variant="danger"
                            size="sm"
                            v-on:click="removeTag(tag.name)"
                            title="Remove Tag">
                    X
                  </b-button>
                </b-badge>
            </p>
          </h4>
        </div>
      </b-form-group>

      <b-form-group
              description="Add New Tags"
              id="input-group-5"
              label-for="input-5"
              label="Add Tag:"
      >
        <div v-if="tags.length >= 1">
          <label class="mr-2">Existing Tags: </label>
          <select id="addTagSelect">
            <option disabled
                    value="">
              Select Tag
            </option>
            <option v-for="tag in tags"
                    v-bind:key="'tag' + tag.id"
                    :for="tag.name"
                    value="tag.id">
              {{tag.name}}
            </option>
          </select>
          <b-button class="ml-2"
                    variant="primary"
                    size="sm"
                    title="Add Tag"
                    v-on:click = "addTag()">
            Add
          </b-button>
        </div>
        <div>
          <label class="mr-2">New Tag:</label>
          <input id="createTagName"
                 placeholder="New Tag Name"/>
          <b-button class="ml-2"
                    variant="primary"
                    size="sm"
                    title="Create Tag"
                    v-on:click="createTag()">
            Add
          </b-button>
        </div>
      </b-form-group>

      <b-form-group
              description="Shop Categories"
              id="input-group-6"
              label-for="input-6"
              label="Shop Categories:"
      >
        <div v-if="form.categories.length >= 1">
          <h4>
            <p v-for="category in form.categories"
               v-bind:key="'category' + category.id">
                <b-badge variant="info"
                         small
                         :for="category.name">
                  {{category.name}}
                  <b-button class="ml-1"
                            variant="danger"
                            size="sm"
                            v-on:click="removeCategory(category.name)"
                            title="Remove Category">
                    X
                  </b-button>
                </b-badge>
            </p>
          </h4>
        </div>
      </b-form-group>

      <b-form-group
              description="Add New Categories"
              id="input-group-7"
              label-for="input-7"
              label="Add Category:"
      >
        <div v-if="categories.length >= 1">
          <label class="mr-2">Existing Categories: </label>
          <select id="addCategorySelect">
            <option disabled value="">Select Category</option>
            <option  v-for="category in categories"
                     v-bind:key="'category' + category.id"
                     :for="category.name"
                     value="category.id">
              {{category.name}}
            </option>
          </select>
          <b-button class="ml-2"
                    variant="primary"
                    size="sm"
                    title="Add Category"
                    v-on:click="addCategory()">
            Add
          </b-button>
        </div>
        <div>
          <label class="mr-2">New Category:</label>
          <input id="createCategoryName"
                 placeholder="New Category Name"/>
          <b-button class="ml-2"
                    variant="primary"
                    size="sm"
                    title="Create Category"
                    v-on:click="createCategory()">
            Add
          </b-button>
        </div>
      </b-form-group>

      <div class="mt-5">
        <b-button class="mr-5" type="submit" variant="primary">Submit</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </div>
    </b-form>
  </div>
</template>

<script>

let addDiff = (newAttributes, target) =>
    newAttributes.forEach(function (attribute) {
      if(target.every(element => attribute.name !== element.name)){
        target.push(attribute)
      }
    });

export default {
  name: "ShopForm",
  props: ["handleSubmit", "initialValues"],
  created: async function() {
    await fetch('/api/category/list')
        .then((response) => response.json())
        .then((response) => {
          if (response.status == 404) {
            throw new Error(response.message);
          }
          return response;
        })
        .then((data) => {
          // add categories that aren't part of the shop to the list so that they can be added
          addDiff(data.filter(category => {
                    let categoryInShop = this.initialValues.categories.find(c => c.id === category.id);
                    return categoryInShop == undefined;
                  }), this.categories)
        });

    await fetch('/api/tag/list')
        .then((response) => response.json())
        .then((response) => {
          if (response.status == 404) {
            throw new Error(response.message);
          }
          return response;
        })
        .then((data) => {
          // add tags that aren't part of the shop to the list so that they can be added
          addDiff(data.filter(tag => {
                    let tagInShop = this.initialValues.tags.find(t => t.id === tag.id);
                    return tagInShop == undefined;
                  }), this.tags);
        });
  },
  data: function() {
    return {
      form: {
        ...this.initialValues,
        image: null,
      },
      error: "",
      show: true,
      categories: [],
      tags: [],
    };
  },
  methods: {
    // Encode to Base64
    encode(image, handleDone) {
      if (!image) {
        handleDone("");
        return;
      }

      const reader = new FileReader();
      reader.readAsDataURL(image);

      reader.onload = () => handleDone(reader.result.split(",")[1]);
    },

    onSubmit(event) {
      event.preventDefault();

      try {
        this.encode(this.form.image, (image) => {
          console.log(image)
          this.handleSubmit({ ...this.form, image: image }, (response) => {
            if ((response.status && response.status != 200) || !response.id) {
              this.error = response;
              return;
            }

            // Redirect to View Store Details Page
            this.$router.push(`/app/shops/${response.id}`);
          });
        });
      } catch (error) {
        this.error = error;
      }
    },

    onReset(event) {
      event.preventDefault();

      // Reset our form values
      this.form.name = this.initialValues.name;
      this.form.description = this.initialValues.description;
      this.form.categories = this.initialValues.categories;
      this.form.tags = this.initialValues.tags;
      this.form.image = null;
      this.error = null;

      this.forceRender();
    },

    removeTag(tagName) {
      let tag = this.form.tags.find(t => t.name === tagName);
      this.form.tags.splice(this.tags.indexOf(tag), 1);
      this.tags.push(tag);

      this.forceRender();
    },

    addTag() {
      let select = document.getElementById("addTagSelect")
      let tagName = select.options[select.selectedIndex].text;

      let tag = this.tags.find(t => t.name === tagName);
      this.tags.splice(this.tags.indexOf(tag), 1);
      this.form.tags.push(tag);

      this.forceRender();
    },

    createTag() {
      let tagName = document.getElementById("createTagName").value;
      if (tagName &&
          !this.tags.find(t => t.name.toLowerCase() == tagName.toLowerCase())) {
        this.form.tags.push({ name: tagName });

        this.forceRender();
      } else {
        alert(`Invalid Tag Name - ${tagName ? 'Check if the tag already exists' : 'Please provide a tag name'}`);
      }
    },

    removeCategory(categoryName) {
      let category = this.form.categories.find(c => c.name === categoryName);
      this.form.categories.splice(this.form.categories.indexOf(category), 1);
      this.categories.push(category);

      this.forceRender();
    },

    addCategory() {
      let select = document.getElementById("addCategorySelect")
      let categoryName = select.options[select.selectedIndex].text;

      let category = this.categories.find(c => c.name === categoryName);
      this.categories.splice(this.categories.indexOf(category), 1);
      this.form.categories.push(category);

      this.forceRender();
    },

    createCategory() {
      let categoryName = document.getElementById("createCategoryName").value;
      if (categoryName &&
          !this.categories.find(c => c.name.toLowerCase() == categoryName.toLowerCase())) {
        this.form.categories.push({ name: categoryName });

        this.forceRender();
      } else {
        alert(`Invalid Category Name - ${categoryName ? 'Check if the category already exists' : 'Please provide a category name'}`);
      }
    },

    forceRender() {
      // Trick to reset/clear native browser form validation state
      this.show = false;
      this.$nextTick(() => {
        this.show = true;
      });
    },
  },
};
</script>
