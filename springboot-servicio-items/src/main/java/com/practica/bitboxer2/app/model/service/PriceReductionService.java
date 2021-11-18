package com.practica.bitboxer2.app.model.service;

import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.enums.StateEnum;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PriceReductionService {

    public CompletableFuture<List<PriceReduction>> findAll();

    public CompletableFuture<PriceReduction> findById(Long id);

    public CompletableFuture<List<PriceReduction>> findByState(StateEnum state);

    public CompletableFuture<PriceReduction> save(PriceReduction priceReduction);

    public CompletableFuture<Void> deleteById(Long id);
}