package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Rol;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RolRepositoryIntegrationTest {

    @Autowired
    private RolRepository rolRepository;

    @Test
    void testFindById() {
        Optional<Rol> rol = rolRepository.findById(1L);
        assertTrue(rol.isPresent());
        assertEquals(1L, rol.orElseThrow().getId());
    }

    @Test
    void testFindByName() {
        Optional<Rol> rol = rolRepository.findByName("ADMIN");
        assertTrue(rol.isPresent());
        assertEquals("ADMIN", rol.orElseThrow().getName());
    }

    @Test
    void testSaveRol() {
        Rol rol = new Rol(null, "SUPER_ADMIN");
        rol = rolRepository.save(rol);
        assertNotNull(rol);

        Optional<Rol> super_admin = rolRepository.findByName("super_admin");
        assertTrue(super_admin.isPresent());
        assertEquals(3, rolRepository.findAll().size());
        assertEquals("SUPER_ADMIN", super_admin.orElseThrow().getName());
    }
}