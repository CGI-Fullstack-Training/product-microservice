package com.example.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.Coupon;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private RestTemplate restTemplate;

	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody Product product) {

		Coupon coupon = restTemplate.getForObject("http://localhost:8082/coupon-microservice/coupons/{couponCode}",
				Coupon.class,Map.of("couponCode", product.getCouponCode()));
		System.out.println("coupon :: " + coupon);
		log.info("coupon details are {} ", coupon);
		Double price = product.getProductPrice() - coupon.getDiscount();
		product.setProductPrice(price);
		return ResponseEntity.ok(productService.createProduct(product));
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<?> getProductById(@PathVariable("productId") int productId){
		return ResponseEntity.ok(productService.getProductById(productId));
	}

}
