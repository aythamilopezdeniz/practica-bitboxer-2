package com.practica.bitboxer2.app.model.service;

import com.practica.bitboxer2.app.model.entity.Supplier;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface SupplierService {

    public CompletableFuture<List<Supplier>> findAll();

    public CompletableFuture<Supplier> findById(Long id);

    public CompletableFuture<Supplier> findByName(String name);

    public CompletableFuture<List<Supplier>> findByCountry(String country);

    public CompletableFuture<Supplier> save(Supplier supplier);

    public CompletableFuture<Void> delete(Long id);
}