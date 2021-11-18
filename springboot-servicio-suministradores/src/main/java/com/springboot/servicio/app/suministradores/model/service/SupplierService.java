package com.springboot.servicio.app.suministradores.model.service;

import com.springboot.servicio.app.suministradores.model.entity.Supplier;

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