package com.shopify.minishopify.model;

import com.shopify.minishopify.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TagTest {
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    public void prepareDB() {
        tagRepository.deleteAll();
    }

    @Test
    public void testPersistence() {
        Tag testTag = new Tag("TEST_TAG");
        tagRepository.save(testTag);

        List<Tag> dbTags = tagRepository.findAll();
        assertEquals(1, dbTags.size());
        Tag readTag = dbTags.get(0);
        assertEquals("TEST_TAG", readTag.getName());

        tagRepository.delete(readTag);
        dbTags = tagRepository.findAll();
        assertEquals(0, dbTags.size());
    }
}
