package com.practica.bitboxer2.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@EnableAsync
@EnableEurekaClient
@SpringBootApplication
//@EntityScan({"com.springboot.servicio.app.usuarios.commons.model.entity",
//        "com.springboot.servicio.app.commons.precios.model.entity",
//        "com.springboot.servicio.app.commons.suministrador.model.entity"})
public class SpringbootServicioItemsApplication implements AsyncConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootServicioItemsApplication.class, args);
    }

    @Override
    @Bean(name = "threadPoolTaskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsynchThread-");
        executor.initialize();
        return executor;
    }
}