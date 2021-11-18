package com.practica.bitboxer2.app.model.service;

import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import com.practica.bitboxer2.app.model.repository.PriceReductionRepository;
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
public class PriceReductionServiceImpl implements PriceReductionService {

    private Logger LOGGER = LoggerFactory.getLogger(Class.class);

    @Autowired
    private PriceReductionRepository priceReductionRepository;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<PriceReduction>> findAll() {
        LOGGER.info("Petición obtener la lista de precios reducidos");
        List<PriceReduction> priceReductions = priceReductionRepository.findAll();
        LOGGER.info("Price_Reductions, {}", priceReductions);
        LOGGER.info("Price_Reduction findAll completado");
        return CompletableFuture.completedFuture(priceReductions);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<PriceReduction> findById(Long id) {
        LOGGER.info("Petición de búsqueda de un precio por el id");
        PriceReduction priceReduction = priceReductionRepository.findById(id).orElseThrow();
        LOGGER.info("Precio reducido, {}", priceReduction);
        LOGGER.info("Precio reducido findById completado");
        return CompletableFuture.completedFuture(priceReduction);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<PriceReduction>> findByState(StateEnum state) {
        List<PriceReduction> priceReductions = new ArrayList<>();
        LOGGER.info("Filtrado precios reducidos por el estado");
        priceReductionRepository.findByState(state).forEach((pr) -> priceReductions.add(pr.orElseThrow()));
        LOGGER.info("Price_Reductions, {}", priceReductions);
        LOGGER.info("Price_Reductions findByState completado");
        return CompletableFuture.completedFuture(priceReductions);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<PriceReduction> save(PriceReduction priceReduction) {
        LOGGER.info("Guardando precio reducido en BD");
        PriceReduction priceReductionSave = priceReductionRepository.save(priceReduction);
        LOGGER.info("Price_Reduction, {}", priceReductionSave);
        LOGGER.info("Price_Reduction save completado");
        return CompletableFuture.completedFuture(priceReductionSave);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<Void> deleteById(Long id) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Borrando precio reducido en BD");
            priceReductionRepository.deleteById(id);
        });
    }
}