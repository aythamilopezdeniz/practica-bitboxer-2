package com.practica.bitboxer2.app.controller;

import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import com.practica.bitboxer2.app.model.service.PriceReductionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/api/prices")
public class PriceReductionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private PriceReductionService priceReductionService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Obteniendo lista de precios reducidos");
        List<PriceReduction> priceReductions = null;
        try {
            priceReductions = priceReductionService.findAll().get();
            if (priceReductions.isEmpty()) {
                LOGGER.info("Lista de precios no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Lista de precios obtenidos");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de precios - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de precios - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(priceReductions);
    }

    @GetMapping(value = "/{state}")
    public ResponseEntity<?> findByState(@PathVariable(value = "state") StateEnum state) {
        List<PriceReduction> priceReductions = null;
        try {
            priceReductions = priceReductionService.findByState(state).get();
            if (priceReductions.isEmpty()) {
                LOGGER.error("Lista de precios no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de precios por estado - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de precios por estado - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(priceReductions);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody PriceReduction priceReduction) {
        LOGGER.info("Guardando un precio en BD");
        PriceReduction priceReductionUpdate = null;
        try {
            priceReductionUpdate = priceReductionService.save(priceReduction).get();
            if (priceReductionUpdate == null) {
                LOGGER.error("Precio no guardado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (InterruptedException e) {
            LOGGER.error("Error guardando precio en BD - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error guardando precio en BD - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(priceReductionUpdate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,
            @RequestBody PriceReduction priceReduction) {
        LOGGER.info("Actualizando un precio en BD");
        PriceReduction priceReductionUpdate = null;
        try {
            priceReductionUpdate = priceReductionService.findById(id)
                .thenComposeAsync(priceFound -> {
                    if (priceFound == null) {
                        LOGGER.error("Precio no encontrado");
                        throw new RuntimeException("Precio no encontrado");
                    }
                    if (priceReduction.getReductionPrice() != null)
                        priceFound.setReductionPrice(priceReduction.getReductionPrice());
                    if (priceReduction.getStartDate() != null)
                        priceFound.setStartDate(priceReduction.getStartDate());
                    if (priceReduction.getReductionPrice() != null)
                        priceFound.setStartDate(priceReduction.getEndDate());
                    if (priceReduction.getState() != null)
                        priceFound.setState(priceReduction.getState());
                    return priceReductionService.save(priceFound);
                }).thenApply((priceUpdate) -> {
                    LOGGER.info("Precio actualizado");
                    return priceUpdate;
                }).exceptionally(throwable -> {
                    LOGGER.error("Precio no actualizado " + throwable);
                    throw new RuntimeException(throwable);
                }).get();
        } catch (InterruptedException e) {
            LOGGER.error("Error actualizando precio en BD - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error actualizando precio en BD - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(priceReductionUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        LOGGER.info("Borrando un precio en BD");
        try {
            priceReductionService.deleteById(id).get();
            LOGGER.info("Precio eliminado");
        } catch (InterruptedException e) {
            LOGGER.error("Error eliminando un precio - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error eliminando un precio - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}