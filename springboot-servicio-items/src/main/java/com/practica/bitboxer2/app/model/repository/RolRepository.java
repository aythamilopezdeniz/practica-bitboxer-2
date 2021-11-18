package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {

    @Query(value = "select r from Rol r where lower(r.name) = lower(?1)")
    public Optional<Rol> findByName(String name);
}