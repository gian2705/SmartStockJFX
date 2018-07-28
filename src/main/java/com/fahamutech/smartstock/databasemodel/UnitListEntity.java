package com.fahamutech.smartstock.databasemodel;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "unit_list", schema = "lb", catalog = "")
public class UnitListEntity {
    private String unit;

    @Basic
    @Column(name = "unit", nullable = true, length = 100)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitListEntity that = (UnitListEntity) o;
        return Objects.equals(unit, that.unit);
    }

    @Override
    public int hashCode() {

        return Objects.hash(unit);
    }
}
