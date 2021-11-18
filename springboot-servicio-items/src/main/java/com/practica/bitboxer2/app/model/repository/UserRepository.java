package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select u from User u where u.name like %:name%")
    public List<Optional<User>> findByNameLike(String name);

    public Optional<User> findByName(String name);

    public Optional<User> findByLastName(String name);

    public Optional<User> findByUsername(String name);

    @Query(value = "select u from User u inner join u.roles role where lower(role.name) = lower(?1)")
    public List<Optional<User>> findUserByRol(String rol);
}