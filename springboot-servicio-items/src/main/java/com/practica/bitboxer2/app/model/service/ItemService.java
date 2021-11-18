package com.practica.bitboxer2.app.model.service;

import com.practica.bitboxer2.app.model.entity.Item;
import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.entity.Supplier;
import com.practica.bitboxer2.app.model.enums.StateEnum;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ItemService {

    public CompletableFuture<List<Item>> findAll();

    public CompletableFuture<Item> findByItemCode(String itemCode);

    public CompletableFuture<List<Item>> findByState(StateEnum state);

    public CompletableFuture<List<Supplier>> findBySuppliers(Long id);

    public CompletableFuture<List<PriceReduction>> findByPriceReductions(Long id);

    public CompletableFuture<Item> save(Item item) throws Exception;

    public CompletableFuture<Void> delete(Long id);
}