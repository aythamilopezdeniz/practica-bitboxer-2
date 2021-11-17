package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SupplierRepositoryIntegrationTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Test
    void testFindAll() {
        List<Supplier> suppliers = supplierRepository.findAll();
        assertFalse(suppliers.isEmpty());
        assertEquals(5, suppliers.size());
    }

    @Test
    void testFindByName() {
        Optional<Supplier> supplier = supplierRepository.findByName("Ferreteria S.L");
        assertFalse(supplier.isEmpty());
        assertEquals("France", supplier.orElseThrow().getCountry());

        List<Optional<Supplier>> suppliers = supplierRepository.findByNameLike("l");
        assertFalse(suppliers.isEmpty());
        assertEquals(5, suppliers.size());
        assertEquals("ferreteria S.L", suppliers.get(4).orElseThrow().getName());
    }

    @Test
    void testFindByCountry() {
        List<Optional<Supplier>> suppliers = supplierRepository.findByCountry("Spain");
        assertFalse(suppliers.isEmpty());
        assertEquals(3, suppliers.size());
        assertEquals("supplier2", suppliers.get(0).orElseThrow().getName());
    }

    @Test
    void testSaveSupplier() {
        Supplier supplier = new Supplier(null, "alcampo", "Italy");
        Supplier supplierSave = supplierRepository.save(supplier);
        assertNotNull(supplierSave);

        Optional<Supplier> newSupplier = supplierRepository.findByName("alcampo");
        assertFalse(newSupplier.isEmpty());
        assertEquals("Italy", newSupplier.orElseThrow().getCountry());
    }

    @Test
    void testUpdateSupplier() {
        Optional<Supplier> supplier = supplierRepository.findById(1L);
        supplier.orElseThrow().setName("Carrefour");

        Supplier supplierSave = supplierRepository.save(supplier.orElseThrow());
        assertNotNull(supplierSave);
        assertEquals("Carrefour", supplierRepository.findById(1L).orElseThrow().getName());
    }

    @Test
    void testDeleteSupplier() {
        Optional<Supplier> suppliers = supplierRepository.findByName("ferreteria S.L");
        supplierRepository.delete(suppliers.orElseThrow());
        assertThrows(DataIntegrityViolationException.class, () -> supplierRepository.findByName("ferreteria S.L").orElseThrow());
    }
}