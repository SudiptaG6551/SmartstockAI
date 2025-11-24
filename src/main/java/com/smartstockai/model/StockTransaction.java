package com.smartstockai.model;

import java.time.LocalDateTime;

public class StockTransaction {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String transactionType;
    private String referenceType;
    private Long referenceId;
    private LocalDateTime transactionDate;
    private String notes;

    public StockTransaction() {
    }

    public StockTransaction(Long id, Long productId, Integer quantity, String transactionType, String referenceType, Long referenceId, LocalDateTime transactionDate, String notes) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
        this.transactionDate = transactionDate;
        this.notes = notes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

