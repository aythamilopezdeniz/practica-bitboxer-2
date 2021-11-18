package com.springboot.servicio.app.suministradores.model.service;

import com.springboot.servicio.app.suministradores.model.entity.Supplier;
import com.springboot.servicio.app.suministradores.model.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SupplierServiceImpl implements SupplierService {

    private Logger LOGGER = LoggerFactory.getLogger(SupplierServiceImpl.class);

    @Autowired
    private SupplierRepository supplierRepository;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<Supplier>> findAll() {
        LOGGER.info("Petición obtener la lista de suministradores");
        List<Supplier> suppliers = supplierRepository.findAll();
        LOGGER.info("Suppliers, {}", suppliers);
        LOGGER.info("Supplier findAll completado");
        return CompletableFuture.completedFuture(suppliers);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<Supplier> findById(Long id) {
        LOGGER.info("Petición de búsqueda de un suministrador por el id");
        Supplier supplier = supplierRepository.findById(id).orElseThrow();
        LOGGER.info("Supplier, {}", supplier);
        LOGGER.info("Supplier findById completado");
        return CompletableFuture.completedFuture(supplier);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<Supplier> findByName(String name) {
        LOGGER.info("Petición de búsqueda de un suministrador por el nombre");
        Supplier supplier = supplierRepository.findByName(name).orElseThrow();
        LOGGER.info("Supplier, {}", supplier);
        LOGGER.info("Supplier findByName completado");
        return CompletableFuture.completedFuture(supplier);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<Supplier>> findByCountry(String country) {
        List<Supplier> suppliers = new ArrayList<>();
        LOGGER.info("Filtrado de suministradores por país");
        supplierRepository.findByCountry(country).forEach((c) -> suppliers.add(c.orElseThrow()));
        LOGGER.info("Suppliers, {}", suppliers);
        LOGGER.info("Suppliers findByCountry completado");
        return CompletableFuture.completedFuture(suppliers);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<Supplier> save(Supplier supplier) {
        LOGGER.info("Guardando suministrador en BD");
        Supplier supplierSave = supplierRepository.save(supplier);
        LOGGER.info("Supplier, {}", supplierSave);
        LOGGER.info("Supplier save completado");
        return CompletableFuture.completedFuture(supplierSave);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Borrando suministrador en BD");
            supplierRepository.deleteById(id);
        });
    }
}