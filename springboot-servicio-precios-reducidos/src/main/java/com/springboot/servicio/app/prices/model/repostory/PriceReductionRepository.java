package com.springboot.servicio.app.prices.model.repostory;

import com.springboot.servicio.app.prices.model.entity.PriceReduction;
import com.springboot.servicio.app.prices.model.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceReductionRepository extends JpaRepository<PriceReduction, Long> {

//    @Query(value = "select p from PriceReduction p where lower(p.state) = lower(?1)")
    public List<Optional<PriceReduction>> findByState(StateEnum state);
}