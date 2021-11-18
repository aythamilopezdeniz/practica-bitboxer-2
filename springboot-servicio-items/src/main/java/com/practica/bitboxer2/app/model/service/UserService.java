package com.practica.bitboxer2.app.model.service;

import com.practica.bitboxer2.app.model.entity.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserService {

    public CompletableFuture<List<User>> findAll();

    public CompletableFuture<User> findById(Long id);

    public CompletableFuture<User> findByName(String name);

    public CompletableFuture<List<User>> findByRol(String rol);

    public CompletableFuture<User> save(User user);

    public CompletableFuture<Void> deleteById(Long id);
}