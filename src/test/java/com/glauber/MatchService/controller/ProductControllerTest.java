package com.glauber.MatchService.controller;

import com.glauber.MatchService.BaseCompTest;
import com.glauber.MatchService.model.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProductControllerTest extends BaseCompTest {
    private static final String TRUNCATE_PRODUCTS = "classpath:db.sql/reset.sql";
    private static final String INSERT_INTO_PRODUCTS = "classpath:db.sql/insert_into_product.sql";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("Deve encontrar um alerta pelo id")
    @Sql({TRUNCATE_PRODUCTS, INSERT_INTO_PRODUCTS})
    public void shouldFindAlertById() throws Exception {


        mockMvc.perform(get("/api/match/products/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("Deve deletar um alerta pelo id")
    @Sql({TRUNCATE_PRODUCTS, INSERT_INTO_PRODUCTS})
    public void shouldDeleteAlertById() throws Exception {


        mockMvc.perform(delete("/api/match/products/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}