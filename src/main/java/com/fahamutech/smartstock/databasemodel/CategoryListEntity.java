package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "category_list", schema = "lb", catalog = "")
public class CategoryListEntity {
    private String category;

    @Basic
    @Column(name = "category", nullable = true, length = 100)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryListEntity that = (CategoryListEntity) o;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {

        return Objects.hash(category);
    }
}
