package com.galvanize.controllers;

import com.galvanize.entities.Product;
import com.galvanize.services.ProductService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.Assert.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductService service;

    private Product p;


    @Before
    public void setUp() throws Exception {
        p = new Product();
        p.setId(1);
        p.setName("Dash");
        p.setStockNumber("DASH1234");
        p.setListPrice(299.99);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldCreateProduct() throws Exception {
        given(this.service.create(Mockito.any(Product.class)))
                .willReturn(p);

        MockHttpServletRequestBuilder request = post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Dash\",\"stockNumber\": \"DASH1234\",\"listPrice\": \"299.99\",\"discount\": \".00\"}");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dash")))
                .andExpect(jsonPath("$.stockNumber", is("DASH1234")));
    }

    @Test
    public void shouldNotCreateMovieBadURL() throws Exception {
        MockHttpServletRequestBuilder request = post("/product");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldReturnProduct() throws Exception {
        given(this.service.findById(1))
                .willReturn(p);

        MockHttpServletRequestBuilder request = get("/products/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Dash")))
                .andExpect(jsonPath("$.stockNumber", is("DASH1234")));
    }

    @Test
    public void shouldNotReturnProductByIdBadURL() throws Exception {
        MockHttpServletRequestBuilder request = get("/product/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldNotReturnProductByBadId() throws Exception {
        MockHttpServletRequestBuilder request = get("/products/8");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("")));
    }

    @Test
    public void shouldReturnAllMovies() throws Exception {
        Product p2 = new Product();
        p2.setId(2);
        p2.setName("Vive");
        p2.setStockNumber("VIVE1234");
        p2.setListPrice(499.99);
        ArrayList<Product> products = new ArrayList<>();
        products.add(p);
        products.add(p2);
        given(this.service.findAllProducts())
                .willReturn((Iterable<Product>) products);

        MockHttpServletRequestBuilder request = get("/products");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void shouldNotReturnAllMoviesBadUrl() throws Exception {
        MockHttpServletRequestBuilder request = get("/product");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }


    @Test
    public void shouldFindMovieByTitle() throws Exception {
        given(this.service.findByName("Dash"))
                .willReturn(p);

        MockHttpServletRequestBuilder request = get("/products/search?name=Dash");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Dash")));
    }

    @Test
    public void shouldNotFindMovieByBadTitle() throws Exception {
        given(this.service.findByName("Dash"))
                .willReturn(p);

        MockHttpServletRequestBuilder request = get("/products/search?name=Vive");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(is("")));
    }

    @Test
    public void shouldDeleteMovie() throws Exception {
        MockHttpServletRequestBuilder request = delete("/products/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldNotDeleteMovieBadUrl() throws Exception {
        MockHttpServletRequestBuilder request = delete("/product/1");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void shouldUpdateMovie() throws Exception {
        p.setVersion(2);
        p.setName("Vive");
        given(this.service.update(anyInt(),Mockito.any(Product.class)))
                .willReturn(p);

        MockHttpServletRequestBuilder request = put("/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Vive\"}");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.version",is(2)))
                .andExpect(jsonPath("$.name",is("Vive")));
    }

    @Test
    public void shouldReturnAverageRatings() throws Exception {
        Map<String,Double> averageRatings = new HashMap<>();
        averageRatings.put("AverageRatings",4.666667);
        given(this.service.findAverageRatings()).willReturn(averageRatings);

        MockHttpServletRequestBuilder request = get("/products/ratings/average");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.AverageRatings",is(4.666667)));
    }

    @Test
    public void shouldReturnNumberOfReviews() throws Exception {
        Map<String,Integer> numOfReviews = new HashMap<>();
        numOfReviews.put("NumberOfReviews",2);
        given(this.service.findNumberOfReviews()).willReturn(numOfReviews);

        MockHttpServletRequestBuilder request = get("/products/reviews/count");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.NumberOfReviews",is(2)));
    }

    @Test
    public void shouldReturnTotalNumberOfProducts() throws Exception {
        Map<String,Integer> numOfProducts = new HashMap<>();
        numOfProducts.put("NumberOfProducts",3);
        given(this.service.findNumberOfProducts()).willReturn(numOfProducts);

        MockHttpServletRequestBuilder request = get("/products/count");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.NumberOfProducts",is(3)));
    }

    @Test
    public void shouldReturnNumberOfRestrictedProducts() throws Exception {
        p.setRestricted(true);
        ArrayList<Product> restrictedProducts = new ArrayList<>();
        restrictedProducts.add(p);
        given(this.service.findRestricted())
                .willReturn(restrictedProducts);

        MockHttpServletRequestBuilder request = get("/products/restricted");

        this.mvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }


}