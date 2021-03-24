package com.shopify.minishopify.viewmodel;

import java.util.List;

public class CheckoutList {
    private List<Checkout> checkouts;

    public CheckoutList() {}

    public CheckoutList(List<Checkout> checkouts) {
        this.checkouts = checkouts;
    }

    public List<Checkout> getCheckouts() {
        return checkouts;
    }

    public void setCheckouts(List<Checkout> checkouts) {
        this.checkouts = checkouts;
    }
}
