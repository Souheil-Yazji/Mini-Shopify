package com.shopify.minishopify.controllers;

import com.shopify.minishopify.repository.CategoryRepository;
import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private ShopController userController;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private TagRepository tagRepository;

    @Test
    public void whenCallStatsEndpoint_thenReturnExpectedFields() throws Exception {
        mvc.perform(get("/api/stats"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numShops").value(0))
                .andExpect(jsonPath("$.numProducts").value(0))
                .andExpect(jsonPath("$.numUsers").value(0));
    }
}
