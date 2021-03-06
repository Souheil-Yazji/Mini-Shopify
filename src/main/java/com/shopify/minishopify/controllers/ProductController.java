package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Product;
import com.shopify.minishopify.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products/list")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        Product product = null;
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            product = optional.get();
        }

        return product;
    }

    @PostMapping("/products/create")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PostMapping("/products/update/{id}")
    public Product updateProductById(@PathVariable int id, @RequestBody Product updatedProduct) {
        Product product = null;
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            product = optional.get();
            product.setShop(updatedProduct.getShop());
            product.setImage(updatedProduct.getImage());
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());

            product = productRepository.save(product);
        }

        return product;
    }

    @DeleteMapping("/products/delete/{id}")
    public Product deleteProductById(@PathVariable int id) {
        Product product = null;
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            productRepository.deleteById(product.getId());
        }

        return product;
    }
}
