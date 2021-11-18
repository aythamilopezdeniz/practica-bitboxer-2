package com.springboot.servicio.app.commons.precios.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.servicio.app.commons.precios.model.enums.StateEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "PRICE_REDUCTION")
public class PriceReduction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "REDUCTION_PRICE")
    private Double reductionPrice;

    @NotNull
    @Column(name = "START_DATE")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date startDate;

    @NotNull
    @Column(name = "END_DATE")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date endDate;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private StateEnum state;

    public PriceReduction() {
    }

    public PriceReduction(Long id, Double reductionPrice, Date startDate, Date endDate) {
        this.id = id;
        this.reductionPrice = reductionPrice;
        this.startDate = startDate;
        this.endDate = endDate;
        this.state = StateEnum.INACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getReductionPrice() {
        return reductionPrice;
    }

    public void setReductionPrice(Double reductionPrice) {
        this.reductionPrice = reductionPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriceReduction that = (PriceReduction) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(reductionPrice, that.reductionPrice) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reductionPrice, startDate, endDate, state);
    }

    @Override
    public String toString() {
        return "PriceReduction{id=" + id + ", reductionPrice=" + reductionPrice + ", startDate=" + startDate +
                ", endDate=" + endDate + ", state=" + state + '}';
    }
}