package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "receipt", schema = "lb", catalog = "")
public class ReceiptEntity {
    private Date date;
    private String receipt;

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "receipt", nullable = true, length = 80)
    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReceiptEntity that = (ReceiptEntity) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(receipt, that.receipt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, receipt);
    }
}
