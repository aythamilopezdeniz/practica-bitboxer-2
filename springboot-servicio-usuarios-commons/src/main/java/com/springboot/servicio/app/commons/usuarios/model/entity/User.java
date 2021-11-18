package com.springboot.servicio.app.commons.usuarios.model.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

    private static final long serialVersionUID = -8778139990223052790L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "NAME", length = 20)
    private String name;

    @NotNull
    @Column(name = "LASTNAME", length = 20)
    private String lastName;

    @NotNull
    @NotEmpty
    @Column(name = "USERNAME", unique = true, length = 20)
    private String username;

    @NotNull
    @NotEmpty
    @Column(name = "PASSWORD", length = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_ID", "ROLE_ID"})})
    private List<Rol> roles;

    public User() {
    }

    public User(Long id, String name, String lastName, String username, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public void addRoles(Rol rol) {
        if (rol != null) {
            if (this.roles == null) this.roles = new ArrayList<>();
            this.roles.add(rol);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, username, password, roles);
    }

    @Override
    public String toString() {
        String user = "User{id=" + id + ", name='" + name + '\'' + ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' + ", password='" + password;
        if (roles == null) return user;
        return user.concat('\'' + ", roles=" + roles + '}');
    }
}