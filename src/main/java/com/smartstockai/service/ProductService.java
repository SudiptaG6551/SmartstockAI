package com.smartstockai.service;

import com.smartstockai.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Product product);
    Product getProductById(Long id);
    Product getProductBySku(String sku);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    void deleteProduct(Long id);
}

