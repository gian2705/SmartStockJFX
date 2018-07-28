package com.fahamutech.smartstock.databasemodel;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "login_info", schema = "lb", catalog = "")
public class LoginInfoEntity {
    private String name;
    private String password;
    private String userType;
    private int id;

    @Basic
    @Column(name = "name", nullable = false, length = -1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "password", nullable = true, length = -1)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "user_type", nullable = false, length = 45)
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginInfoEntity that = (LoginInfoEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(password, that.password) &&
                Objects.equals(userType, that.userType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, password, userType, id);
    }
}
