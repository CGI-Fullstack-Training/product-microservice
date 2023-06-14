package com.example.demo.service;

import com.example.demo.model.Product;

public interface ProductService {

	public Product createProduct(Product product);

	public Product getProductById(int productId);
}
