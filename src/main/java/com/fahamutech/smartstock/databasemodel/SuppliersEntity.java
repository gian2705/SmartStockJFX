package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "suppliers", schema = "lb", catalog = "")
public class SuppliersEntity {
    private String productId;
    private String categoryId;
    private String unit;
    private Double purchase;

    @Basic
    @Column(name = "product_id", nullable = true, length = 100)
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "category_id", nullable = true, length = 100)
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
    @Column(name = "purchase", nullable = true, precision = 0)
    public Double getPurchase() {
        return purchase;
    }

    public void setPurchase(Double purchase) {
        this.purchase = purchase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SuppliersEntity that = (SuppliersEntity) o;
        return Objects.equals(productId, that.productId) &&
                Objects.equals(categoryId, that.categoryId) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(purchase, that.purchase);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, categoryId, unit, purchase);
    }
}
