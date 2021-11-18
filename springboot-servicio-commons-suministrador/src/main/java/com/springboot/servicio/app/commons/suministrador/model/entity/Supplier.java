package com.springboot.servicio.app.commons.suministrador.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "SUPPLIERS")
public class Supplier implements Serializable {

    private static final long serialVersionUID = -8032082945743597652L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "NAME", unique = true, length = 30)
    private String name;

    @NotNull
    @NotEmpty
    @Column(name = "COUNTRY", length = 50)
    private String country;

    public Supplier() {
    }

    public Supplier(Long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(id, supplier.id) &&
                Objects.equals(name, supplier.name) &&
                Objects.equals(country, supplier.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, country);
    }

    @Override
    public String toString() {
        return "Supplier{id=" + id + ", name='" + name + '\'' + ", country='" + country + '\'' + '}';
    }
}