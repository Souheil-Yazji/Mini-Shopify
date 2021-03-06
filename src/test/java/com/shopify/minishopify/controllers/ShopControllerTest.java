package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.model.User;
import com.shopify.minishopify.repository.ShopRepository;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ShopControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShopRepository shopRepository;

    @InjectMocks
    private ShopController shopController;

    // test objects
    private Shop shop1;
    private User shopOwner;


    @Before
    public void initialize(){
        shopOwner = new User("shop owner", "shopowner@email.com");;
        shop1 = new Shop(shopOwner, "Shop1", "Shop1 description");
    }

    @Test
    public void whenFindExistingShopById_thenReturnShop() throws Exception {

        when(shopRepository.findById(shop1.getId())).thenReturn(Optional.of(shop1));

        mvc.perform(get("/shops/" + shop1.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenFindNonExistingShopById_thenReturnNotFound() throws Exception {

        mvc.perform(get("/shops/2"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenUpdateExistingShop_thenReturnNewShop() throws Exception {

        when(shopRepository.findById(shop1.getId())).thenReturn(Optional.of(shop1));

        mvc.perform(get("/shops/" + shop1.getId()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateExistingShop_thenReturnOk() throws Exception {

        // create request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", shop1.getName());
        requestBody.put("description", shop1.getDescription());

        // mock repository behaviour
        when(shopRepository.findById(2)).thenReturn(Optional.of(shop1));
        when(shopRepository.save(any())).thenReturn(shop1);

        mvc.perform(put("/shops/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody.toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void whenUpdateNonExistingShop_thenReturnNotFound() throws Exception {

        // create request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", shop1.getName());
        requestBody.put("description", shop1.getDescription());

        mvc.perform(put("/shops/update/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void whenCreateNewShop_thenReturnShop() throws Exception {

        // create request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", shop1.getName());
        requestBody.put("description", shop1.getDescription());

        when(shopRepository.save(any())).thenReturn(shop1);

        mvc.perform(post("/shops/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody.toString()))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(shop1.getName()))
                .andExpect(jsonPath("$.description").value(shop1.getDescription()));
    }
}