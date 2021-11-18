package com.springboot.servicio.app.suministradores.controller;

import com.springboot.servicio.app.suministradores.model.entity.Supplier;
import com.springboot.servicio.app.suministradores.model.service.SupplierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/suppliers")
public class SupplierController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierController.class);

    @Autowired
    private SupplierService supplierService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Obteniendo lista de suministradores");
        List<Supplier> suppliers = null;
        try {
            suppliers = supplierService.findAll().get();
            if (suppliers.isEmpty()) {
                LOGGER.error("Lista de suministradores no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de suministradores - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de suministradores - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        Supplier supplier = null;
        try {
            supplier = supplierService.findById(id).get();
            if (supplier == null) {
                LOGGER.error("Suministrador no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo suministrador por id - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo suministrador por id - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(supplier);
    }

    @GetMapping(value = "/name/{name}")
    public ResponseEntity<?> findByName(@PathVariable(value = "name") String name) {
        Supplier supplier = null;
        try {
            supplier = supplierService.findByName(name).get();
            if (supplier == null) {
                LOGGER.error("Suministrador no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo suministrador por nombre - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo suministrador por nombre - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(supplier);
    }

    @GetMapping(value = "/country/{country}")
    public ResponseEntity<?> findByCountry(@PathVariable(value = "country") String country) {
        List<Supplier> suppliers = null;
        try {
            suppliers = supplierService.findByCountry(country).get();
            if (suppliers.isEmpty()) {
                LOGGER.error("Suministradores no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de suministradores por país - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de suministradores por país - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(suppliers);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Supplier supplier) {
        Supplier supplierSave = null;
        try {
            supplierSave = supplierService.save(supplier).get();
            if (supplierSave == null) {
                LOGGER.error("Suministrador no guardado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error guardando suministrador en BD - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error guardando suministrador en BD - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierSave);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody Supplier supplier) {
        Supplier supplierUpdate = null;
        try {
            supplierUpdate = supplierService.findById(id).thenComposeAsync(supplierFound -> {
                if (supplierFound == null) {
                    LOGGER.error("Suministrador no encontrado");
                    throw new RuntimeException("Suministrador no encontrado");
                }
                if (supplier.getName() != null)
                    supplierFound.setName(supplier.getName());
                if (supplier.getCountry() != null)
                    supplierFound.setCountry(supplier.getCountry());
                return supplierService.save(supplierFound);
            }).thenApply(update -> {
                LOGGER.info("Suministrador actualizado");
                return update;
            }).exceptionally(throwable -> {
                LOGGER.error("Precio no actualizado " + throwable.getStackTrace());
                throw new RuntimeException("");
            }).get();
        } catch (InterruptedException e) {
            LOGGER.error("Error actualizando suministrador en BD - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error actualizando suministrador en BD - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(supplierUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        LOGGER.info("Borrando un suministrador en BD");
        try {
            supplierService.delete(id).get();
            LOGGER.info("Suministrador eliminado");
        } catch (InterruptedException e) {
            LOGGER.error("Error eliminando suministrador - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error eliminando suministrador - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}