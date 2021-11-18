package com.springboot.servicio.app.prices.model.service;

import com.springboot.servicio.app.prices.model.entity.PriceReduction;
import com.springboot.servicio.app.prices.model.enums.StateEnum;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PriceReductionService {

    public CompletableFuture<List<PriceReduction>> findAll();

    public CompletableFuture<PriceReduction> findById(Long id);

    public CompletableFuture<List<PriceReduction>> findByState(StateEnum state);

    public CompletableFuture<PriceReduction> save(PriceReduction priceReduction);

    public CompletableFuture<Void> deleteById(Long id);
}