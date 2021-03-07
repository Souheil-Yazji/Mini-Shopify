package com.shopify.minishopify.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.*;

import java.util.Arrays;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    @JsonBackReference
    private Shop shop;

    @Column(name = "name")
    @NotEmpty(message = "Product must have a name")
    private String name;

    @Column(name = "description")
    @NotEmpty(message =  "Product must have a description")
    private String description;

    @Column(name = "price")
    @NotNull(message = "Product must have a price")
    @Positive(message = "Price should be greater than 0")
    private Float price;

    @Column(name = "quantity")
    @NotNull(message = "Product must have a quantity")
    @Positive(message = "Quantity should be greater than 0")
    private Integer quantity;

    @Column(name = "image")
    @NotNull(message = "Product must have an image")
    private String image;

    public Product() {
    }

    public Product(String name, String description, float price, int quantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, String description, float price, int quantity, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.price, price) == 0 && quantity == product.quantity &&
                Objects.equals(shop, product.shop) && Objects.equals(name, product.name) &&
                image.equals(product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shop, name, price, quantity, image);
    }
}
