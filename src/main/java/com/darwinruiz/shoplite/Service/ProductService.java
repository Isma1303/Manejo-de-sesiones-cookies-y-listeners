package com.darwinruiz.shoplite.Service;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;

public class ProductService {
    private final ProductRepository repository =  new ProductRepository();


    public void add(String name, Integer price, String imageUrl) {
        Product nuevo= new Product(0, name, price, imageUrl);
        repository.add(nuevo);
    }
}
