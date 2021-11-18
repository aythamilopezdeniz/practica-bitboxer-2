package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query(value = "select s from Supplier s where lower(s.name) = lower(?1)")
    public Optional<Supplier> findByName(String name);

    @Query(value = "select s from Supplier s where lower(s.name) like %:name%")
    public List<Optional<Supplier>> findByNameLike(String name);

    @Query(value = "select s from Supplier s where lower(s.country) = lower(?1)")
    public List<Optional<Supplier>> findByCountry(String country);
}