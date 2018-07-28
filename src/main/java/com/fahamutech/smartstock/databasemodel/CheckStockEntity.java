package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "check_stock", schema = "lb", catalog = "")
public class CheckStockEntity {
    private String product;
    private String status;

    @Basic
    @Column(name = "product", nullable = false, length = 100)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 5)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckStockEntity that = (CheckStockEntity) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {

        return Objects.hash(product, status);
    }
}
