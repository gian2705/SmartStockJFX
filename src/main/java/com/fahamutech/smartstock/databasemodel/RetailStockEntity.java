package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "retail_stock", schema = "lb", catalog = "")
public class RetailStockEntity {
    private String product;
    private String unit;
    private String category;
    private String shelf;
    private Integer quantity;
    private Integer wquantity;
    private String qStatus;
    private Integer reorder;
    private String supplier;
    private Double purchase;
    private Double sell;
    private Double wsell;
    private Double profit;
    private Double times;
    private Date expire;

    @Basic
    @Column(name = "product", nullable = true, length = 150)
    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    @Basic
    @Column(name = "unit", nullable = true, length = 30)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Basic
    @Column(name = "category", nullable = true, length = 50)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "shelf", nullable = true, length = 50)
    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
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
    @Column(name = "wquantity", nullable = true)
    public Integer getWquantity() {
        return wquantity;
    }

    public void setWquantity(Integer wquantity) {
        this.wquantity = wquantity;
    }

    @Basic
    @Column(name = "q_status", nullable = true, length = 45)
    public String getqStatus() {
        return qStatus;
    }

    public void setqStatus(String qStatus) {
        this.qStatus = qStatus;
    }

    @Basic
    @Column(name = "reorder", nullable = true)
    public Integer getReorder() {
        return reorder;
    }

    public void setReorder(Integer reorder) {
        this.reorder = reorder;
    }

    @Basic
    @Column(name = "supplier", nullable = true, length = 100)
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
    @Column(name = "sell", nullable = true, precision = 0)
    public Double getSell() {
        return sell;
    }

    public void setSell(Double sell) {
        this.sell = sell;
    }

    @Basic
    @Column(name = "wsell", nullable = true, precision = 0)
    public Double getWsell() {
        return wsell;
    }

    public void setWsell(Double wsell) {
        this.wsell = wsell;
    }

    @Basic
    @Column(name = "profit", nullable = true, precision = 0)
    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    @Basic
    @Column(name = "times", nullable = true, precision = 0)
    public Double getTimes() {
        return times;
    }

    public void setTimes(Double times) {
        this.times = times;
    }

    @Basic
    @Column(name = "expire", nullable = true)
    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetailStockEntity that = (RetailStockEntity) o;
        return Objects.equals(product, that.product) &&
                Objects.equals(unit, that.unit) &&
                Objects.equals(category, that.category) &&
                Objects.equals(shelf, that.shelf) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(wquantity, that.wquantity) &&
                Objects.equals(qStatus, that.qStatus) &&
                Objects.equals(reorder, that.reorder) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(purchase, that.purchase) &&
                Objects.equals(sell, that.sell) &&
                Objects.equals(wsell, that.wsell) &&
                Objects.equals(profit, that.profit) &&
                Objects.equals(times, that.times) &&
                Objects.equals(expire, that.expire);
    }

    @Override
    public int hashCode() {

        return Objects.hash(product, unit, category, shelf, quantity, wquantity, qStatus, reorder, supplier, purchase, sell, wsell, profit, times, expire);
    }
}
