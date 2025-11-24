package com.smartstockai.service;

import com.smartstockai.dao.ProductDao;
import com.smartstockai.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product createProduct(Product product) {
        if (productDao.existsBySku(product.getSku())) {
            throw new RuntimeException("Product with SKU already exists");
        }
        return productDao.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return productDao.update(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public Product getProductBySku(String sku) {
        return productDao.findBySku(sku).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDao.findByCategory(category);
    }

    @Override
    public void deleteProduct(Long id) {
        productDao.deleteById(id);
    }
}

