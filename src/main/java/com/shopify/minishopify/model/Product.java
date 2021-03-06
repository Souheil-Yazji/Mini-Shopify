package com.shopify.minishopify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    private Shop shop;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private float price;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "image")
    private byte[] image;

    public Product() {
    }

    public Product(String name, float price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, float price, int quantity, byte[] image) {
        this.name = name;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Float.compare(product.price, price) == 0 && quantity == product.quantity &&
                Objects.equals(shop, product.shop) && Objects.equals(name, product.name) &&
                Arrays.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, shop, name, price, quantity);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }
}