package com.shopify.minishopify.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name = "shop")
public class Shop {
    @ManyToMany(cascade = ALL)
    @JoinTable(name = "shop_tag",
            joinColumns =
            @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private final List<Tag> tags;

    @ManyToMany(cascade = ALL)
    @JoinTable(name = "shop_category",
            joinColumns =
            @JoinColumn(name = "shop_id", referencedColumnName = "id"),
            inverseJoinColumns =
            @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private final List<Category> categories;

    @OneToMany(cascade = ALL, mappedBy = "shop")
    private final List<Product> products;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @ManyToOne(cascade = ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    public Shop() {
        categories = new ArrayList<>();
        tags = new ArrayList<>();
        products = new ArrayList<>();
    }

    public Shop(User owner, String name) {
        this.owner = owner;
        this.name = name;
        categories = new ArrayList<>();
        tags = new ArrayList<>();
        products = new ArrayList<>();
    }

    public Shop(User owner, String name, String description) {
        this.owner = owner;
        this.name = name;
        this.description = description;
        categories = new ArrayList<>();
        tags = new ArrayList<>();
        products = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public User getOwner() {
        return owner;
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

    public List<Tag> getTags() {
        return tags;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        product.setShop(this);
        products.add(product);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void addCategory(Category category) {
        categories.add(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return id == shop.id && Objects.equals(tags, shop.tags) && Objects.equals(categories, shop.categories) &&
                Objects.equals(products, shop.products) && Objects.equals(owner, shop.owner) &&
                Objects.equals(name, shop.name) && Objects.equals(description, shop.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tags, categories, products, id, owner, name, description);
    }
}