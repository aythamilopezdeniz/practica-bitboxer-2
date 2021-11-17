package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Rol;
import com.practica.bitboxer2.app.model.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindById() {
        Optional<User> user = userRepository.findById(1L);
        assertTrue(user.isPresent());
        assertEquals(1L, user.orElseThrow().getId());
    }

    @Test
    void testFindByNameLike() {
        List<Optional<User>> list = userRepository.findByNameLike("Antonia");
        assertFalse(list.isEmpty());
        assertEquals("Antonia", list.get(0).orElseThrow().getName());
        assertEquals("Antonia Del Carmen", list.get(1).orElseThrow().getName());
    }

    @Test
    void testFindByNameThrowException() {
        Optional<User> user = userRepository.findByName("Martín");
        assertThrows(NoSuchElementException.class, user::orElseThrow);
        assertFalse(user.isPresent());
    }

    @Test
    void testFindByLastName() {
        Optional<User> user = userRepository.findByLastName("Carrasco");
        assertTrue(user.isPresent());
        assertEquals("Carrasco", user.orElseThrow().getLastName());
    }

    @Test
    void testFindByUserName() {
        Optional<User> user = userRepository.findByUsername("Fran");
        assertTrue(user.isPresent());
        assertEquals("Fran", user.orElseThrow().getUsername());
    }

    @Test
    void testSaveUser() {
        User user = new User(null, "Rosa", "Santana", "Rosita", "456");
        User userSave = userRepository.save(user);

        assertEquals("Rosa", userSave.getName());
        assertEquals("Rosita", userSave.getUsername());
    }

    @Test
    void testUpdateUser() {
        Optional<User> user = userRepository.findByName("Francisco");
        user.orElseThrow().setName("Antonio");
        user.orElseThrow().setUsername("Toñín");

        User update = userRepository.save(user.orElseThrow());
        assertEquals("Antonio", update.getName());
        assertEquals("Toñín", update.getUsername());
    }

    @Test
    void testDeleteUser() {
        Optional<User> user = userRepository.findByName("Antonia Del Carmen");
        userRepository.delete(user.orElseThrow());
        assertThrows(NoSuchElementException.class, () -> userRepository.findByName("Antonia Del Carmen").orElseThrow());
        assertEquals(2, userRepository.findAll().size());
    }

    @Test
    void testFindUsersByRol() {
        List<Optional<User>> users = userRepository.findUserByRol("User");
        assertFalse(users.isEmpty());
        assertEquals("Francisco", users.get(0).orElseThrow().getName());
        assertEquals("Antonia", users.get(1).orElseThrow().getName());
        assertTrue(users.size() == 3);

        users = userRepository.findUserByRol("admin");
        assertEquals(1, users.size());
    }
}