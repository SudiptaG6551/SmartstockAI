package com.smartstockai.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private Long id;
    private String name;
    private String description;
    private String sku;
    @JsonProperty("purchasePrice")
    private BigDecimal purchasePrice;
    @JsonProperty("sellingPrice")
    private BigDecimal sellingPrice;
    @JsonProperty("current_stock")
    private Integer currentStock;
    private String category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean active;

    public Product() {
    }

    public Product(Long id, String name, String description, String sku, BigDecimal purchasePrice, BigDecimal sellingPrice,
                   String category, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean active, Integer currentStock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sku = sku;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.category = category;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.active = active;
        this.currentStock = currentStock;
    }

    public Product(Long id, String name, String description, String sku, BigDecimal purchasePrice, BigDecimal sellingPrice,
                   String category, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean active) {
        this(id, name, description, sku, purchasePrice, sellingPrice, category, createdAt, updatedAt, active, null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(Integer currentStock) {
        this.currentStock = currentStock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

