package com.fahamutech.smartstock.databasemodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "cash_purchase", schema = "lb", catalog = "")
public class CashPurchaseEntity {
    private int id;
    private Date date;
    private String receipt;
    private String product;
    private String category;
    private String unit;
    private String supplier;
    private Integer squantity;
    private Integer quantity;
    private Double purchase;
    private Double amount;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "receipt", nullable = true, length = -1)
    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    @Basic
    @Column(name = "product", nullable = true, length = 150)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "category", nullable = true, length = 45)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "unit", nullable = true, length = 45)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "supplier", nullable = true, length = 80)
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Basic
    @Column(name = "squantity", nullable = true)
    public Integer getSquantity() {
        return squantity;
    }

    public void setSquantity(Integer squantity) {
        this.squantity = squantity;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashPurchaseEntity that = (CashPurchaseEntity) o;
        return id == that.id &&
                Objects.equals(date, that.date) &&
                Objects.equals(receipt, that.receipt) &&
                Objects.equals(product, that.product) &&
                Objects.equals(category, that.category) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(squantity, that.squantity) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(purchase, that.purchase) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, date, receipt, product, category, unit, supplier, squantity, quantity, purchase, amount);
    }
}
