package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceReductionRepository extends JpaRepository<PriceReduction, Long> {

    public List<Optional<PriceReduction>> findByState(StateEnum state);
}