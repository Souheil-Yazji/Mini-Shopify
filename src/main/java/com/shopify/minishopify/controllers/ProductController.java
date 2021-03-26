package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Product;
import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/shop/{id}/products")
    public Iterable<Product> getAllProducts(@PathVariable int id) {
        return productRepository.findByShopId(id);
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable int id) {
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No product found with id: %d", id));
        }
    }

    @GetMapping("/products")
    public Iterable<Product> getProductsByIds(@RequestParam List<Integer> ids) {

        return productRepository.findAllById(ids);
    }

    @PostMapping("/shop/{id}/products/create")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    public Product createProduct(@PathVariable int id, @Valid @RequestBody Product product) {
        Shop shop = null;
        Optional<Shop> optional = shopRepository.findById(id);

        if(optional.isPresent()) {
            shop = optional.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No shop found with id: %d", id));
        }

        product.setShop(shop);

        return productRepository.save(product);
    }

    @PostMapping("/products/update/{id}")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    public Product updateProductById(@PathVariable int id, @Valid @RequestBody Product updatedProduct) {
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            Product product = optional.get();
            product.setImage(updatedProduct.getImage());
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());

            return productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No product found with id: %d", id));
        }
    }

    @DeleteMapping("/products/delete/{id}")
    @PreAuthorize("hasRole('SHOP') or hasRole('ADMIN')")
    public Product deleteProductById(@PathVariable int id) {
        Product product = null;
        Optional<Product> optional = productRepository.findById(id);

        if(optional.isPresent()) {
            product = optional.get();
            productRepository.deleteById(product.getId());

            return product;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No product found with id: %d", id));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
