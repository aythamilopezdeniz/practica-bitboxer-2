package com.practica.bitboxer2.app.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RolJUnitTest {

    private Rol rol1 = null;

    private Rol rol2 = null;

    @BeforeEach
    void initMethodTest() {
        this.rol1 = new Rol(1L, "Admin");
        this.rol2 = new Rol(2L, "User");
    }

    @Test
    void testIdRol() {
        Rol rol = new Rol();
        rol.setId(1L);
        assertNotNull(this.rol1.getId());
        assertTrue(this.rol1.getId().equals(rol.getId()));
        assertNotEquals(this.rol1.hashCode(), rol.hashCode());
    }

    @Test
    void testNameRol() {
        Rol rol = new Rol();
        rol.setName("user");
        assertNotNull(this.rol2.getName());
        assertFalse(this.rol2.getName().equals(rol.getName()));

        rol.setId(2L);
        rol.setName("User");
        assertEquals(this.rol2.hashCode(), rol.hashCode());
    }

    @Test
    void testToStringRol() {
        assertFalse(this.rol1.toString().equals(this.rol2.toString()));
        assertNotEquals(this.rol1.hashCode(), this.rol2.hashCode());
    }

    @Test
    void testEqualsRol() {
        assertFalse(this.rol1.equals(this.rol2));
        assertNotEquals(this.rol1, this.rol2);
    }
}