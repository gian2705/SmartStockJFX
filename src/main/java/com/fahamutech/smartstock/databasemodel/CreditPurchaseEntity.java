package com.fahamutech.smartstock.databasemodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "credit_purchase", schema = "lb", catalog = "")
public class CreditPurchaseEntity {
    private int id;
    private String date;
    private String due;
    private String invoice;
    private String product;
    private String category;
    private String unit;
    private String supplier;
    private Integer quantity;
    private Double purchase;
    private Double amount;
    private String status;
    private Integer stockQuantity;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false, length = 50)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "due", nullable = true, length = 100)
    public String getDue() {
        return due;
    }

    public void setDue(String due) {
        this.due = due;
    }

    @Basic
    @Column(name = "invoice", nullable = true, length = 100)
    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    @Basic
    @Column(name = "product", nullable = true, length = 200)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "category", nullable = true, length = 100)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "unit", nullable = true, length = 60)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "supplier", nullable = true, length = 200)
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Basic
    @Column(name = "quantity", nullable = true)
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "purchase", nullable = true, precision = 0)
    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    @Basic
    @Column(name = "amount", nullable = true, precision = 0)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "stock_quantity", nullable = true)
    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditPurchaseEntity that = (CreditPurchaseEntity) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(due, that.due) &&
                Objects.equals(invoice, that.invoice) &&
                Objects.equals(product, that.product) &&
                Objects.equals(category, that.category) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(purchase, that.purchase) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(status, that.status) &&
                Objects.equals(stockQuantity, that.stockQuantity);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, due, invoice, product, category, unit, supplier, quantity, purchase, amount, status, stockQuantity);
    }
}
