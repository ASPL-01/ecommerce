package com.galvanize.repositories;

import com.galvanize.entities.Product;
import com.galvanize.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;



public interface IProductRepository extends CrudRepository<Product, Integer> {
    public Product findByName(String name);

    public ArrayList<Product> findByRestricted(boolean restricted);

    public ArrayList<Product> findByReviewNotNull();

    @Query(value = "SELECT COUNT(*) FROM products", nativeQuery = true)
    public int findNumOfProducts();

    @Query(value ="SELECT AVG(rating) FROM products WHERE rating is not null;", nativeQuery = true)
    public Double findAverageRatings();

}
