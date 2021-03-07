package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.model.User;
import com.shopify.minishopify.repository.ShopRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ShopControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShopRepository shopRepository;

    // test objects
    private Shop shop1;
    private User shopOwner;
    private JSONObject ownerJsonBody;
    private JSONObject shopJsonBody;


    @BeforeAll
    public void initialize() throws Exception {
        shopOwner = new User("shop owner", "shopowner@email.com");
        shop1 = new Shop(shopOwner, "Shop1", "Shop1 description", "image");

        ownerJsonBody = new JSONObject();
        ownerJsonBody.put("id", shopOwner.getId());
        ownerJsonBody.put("name", shopOwner.getName());
        ownerJsonBody.put("email", shopOwner.getEmail());

        shopJsonBody = new JSONObject();
        shopJsonBody.put("owner", ownerJsonBody);
        shopJsonBody.put("name", shop1.getName());
        shopJsonBody.put("description", shop1.getDescription());
        shopJsonBody.put("image", shop1.getImage());
    }

    @Test
    public void whenFindExistingShopById_thenReturnShop() throws Exception {

        when(shopRepository.findById(shop1.getId())).thenReturn(Optional.of(shop1));

        mvc.perform(get("/api/shops/" + shop1.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenFindNonExistingShopById_thenReturnNotFound() throws Exception {

        mvc.perform(get("/api/shops/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdateExistingShop_thenReturnOk() throws Exception {

        // mock repository behaviour
        when(shopRepository.findById(2)).thenReturn(Optional.of(shop1));
        when(shopRepository.save(any())).thenReturn(shop1);

        mvc.perform(put("/api/shops/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(shopJsonBody.toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateNonExistingShop_thenReturnNotFound() throws Exception {

        mvc.perform(put("/api/shops/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shopJsonBody.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateNewShop_thenReturnShop() throws Exception {

        when(shopRepository.save(any())).thenReturn(shop1);

        mvc.perform(post("/api/shops/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(shopJsonBody.toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(shop1.getName()))
                .andExpect(jsonPath("$.description").value(shop1.getDescription()));
    }

    @Test
    public void whenCreateInvalidShop_thenReturnValidationFailure() throws Exception {

        // create a request body with missing user
        JSONObject requestBodyMissingUser = new JSONObject();
        requestBodyMissingUser.put("name", shop1.getName());
        requestBodyMissingUser.put("description", shop1.getDescription());
        requestBodyMissingUser.put("image", shop1.getImage());

        when(shopRepository.save(any())).thenReturn(shop1);

        mvc.perform(post("/api/shops/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMissingUser.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // create a request body with missing name
        JSONObject requestBodyMissingName = new JSONObject();
        requestBodyMissingName.put("owner", ownerJsonBody);
        requestBodyMissingName.put("description", shop1.getDescription());
        requestBodyMissingName.put("image", shop1.getImage());

        mvc.perform(post("/api/shops/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMissingName.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // create a request body with missing description
        JSONObject requestBodyMissingDescription = new JSONObject();
        requestBodyMissingDescription.put("name", shop1.getName());
        requestBodyMissingDescription.put("owner", ownerJsonBody);
        requestBodyMissingDescription.put("image", shop1.getImage());

        mvc.perform(post("/api/shops/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMissingDescription.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());

        // create a request body with missing description
        JSONObject requestBodyMissingImage = new JSONObject();
        requestBodyMissingImage.put("name", shop1.getName());
        requestBodyMissingImage.put("owner", ownerJsonBody);
        requestBodyMissingImage.put("description", shop1.getDescription());

        mvc.perform(post("/api/shops/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBodyMissingImage.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
