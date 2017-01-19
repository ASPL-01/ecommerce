package com.galvanize.controllers;

import com.galvanize.entities.Product;
import com.galvanize.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value="/products")
public class ProductController {
    private ProductService service;

    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public Product create(@RequestBody Product product){
        return this.service.create(product);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.GET)
    public Product findById(@PathVariable int id){
        return this.service.findById(id);
    }

    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public Iterable<Product> findAll(){
        return this.service.findAllProducts();
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public Product findByName(@RequestParam Map<String, String> query){
        return this.service.findByName(query.get("name"));
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        this.service.delete(id);
    }

    @RequestMapping(value = {"/{id}"}, method = RequestMethod.PUT)
    public Product update(@PathVariable int id, @RequestBody Product product){
        return this.service.update(id,product);
    }

    @RequestMapping(value = {"/ratings/average"}, method = RequestMethod.GET)
    public Map<String,Double> getAverage(){
        return this.service.findAverageRatings();
    }

    @RequestMapping(value = {"/reviews/count"}, method = RequestMethod.GET)
    public Map<String,Integer> getNumReviews() {
        return this.service.findNumberOfReviews();
    }

    @RequestMapping(value = {"/count"}, method = RequestMethod.GET)
    public Map<String,Integer> getNumProducts() {
        return this.service.findNumberOfProducts();
    }

    @RequestMapping(value = {"/restricted"}, method = RequestMethod.GET)
    public ArrayList<Product> getRestricted(){
        return this.service.findRestricted();
    }
}
