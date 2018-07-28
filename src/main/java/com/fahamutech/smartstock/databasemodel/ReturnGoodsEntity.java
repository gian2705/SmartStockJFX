package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "return_goods", schema = "lb", catalog = "")
public class ReturnGoodsEntity {
    private String product;
    private String description;
    private Double amount;
    private String user;

    @Basic
    @Column(name = "product", nullable = false, length = 100)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        ReturnGoodsEntity that = (ReturnGoodsEntity) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(description, that.description) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(product, description, amount, user);
    }
}
