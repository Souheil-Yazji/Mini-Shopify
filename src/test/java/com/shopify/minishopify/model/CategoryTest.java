package com.shopify.minishopify.model;

import com.shopify.minishopify.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CategoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void prepareDB() {
        categoryRepository.deleteAll();
    }

    @Test
    public void testPersistence() {
        Category testCategory = new Category("TEST_CATEGORY");
        categoryRepository.save(testCategory);

        List<Category> dbCategories = categoryRepository.findAll();
        assertEquals(dbCategories.size(), 1);
        Category readCategory = dbCategories.get(0);
        assertEquals("TEST_CATEGORY", readCategory.getName());

        categoryRepository.delete(readCategory);
        dbCategories = categoryRepository.findAll();
        assertEquals(dbCategories.size(), 0);
    }
}
