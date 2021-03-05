package com.shopify.minishopify.model;

import com.shopify.minishopify.repository.CategoryRepository;
import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.TagRepository;
import com.shopify.minishopify.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ShopTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void prepareDB() {
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @AfterEach
    public void resetDB() {
        productRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAll();
        categoryRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testPersistence() {
        User testUser = new User("TEST_USER", "JUNIT");
        Shop testShop = new Shop(testUser, "TEST_SHOP", "JUnit Test Shop");
        Product product = new Product("TEST_PRODUCT", 1.0f, 1);
        testShop.addProduct(product);
        testShop.addCategory(new Category("TEST_CATEGORY"));
        testShop.addTag(new Tag("TEST_TAG"));

        shopRepository.save(testShop);

        List<User> dbUsers = userRepository.findAll();
        assertEquals(dbUsers.size(), 1);
        User readUser = dbUsers.get(0);
        assertEquals("TEST_USER", readUser.getName());
        assertEquals("JUNIT", readUser.getEmail());

        List<Category> dbCategories = categoryRepository.findAll();
        assertEquals(dbCategories.size(), 1);
        Category readCategory = dbCategories.get(0);
        assertEquals("TEST_CATEGORY", readCategory.getName());

        List<Tag> dbTags = tagRepository.findAll();
        assertEquals(dbTags.size(), 1);
        Tag readTag = dbTags.get(0);
        assertEquals("TEST_TAG", readTag.getName());

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
        assertEquals("TEST_SHOP", readShop.getName());
        assertEquals("JUnit Test Shop", readShop.getDescription());
        assertEquals(readUser, readShop.getOwner());
        assertTrue(readShop.getTags().contains(readTag));
        assertTrue(readShop.getCategories().contains(readCategory));
        assertTrue(readShop.getProducts().contains(readProduct));

        assertEquals(productShop, readShop);
    }
}
