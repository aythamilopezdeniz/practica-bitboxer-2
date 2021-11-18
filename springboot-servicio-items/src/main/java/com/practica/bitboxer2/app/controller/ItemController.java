package com.practica.bitboxer2.app.controller;

import com.practica.bitboxer2.app.model.entity.Item;
import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.entity.Supplier;
import com.practica.bitboxer2.app.model.entity.User;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import com.practica.bitboxer2.app.model.service.ItemService;
import com.practica.bitboxer2.app.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/items")
public class ItemController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Obteniendo lista de items");
        List<Item> future = null;
        try {
            future = itemService.findAll().get();
            if (future.isEmpty()) {
                LOGGER.error("Items no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Items obtenidos");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de items - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de items - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(future);
    }

    @GetMapping(value = "/itemCode/{code}")
    public ResponseEntity<?> findByItemCode(@PathVariable(value = "code") String code) {
        LOGGER.info("Obteniendo item por itemCode");
        Item item = null;
        try {
            CompletableFuture<Item> future = itemService.findByItemCode(code);
            item = future.whenCompleteAsync((r, e) -> System.out.println("Result findByItemCode Async: " + r), executor).get();
            if (item == null) {
                LOGGER.error("Item no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Item obtenido");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo un item - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo un item - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(item);
    }

    @GetMapping(value = "/state/{state}")
    public ResponseEntity<?> findByState(@PathVariable(value = "state") StateEnum state) {
        LOGGER.info("Obteniendo items por estado");
        List<Item> items = null;
        try {
            CompletableFuture<List<Item>> future = itemService.findByState(state);
            items = future.whenCompleteAsync((r, e) -> System.out.println("Result findByState: Async: " + r), executor).get();
            if (items.isEmpty()) {
                LOGGER.error("Items no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Items obtenidos");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de items - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de items - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping(value = "/suppliers/{id}")
    public ResponseEntity<?> findBySuppliers(@PathVariable(value = "id") Long id) {
        LOGGER.info("Obteniendo lista de suppliers del item");
        List<Supplier> items = null;
        try {
            CompletableFuture<List<Supplier>> future = itemService.findBySuppliers(id);
            items = future.whenCompleteAsync((r, e) -> System.out.println("Result findBySuppliers Async " + r), executor).get();
            if (items.isEmpty()) {
                LOGGER.error("Items no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Items obtenidos");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de items - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de items - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping(value = "/price_reductions/{id}")
    public ResponseEntity<?> findByPriceReductions(@PathVariable(value = "id") Long id) {
        LOGGER.info("Obteniendo lista de precios reducidos del item");
        List<PriceReduction> items = null;
        try {
            CompletableFuture<List<PriceReduction>> future = itemService.findByPriceReductions(id);
            items = future.whenCompleteAsync((r, e) -> System.out.println("Result findByPriceReductions Async " + r), executor).get();
            if (items.isEmpty()) {
                LOGGER.error("Items no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Items obtenidos");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de items - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de items - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @PostMapping
    public ResponseEntity<?> saveItem(@RequestBody Item item) {
        LOGGER.info("Guardando un item en BD");
        Item itemSave = null;
        try {
            CompletableFuture<Item> future = itemService.save(item);
            itemSave = future.whenCompleteAsync((r, e) -> System.out.println("Result saveItem Async " + r), executor).get();
            if (itemSave == null) {
                LOGGER.error("Item no guardado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            LOGGER.info("Item guardado");
        } catch (Exception e) {
            LOGGER.error("Error item ya guardando - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSave);
    }

    @PutMapping(value = "/{itemCode}")
    public ResponseEntity<?> updateItem(@PathVariable(value = "itemCode") String itemCode, @RequestBody Item item)
            throws ExecutionException, InterruptedException {
        LOGGER.info("Actualizando un item en BD");
        ResponseEntity<Item> responseEntity = itemService.findByItemCode(itemCode).thenComposeAsync(itemFound -> {
            if (itemFound == null) {
                LOGGER.error("Item no encontrado");
                throw new CompletionException(new Exception("Item no encontrado"));
            }

            if (item.getDescription() != null)
                itemFound.setDescription(item.getDescription());
            if (item.getPrice() != null)
                itemFound.setPrice(item.getPrice());
            if (item.getState() != null)
                itemFound.setState(item.getState());
            if (item.getCreationDate() != null)
                itemFound.setCreationDate(item.getCreationDate());

            if (item.getSuppliers() != null) {
                List<Supplier> suppliers = new ArrayList<>(itemFound.getSuppliers());
                for (Supplier supplier : item.getSuppliers())
                    suppliers.add(supplier);
                List<Supplier> uniqueSuppliers = suppliers.stream().distinct().collect(Collectors.toList());
                itemFound.setSuppliers(uniqueSuppliers);
            }

            if (item.getPriceReductions() != null) {
                List<PriceReduction> priceReductions = new ArrayList<>(itemFound.getPriceReductions());
                for (PriceReduction priceReduction : item.getPriceReductions())
                    priceReductions.add(priceReduction);
                List<PriceReduction> uniquePriceReductions = priceReductions.stream().distinct().collect(Collectors.toList());
                itemFound.setPriceReductions(uniquePriceReductions);
            }

            if (!itemFound.getCreator().equals(item.getCreator()) && item.getCreator() != null &&
                (itemFound.getCreator().getId() != item.getCreator().getId())) {
                User user = null;
                try {
                    user = userService.save(item.getCreator()).get();
                } catch (InterruptedException e) {
                } catch (ExecutionException e) {
                    LOGGER.error("Usuario no actualizado en item");
                }
                itemFound.setCreator(user);
            }
            try {
                return itemService.save(itemFound);
            } catch (Exception e) {
                throw new RuntimeException("Item no actualizado por el usuario " + e.getStackTrace());
            }
        }).thenApply((itemUpdate) -> {
            LOGGER.info("Item actualizado " + itemUpdate);
            return ResponseEntity.status(HttpStatus.CREATED).body(itemUpdate);
        }).exceptionally((throwable) -> {
            LOGGER.error("Item no actualizado " + throwable.getStackTrace());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }).whenCompleteAsync((r, e) -> System.out.println("Result saveItem Async " + r)).get();
        return responseEntity;
    }

    @DeleteMapping(value = "/{itemCode}")
    public ResponseEntity<?> delete(@PathVariable(value = "itemCode") String itemCode) {
        LOGGER.info("Eliminando un item en BD");
        Item itemDelete = null;
        try {
            CompletableFuture<Item> future = itemService.findByItemCode(itemCode);
            itemDelete = future.whenCompleteAsync((r, e) -> System.out.println("Result deleteItem Async " + r), executor).get();
            if (itemDelete == null) {
                LOGGER.error("Item no guardado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            itemService.delete(itemDelete.getId());
            LOGGER.info("Item eliminado");
        } catch (InterruptedException e) {
            LOGGER.error("Error eliminando un item - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error eliminando un item - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}