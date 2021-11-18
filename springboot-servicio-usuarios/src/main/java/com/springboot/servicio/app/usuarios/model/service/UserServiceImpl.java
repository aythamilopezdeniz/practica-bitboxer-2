package com.springboot.servicio.app.usuarios.model.service;

import com.springboot.servicio.app.usuarios.model.entity.User;
import com.springboot.servicio.app.usuarios.model.repository.UserRepository;
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
public class UserServiceImpl implements UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<User>> findAll() {
        LOGGER.info("Petición obtener la lista de usuarios");
        List<User> users = userRepository.findAll();
        LOGGER.info("Users, {}", users);
        LOGGER.info("Users findAll completado");
        return CompletableFuture.completedFuture(users);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<User> findById(Long id) {
        LOGGER.info("Petición de búsqueda de un usuario por el id");
        User user = userRepository.findById(id).orElseThrow();
        LOGGER.info("User, {}", user);
        LOGGER.info("User findById completado");
        return CompletableFuture.completedFuture(user);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<User> findByName(String name) {
        LOGGER.info("Petición de búsqueda de un usuario por el nombre");
        User user = userRepository.findByName(name).orElseThrow();
        LOGGER.info("User, {}", user);
        LOGGER.info("User findByName completado");
        return CompletableFuture.completedFuture(user);
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<User>> findByRol(String rol) {
        List<User> users = new ArrayList<>();
        LOGGER.info("Filtrado de usuarios por su rol");
        userRepository.findUserByRol(rol).forEach((user) -> users.add(user.orElseThrow()));
        LOGGER.info("Users, {}", users);
        LOGGER.info("Users findByRol completado");
        return CompletableFuture.completedFuture(users);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<User> save(User user) {
        LOGGER.info("Guardando usuario en BD");
        User userSave = userRepository.save(user);
        LOGGER.info("User {}", userSave);
        LOGGER.info("User save completado");
        return CompletableFuture.completedFuture(userSave);
    }

    @Async
    @Override
    @Transactional
    public CompletableFuture<Void> deleteById(Long id) {
        return CompletableFuture.runAsync(() -> {
            LOGGER.info("Borrando usuario en BD");
            userRepository.deleteById(id);
        });
    }
}