package com.shopify.minishopify.model;

import java.util.List;

public class Shop {
    private List<Tag> tags;
    private List<Category> categories;
    private List<Product> products;

    private int id;
    private int owner_id;
    private String name;
    private String desc;
}
