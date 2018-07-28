package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "paid_invoice", schema = "lb", catalog = "")
public class PaidInvoiceEntity {
    private Date date;
    private String invoice;
    private String supplier;
    private String method;
    private String reference;

    @Basic
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "invoice", nullable = true, length = 75)
    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    @Basic
    @Column(name = "supplier", nullable = true, length = 45)
    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Basic
    @Column(name = "method", nullable = true, length = 45)
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Basic
    @Column(name = "reference", nullable = true, length = 75)
    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaidInvoiceEntity that = (PaidInvoiceEntity) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(invoice, that.invoice) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(method, that.method) &&
                Objects.equals(reference, that.reference);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, invoice, supplier, method, reference);
    }
}
