package com.shopify.minishopify.controllers;

import com.shopify.minishopify.repository.CategoryRepository;
import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.TagRepository;
import com.shopify.minishopify.security.jwt.AuthEntryPointJwt;
import com.shopify.minishopify.security.jwt.JwtUtils;
import com.shopify.minishopify.security.services.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TagControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ShopRepository shopRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    private UserController userController;

    @MockBean
    private JwtUtils jwtUtils;

    @Test
    public void whenGetTagList_ReturnAllTags() throws Exception {
        mvc.perform(get("/api/tag/list"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(tagRepository, never()).search(any());
        verify(tagRepository).findAll();
    }

    @Test
    public void whenGetTagListWithKeyword_ReturnTagsWithKeyword() throws Exception {
        mvc.perform(get("/api/tag/list?keyword=TEST"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
        verify(tagRepository).search("TEST");
        verify(tagRepository, never()).findAll();
    }
}
