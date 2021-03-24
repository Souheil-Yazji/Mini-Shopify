package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Product;
import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.viewmodel.Checkout;
import com.shopify.minishopify.viewmodel.CheckoutList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CheckoutController {
    private static final Logger LOG = LoggerFactory.getLogger(CheckoutController.class);

    @Autowired
    private ProductRepository productRepository;

    @PostMapping("/checkout")
    public ResponseEntity checkout(@RequestBody CheckoutList checkoutList) {
        float totalPrice = 0;

        for(Checkout c : checkoutList.getCheckouts()) {
            Optional<Product> optional = productRepository.findById(c.getId());

            if(optional.isPresent()) {
                Product p = optional.get();

                if(p.getQuantity() < c.getQuantity()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Not enough products for id: %d", c.getId()));
                }

                totalPrice += c.getQuantity() * p.getPrice();
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No product found with id: %d", c.getId()));
            }
        }

        makePurchase(totalPrice);

        LOG.info("Purchase made with amount {} ", totalPrice);

        return new ResponseEntity(HttpStatus.OK);
    }

    // Placeholder for making actual purchases
    private boolean makePurchase(float amount) {
        // Actual purchases would be made here...
        return true;
    }
}
