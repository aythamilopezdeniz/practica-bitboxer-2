package com.practica.bitboxer2.app.controller;

import com.practica.bitboxer2.app.model.entity.User;
import com.practica.bitboxer2.app.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        LOGGER.info("Obteniendo lista de users");
        List<User> users = null;
        try {
            CompletableFuture<List<User>> future = userService.findAll();
            users = future.whenCompleteAsync((r, e) -> System.out.println("Result findAll User Async: " + r), executor).get();
            if (users.isEmpty()) {
                LOGGER.error("Users no encontrados");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("Users obtenidos");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo lista de users - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo lista de users - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id) {
        LOGGER.info("Obteniendo user por Id");
        User user = null;
        try {
            CompletableFuture<User> future = userService.findById(id);
            user = future.whenCompleteAsync((r, e) -> System.out.println("Result findById User Async: " + r), executor).get();
            if (user == null) {
                LOGGER.error("User no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("User obtenido");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo un user - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo un user - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping(value = "/rol/{rol}")
    public ResponseEntity<?> findByRol(@PathVariable(value = "rol") String rol) {
        LOGGER.info("Obteniendo lista de users por Rol");
        List<User> users = null;
        try {
            CompletableFuture<List<User>> future = userService.findByRol(rol);
            users = future.whenCompleteAsync((r, e) -> System.out.println("Result findById User Async: " + r), executor).get();
            if (users == null) {
                LOGGER.error("User no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            LOGGER.info("User obtenido");
        } catch (InterruptedException e) {
            LOGGER.error("Error obteniendo un user - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error obteniendo un user - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {
        LOGGER.info("Guardando un user en BD");
        User userSave = null;
        try {
            CompletableFuture<User> future = userService.save(user);
            userSave = future.whenCompleteAsync((r, e) -> System.out.println("Result save User Async: " + r), executor).get();
            if (user == null) {
                LOGGER.error("User no guardado");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
            LOGGER.info("User guardado");
        } catch (InterruptedException e) {
            LOGGER.error("Error guardando un user - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error guardando un user - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id, @RequestBody User user) {
        LOGGER.info("Actualizando un user en BD");
        User newUserUpdate = null;
        try {
            newUserUpdate = userService.findById(id).thenComposeAsync(userFound -> {
                if (userFound == null) {
                    LOGGER.error("User no actualizado");
                    throw new RuntimeException("User no encontrado");
                }
                if (user.getName() != null)
                   userFound.setName(user.getName());
                if (user.getLastName() != null)
                   userFound.setLastName(user.getLastName());
                if (user.getUsername() != null)
                    userFound.setUsername(user.getUsername());
                if (userFound.getPassword() != null)
                    userFound.setPassword(user.getPassword());
                userFound.setRoles(user.getRoles());
                return userService.save(userFound);
            }).thenApply((userUpdate) -> {
                LOGGER.info("User actualizado");
                return userUpdate;
            }).exceptionally((throwable) -> {
                LOGGER.error("User no actualizado" + throwable.getStackTrace());
                throw new RuntimeException(throwable);
            }).get();
        } catch (InterruptedException e) {
            LOGGER.error("Error actualizando un user - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error actualizando un user - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserUpdate);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        LOGGER.info("Eliminando un user en BD");
        try {
            userService.deleteById(id).get();
            LOGGER.info("User eliminado");
        } catch (InterruptedException e) {
            LOGGER.error("Error eliminando un user - InterruptedException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (ExecutionException e) {
            LOGGER.error("Error eliminando un user - ExecutionException");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}