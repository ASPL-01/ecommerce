package com.galvanize.services;

import com.galvanize.entities.Product;
import com.galvanize.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private IProductRepository repository;

    @Autowired
    public void setRepository(IProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product p) {
        return this.repository.save(p);
    }

    public Product findById(int id) {
        return this.repository.findOne(id);
    }

    private void setActualPrice(Product p){
        p.setActualPrice(p.getListPrice() * p.getDiscount());
    }

    public Product findByName(String name){
        return this.repository.findByName(name);
    }

    public Iterable<Product> findAllProducts(){
        return this.repository.findAll();
    }

    public void delete(int id){
        this.repository.delete(id);
    }

    public Product update(int id, Product product){
        Product old = this.repository.findOne(id);
        old.setName(product.getName());
        old.setStockNumber(product.getStockNumber());
        old.setListPrice(product.getListPrice());
        old.setDiscount(product.getDiscount());
        return this.repository.save(old);
    }

    public Map<String,Double> findAverageRatings(){
        Map<String,Double> averageRatings = new HashMap<>();
        averageRatings.put("AverageRatings",this.repository.findAverageRatings());
        return averageRatings;
    }

    public Map<String,Integer> findNumberOfReviews(){
        Map<String, Integer> numOfReviews = new HashMap<>();
        numOfReviews.put("NumberOfReviews",this.repository.findByReviewNotNull().size());
        return numOfReviews;
    }

    public Map<String,Integer> findNumberOfProducts(){
        Map<String, Integer> numOfProducts = new HashMap<>();
        numOfProducts.put("NumberOfProducts",this.repository.findNumOfProducts());
        return numOfProducts;
    }

    public ArrayList<Product> findRestricted(){
        return this.repository.findByRestricted(true);
    }
}
