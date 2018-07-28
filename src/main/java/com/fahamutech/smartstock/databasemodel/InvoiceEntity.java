package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "invoice", schema = "lb", catalog = "")
public class InvoiceEntity {
    private String date;
    private String invoice;

    @Basic
    @Column(name = "date", nullable = false, length = 45)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "invoice", nullable = true, length = 100)
    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceEntity that = (InvoiceEntity) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(invoice, that.invoice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, invoice);
    }
}
