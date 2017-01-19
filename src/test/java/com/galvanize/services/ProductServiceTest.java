package com.galvanize.services;

import com.galvanize.services.ProductService;
import com.galvanize.entities.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.mvc.condition.ProducesRequestCondition;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(value = {"/sql/seed.sql"})
public class ProductServiceTest {

    @Autowired
    private ProductService service;

    Product product = new Product();

    @Before
    public void setUp() throws Exception {
        product.setName("Oculus");
        product.setStockNumber("OCU1234");
        product.setListPrice(899.99);
        product.setDiscount(0.10);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void shouldCreateProduct() throws Exception {
        Product after = this.service.create(product);
        assertEquals(4,after.getId());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void shouldNotCreateProductWithDuplicateName() throws Exception {
        Product before = new Product();
        before.setName("Vive");
        Product after = this.service.create(before);
    }

    @Test
    public void shouldCreateProductWithStockNumber() {
        Product after = this.service.create(product);
        assertEquals("OCU1234",after.getStockNumber());
    }

    @Test(expected = org.springframework.dao.DataIntegrityViolationException.class)
    public void shouldNotCreateProductWithDuplicateStockNumber() {
        Product before = new Product();
        before.setName("ViveItUp");
        before.setStockNumber("HTCV123");
        Product after = this.service.create(before);
    }

    @Test
    public void shouldGetProductById(){
        Product after = this.service.create(product);
        Product product1 = this.service.findById(1);
        assertEquals("Vive",product1.getName());
    }

    @Test
    public void shouldNotGetProductByBadId(){
        Product product1 = this.service.findById(4);
        assertNull(product1);
    }

    @Test
    public void shouldGetProductByName(){
        Product after = this.service.findByName("Vive");
        assertEquals("HTCV123",after.getStockNumber());
    }

    @Test
    public void shouldNotGetProductByBadName(){
        Product product1 = this.service.findByName("Blaaa");
        assertNull(product1);
    }

    @Test
    public void shouldFindAllProducts(){
        Product product1 = new Product();
        product1.setName("Tea Cup");
        product1.setStockNumber("TEACUP123");
        product1.setListPrice(10.00);
        product1.setDiscount(0.0);
        Product product2 = this.service.create(product1);
        ArrayList<Product> products = (ArrayList<Product>) this.service.findAllProducts();
        assertEquals(4,products.size());
    }

    @Test
    public void shouldDeleteProductById(){
        Product product1 = new Product();
        product1.setName("Tea Cup");
        product1.setStockNumber("TEACUP123");
        product1.setListPrice(10.00);
        product1.setDiscount(0.0);
        Product product2 = this.service.create(product1);
        ArrayList<Product> products = (ArrayList<Product>) this.service.findAllProducts();
        assertEquals(4,products.size());
        this.service.delete(2);
        ArrayList<Product> products1 = (ArrayList<Product>) this.service.findAllProducts();
        assertEquals(3,products1.size());
    }

    @Test
    public void shouldUpdateProduct(){
        Product product1 = new Product();
        product1.setName("Tea Cup");
        product1.setStockNumber("TEACUP123");
        product1.setListPrice(10.00);
        product1.setDiscount(0.0);
        Product product2 = this.service.update(1,product1);
        assertEquals("Tea Cup",this.service.findById(1).getName());
    }

    @Test
    public void shouldGetAverageRating(){
        Double averageRating = this.service.findAverageRatings().get("AverageRatings");
        assertEquals(4.00,averageRating,0.01);
    }

    @Test
    public void shouldGetTotalNumberOfProducts(){
        int numOfProducts = this.service.findNumberOfProducts().get("NumberOfProducts");
        assertEquals(3,numOfProducts);
    }

    @Test
    public void shouldGetTotalNumberOfProductsWithReviews(){
        Product product1 = this.service.create(product);
        int numOfProducts = this.service.findNumberOfProducts().get("NumberOfProducts");
        assertEquals(4,numOfProducts);
        int numOfReviews = this.service.findNumberOfReviews().get("NumberOfReviews");
        assertEquals(3,numOfReviews);
    }

    @Test
    public void shouldGetRestrictedProducts(){
        int numberOfRestricted = this.service.findRestricted().size();
        assertEquals(1,numberOfRestricted);
        assertEquals(3,this.service.findRestricted().get(0).getId());
    }

}