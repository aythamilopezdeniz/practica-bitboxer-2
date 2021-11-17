package com.practica.bitboxer2.app.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SupplierJUnitTest {

    private Supplier supplier1 = null;

    private Supplier supplier2 = null;

    @BeforeEach
    void initMethodTest() {
        this.supplier1 = new Supplier(1L, "supplier1", "German");
        this.supplier2 = new Supplier(2L, "supplier2", "Spain");
    }

    @Test
    void testGetIdSupplier() {
        assertTrue(this.supplier1.getId().equals(1L));

        Supplier supplier = new Supplier();
        supplier.setId(1L);
        assertEquals(this.supplier1.getId(), supplier.getId());
        assertNotNull(this.supplier1.getId());
        assertNotEquals(this.supplier1.hashCode(), supplier.hashCode());
    }

    @Test
    void testGetNameSupplier() {
        assertNotNull(this.supplier2.getName());
        assertTrue(this.supplier2.getName().equals("supplier2"));

        Supplier supplier = new Supplier();
        supplier.setName("supplier2");
        assertEquals(this.supplier2.getName(), supplier.getName());
        assertNotEquals(this.supplier2.hashCode(), supplier.hashCode());
    }

    @Test
    void testGetCountrySupplier() {
        assertNotNull(this.supplier1.getCountry());
        assertNotEquals(this.supplier2.getCountry(), this.supplier1.getCountry());

        Supplier supplier = new Supplier();
        supplier.setCountry("Spain");

        assertFalse(this.supplier2.getCountry().equals(this.supplier1.getCountry()));
        assertNotEquals(this.supplier2.hashCode(), supplier.hashCode());
    }

    @Test
    void testToStringSupplier() {
        Supplier supplier = new Supplier();
        supplier.setId(1L);
        supplier.setName("supplier1");
        supplier.setCountry("German");

        assertNotNull(this.supplier1.toString());
        assertTrue(this.supplier1.equals(supplier));
        assertTrue(this.supplier1.toString().equals(supplier.toString()));

        supplier.setId(2L);
        assertNotEquals(this.supplier1.toString(), supplier.toString());
    }
}