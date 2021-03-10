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
                id="input-group-1"
                label-for="input-1"
                label="Shop Description:"
            >
                <b-form-input
                    id="input-1"
                    placeholder="Enter Shop Description"
                    required
                    v-model="form.description"
                ></b-form-input>
            </b-form-group>

            <b-form-group
                description="Pick an image for your shop"
                id="input-group-5"
                label-for="input-5"
                label="Shop Image:"
            >
                <b-form-file
                    :state="Boolean(form.image)"
                    accept=".png"
                    drop-placeholder="Drop image here..."
                    id="input-5"
                    placeholder="Choose an image or drop it here..."
                    v-model="form.image"
                ></b-form-file>
            </b-form-group>
            <div class="mt-5">
                <b-button class="mr-5" type="submit" variant="primary">Submit</b-button>
                <b-button type="reset" variant="danger">Reset</b-button>
            </div>
        </b-form>
    </div>
</template>

<script>
export default {
    name: "ShopForm",
    props: ["handleSubmit", "initialValues"],
    data: function() {
        return {
            form: {
                ...this.initialValues,
            },
            error: "",
            show: true,
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

            reader.onload = () => handleDone(reader.result);
        },

        onSubmit(event) {
            event.preventDefault();

            try {
                this.encode(this.form.image, (image) => {
                    this.handleSubmit(
                        { ...this.form, image: image },
                        (response) => {
                            if ((response.status && response.status != 200) || !response.id) {
                                this.error = response;
                                return;
                            }

                            // Redirect to View Store Details Page
                            this.$router.push(`/app/shops/${response.id}`);
                        }
                    );
                });
            } catch (error) {
                this.error = error;
            }
        },

        onReset(event) {
            event.preventDefault();

            // Reset our form values
            this.form.name = "";
            this.form.description = "";
            this.form.image = "";
            this.error = null;

            // Trick to reset/clear native browser form validation state
            this.show = false;
            this.$nextTick(() => {
                this.show = true;
            });
        },
    },
};

</script>
