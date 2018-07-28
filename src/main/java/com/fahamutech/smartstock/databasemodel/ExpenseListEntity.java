package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "expense_list", schema = "lb", catalog = "")
public class ExpenseListEntity {
    private String date;
    private String name;
    private String description;
    private String user;
    private Double amount;

    @Basic
    @Column(name = "date", nullable = false, length = 90)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "user", nullable = true, length = 90)
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
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
        ExpenseListEntity that = (ExpenseListEntity) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(user, that.user) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(date, name, description, user, amount);
    }
}
