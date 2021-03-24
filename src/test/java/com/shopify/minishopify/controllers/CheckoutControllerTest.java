package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Product;
import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.repository.CategoryRepository;
import com.shopify.minishopify.repository.ProductRepository;
import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.TagRepository;
import com.shopify.minishopify.viewmodel.Checkout;
import com.shopify.minishopify.viewmodel.CheckoutList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckoutControllerTest {
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

    // Test objects
    private Product product1;
    private Product product2;
    private Checkout checkout;
    private JSONObject checkoutJsonBody;

    @BeforeAll
    public void initialize() throws JSONException {
        product1 = new Product("product", "description", 1.0f, 2, "image");
        product2 = new Product("product", "description", 1.0f, 1, "image");
        checkout = new Checkout(1, 2);

        JSONObject cj = new JSONObject();
        cj.put("id", checkout.getId());
        cj.put("quantity", checkout.getQuantity());

        JSONArray list = new JSONArray();
        list.put(cj);

        checkoutJsonBody = new JSONObject();
        checkoutJsonBody.put("checkouts", list);
    }

    @Test
    public void whenCheckoutWithEnoughProducts_thenReturnOk() throws Exception {
        when(productRepository.findById(checkout.getId())).thenReturn(Optional.of(product1));

        mvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkoutJsonBody.toString()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void whenCheckoutWithNotEnoughProducts_thenReturnBadRequest() throws Exception {
        when(productRepository.findById(checkout.getId())).thenReturn(Optional.of(product2));

        mvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkoutJsonBody.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenCheckoutProductNotExist_thenReturnNotFound() throws Exception {
        mvc.perform(post("/api/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .content(checkoutJsonBody.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
