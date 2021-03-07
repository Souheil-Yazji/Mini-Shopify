package com.shopify.minishopify.model;

import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void prepareDB() {
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAll();
    }

    @AfterEach
    public void resetDB() {
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistence() {
        User testUser = new User("TEST_USER", "JUNIT");
        Shop testShop = new Shop(testUser, "TEST_SHOP", "JUnit Test Shop", "image");
        Product product = new Product("TEST_PRODUCT", 1.0f, 1);
        testShop.addProduct(product);

        productRepository.save(product);

        List<Product> dbProducts = productRepository.findAll();
        assertEquals(dbProducts.size(), 1);
        Product readProduct = dbProducts.get(0);
        assertEquals("TEST_PRODUCT", readProduct.getName());
        assertEquals(1.0f, readProduct.getPrice());
        assertEquals(1, readProduct.getQuantity());

        Shop productShop = readProduct.getShop();

        List<Shop> dbShops = shopRepository.findAll();
        assertEquals(dbShops.size(), 1);
        Shop readShop = dbShops.get(0);
        assertEquals(productShop, readShop);
    }
}
