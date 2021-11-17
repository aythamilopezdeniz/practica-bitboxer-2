package com.practica.bitboxer2.app.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserJUnitTest {

    private User user1 = null;

    private User user2 = null;

    @BeforeEach
    void initMethodTest() {
        this.user1 = new User();
        this.user2 = new User(1L, "Francisco", "Carrasco", "Fran", "1234");
    }

    @Test
    void testGetIdUser() {
        assertEquals(this.user1.getId(), null);
        assertNotNull(this.user2.getId(), () -> "El id de usuario no puede ser nulo.");
        assertTrue(this.user2.getId().equals(1L));

        this.user1.setId(1L);
        assertEquals(this.user1.getId(), this.user2.getId());
    }

    @Test
    void testGetNameUser() {
        assertEquals(this.user1.getName(), null);
        assertNotNull(this.user2.getName(), () -> "El nombre de usuario no puede ser nulo.");
        assertTrue(this.user2.getName().equals("Francisco"));

        this.user1.setName("Antonia");
        assertNotEquals(this.user1.getName(), this.user2.getName());
    }

    @Test
    void testGetLastNameUser() {
        assertEquals(this.user1.getLastName(), null);
        assertNotNull(this.user2.getLastName(), () -> "El apellido de usuario no puede ser nulo.");
        assertTrue(this.user2.getLastName().equals("Carrasco"));

        this.user1.setLastName("Fernández");
        assertNotEquals(this.user1.getLastName(), this.user2.getLastName());
    }

    @Test
    void testGetUserNameUser() {
        assertEquals(this.user1.getUsername(), null);
        assertNotNull(this.user2.getUsername(), () -> "El nombre de usuario no puede ser nulo.");
        assertTrue(this.user2.getUsername().equals("Fran"));

        this.user1.setUsername("Fran");
        assertEquals(this.user1.getUsername(), this.user2.getUsername());
    }

    @Test
    void testGetPasswordUser() {
        assertEquals(this.user1.getPassword(), null);
        assertNotNull(this.user2.getPassword(), () -> "La password del usuario no puede ser nula y/ó vacía.");
        assertTrue(this.user2.getPassword().equals("1234"));

        this.user1.setPassword("5678");
        assertNotEquals(this.user1.getPassword(), this.user2.getPassword());
    }

    @Test
    void testGetRolesUser() {
        Rol rol1 = new Rol(1L, "Admin");
        assertEquals(this.user1.getRoles(), null);
        this.user2.addRoles(rol1);
        assertNotNull(this.user2.getRoles(), () -> "El usuario no tiene roles asignados.");

        List<Rol> roles = new ArrayList<Rol>();
        roles.add(rol1);
        roles.add(new Rol(2L, "User"));
        this.user1.setRoles(roles);
        assertNotEquals(this.user1.getRoles(), this.user2.getRoles());
    }

    @Test
    void testEqualsUser() {
        User user3 = new User(3L, "Francisco", "Fernandez", "Fran", "1235");
        assertTrue(this.user1.equals(new User()));
        assertFalse(this.user2.equals(user3));
    }

    @Test
    void testHashCodeUser() {
        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(1L, "Admin"));

        User user3 = new User(1L, "Francisco", "Carrasco", "Fran", "1234");
        User user4 = new User(1L, "Francisco", "Carrasco", "Fran", "1234");
        user4.setRoles(roles);

        assertTrue(this.user2.hashCode() == user3.hashCode());
        assertNotEquals(this.user2.hashCode(), user4.hashCode());
    }

    @Test
    void testToString() {
        User user3 = new User(1L, "Francisco", "Carrasco", "Fran", "1234");
        assertTrue(this.user2.toString().equals(user3.toString()));

        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(1L, "Admin"));
        user3.setRoles(roles);

        assertNotEquals(this.user2.toString(), user3.toString());
    }
}