package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Item;
import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.entity.Supplier;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select i from Item i where lower(i.itemCode) = lower(?1)")
    public Optional<Item> findByItemCode(String code);

    public List<Optional<Item>> findByState(StateEnum state);

    @Query(value = "select i.suppliers from Item i where i.id = ?1")
    public List<Optional<Supplier>> findBySuppliers(Long item_id);

    @Query(value = "select i.priceReductions from Item i where i.id = ?1")
    public List<Optional<PriceReduction>> findByPriceReductions(Long item_id);
}