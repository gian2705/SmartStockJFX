package com.fahamutech.smartstock.databasemodel;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "cash_sale", schema = "lb", catalog = "")
public class CashSaleEntity {
    private int idn;
    private String id;
    private Date date;
    private String product;
    private String category;
    private String unit;
    private Integer quantity;
    private Double amount;
    private Double discount;
    private String user;

    @Id
    @Column(name = "idn", nullable = false)
    public int getIdn() {
        return idn;
    }

    public void setIdn(int idn) {
        this.idn = idn;
    }

    @Basic
    @Column(name = "id", nullable = true, length = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date", nullable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
    @Column(name = "category", nullable = true, length = 100)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "unit", nullable = true, length = 50)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
    @Column(name = "amount", nullable = true, precision = 0)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Basic
    @Column(name = "discount", nullable = true, precision = 0)
    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "user", nullable = true, length = 50)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CashSaleEntity that = (CashSaleEntity) o;
        return idn == that.idn &&
                Objects.equals(id, that.id) &&
                Objects.equals(date, that.date) &&
                Objects.equals(product, that.product) &&
                Objects.equals(category, that.category) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(discount, that.discount) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idn, id, date, product, category, unit, quantity, amount, discount, user);
    }
}
