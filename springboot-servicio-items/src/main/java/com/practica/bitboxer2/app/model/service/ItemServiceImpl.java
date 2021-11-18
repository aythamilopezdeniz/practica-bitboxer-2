package com.practica.bitboxer2.app.model.service;

import com.practica.bitboxer2.app.model.entity.Item;
import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.entity.Supplier;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import com.practica.bitboxer2.app.model.repository.ItemRepository;
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
public class ItemServiceImpl implements ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemServiceImpl.class);

    @Autowired
    private ItemRepository itemRepository;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<Item>> findAll() {
        LOGGER.info("Realizando la búsqueda de items");
        List<Item> items = itemRepository.findAll();
        LOGGER.info("Items obtenidos, {}", items);
        return priceReductions(items);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<Item> findByItemCode(String itemCode) {
        LOGGER.info("Petición de búsqueda de un item por su código de Item");
        Item item = itemRepository.findByItemCode(itemCode).orElseThrow();
        LOGGER.info("Item obtenido, {}", item);
            for (PriceReduction price : item.getPriceReductions()) {
                if (price.getState().getName().equals("Active"))
                    item = applyDiscount(item, price.getReductionPrice());
            }
        LOGGER.info("Item findByItemCode completado");
        return CompletableFuture.completedFuture(item);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<Item>> findByState(StateEnum state) {
        List<Item> items = new ArrayList<>();
        LOGGER.info("Filtrar los items por el campo estado");
        itemRepository.findByState(state).forEach((el) -> items.add(el.orElseThrow()));
        LOGGER.info("Items obtenidos, {}", items);
        LOGGER.info("Item findByState completado");
        return priceReductions(items);
    }

    private CompletableFuture<List<Item>> priceReductions(List<Item> items) {
        return CompletableFuture.supplyAsync(() -> {
            int i = 0;
            for (Item item : items) {
                for (PriceReduction price : item.getPriceReductions()) {
                    if (price.getState().getName().equals("Active"))
                        items.set(i, applyDiscount(item, price.getReductionPrice()));
                }
            }
            return items;
        }).thenApply(discount -> {
            LOGGER.info("Items completado");
            return discount;
        });
    }

    private Item applyDiscount(Item item, Double discount) {
        if (item != null && discount != null)
            item.setPrice(item.getPrice() - discount);
        return item;
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<Supplier>> findBySuppliers(Long id) {
        List<Supplier> suppliers = new ArrayList<>();
        LOGGER.info("Listado de suministradores asociados al item");
        itemRepository.findBySuppliers(id).forEach((el) -> suppliers.add(el.orElseThrow()));
        LOGGER.info("Items obtenidos, {}", suppliers);
        LOGGER.info("Items findBySuppliers completado");
        return CompletableFuture.completedFuture(suppliers);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<PriceReduction>> findByPriceReductions(Long id) {
        List<PriceReduction> priceReductions = new ArrayList<>();
        LOGGER.info("Listado de precios reducidos asociados al item");
        itemRepository.findByPriceReductions(id).forEach((el) -> priceReductions.add(el.orElseThrow()));
        LOGGER.info("Items obtenidos, {}", priceReductions);
        LOGGER.info("Items findByPriceReductions completado");
        return CompletableFuture.completedFuture(priceReductions);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<Item> save(Item item) throws Exception {
        LOGGER.info("Guardado del item en BD");
        Item itemSave = itemRepository.save(item);
        LOGGER.info("Item guardado, {}", itemSave);
        LOGGER.info("Item save completado");
        return CompletableFuture.completedFuture(itemSave);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<Void> delete(Long id) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Borrando item de BD");
            itemRepository.deleteById(id);
        });
    }
}