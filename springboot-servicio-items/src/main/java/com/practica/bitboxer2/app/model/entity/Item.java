package com.practica.bitboxer2.app.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ITEMS")
public class Item implements Serializable {

    private static final long serialVersionUID = -3596148860426224321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotEmpty
    @Column(name = "ITEM_CODE", unique = true)
    private String itemCode;

    @NotNull
    @NotEmpty
    @Column(name = "DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "PRICE")
    private Double price;

    @Column(name = "STATE")
    @Enumerated(value = EnumType.STRING)
    private StateEnum state;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ITEM_SUPPLIERS",
            joinColumns = @JoinColumn(name = "ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "SUPPLIER_ID"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"ITEM_ID", "SUPPLIER_ID"})})
    private List<Supplier> suppliers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ITEM_PRICE_REDUCTIONS",
            joinColumns = @JoinColumn(name = "ITEM_ID"),
            inverseJoinColumns = @JoinColumn(name = "PRICE_REDUCTION_ID"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"ITEM_ID", "PRICE_REDUCTION_ID"})})
    private List<PriceReduction> priceReductions;

    @NotNull
    @Column(name = "CREATION_DATE")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date creationDate;

    @NotNull
    @OneToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "id")
    private User creator;

    public Item() {
    }

    public Item(Long id, String itemCode, String description, Double price, Date creationDate, User creator) {
        this.id = id;
        this.itemCode = itemCode;
        this.description = description;
        this.price = price;
        this.state = StateEnum.ACTIVE;
        this.creationDate = creationDate;
        this.creator = creator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public void addSupplier(Supplier supplier) {
        if (supplier != null) {
            if (this.suppliers == null) this.suppliers = new ArrayList<>();
            this.suppliers.add(supplier);
        }
    }

    public List<PriceReduction> getPriceReductions() {
        return priceReductions;
    }

    public void setPriceReductions(List<PriceReduction> priceReductions) {
        this.priceReductions = priceReductions;
    }

    public void addPriceReductions(PriceReduction priceReduction) {
        if (priceReduction != null) {
            if (this.priceReductions == null) this.priceReductions = new ArrayList<>();
            this.priceReductions.add(priceReduction);
        }
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(itemCode, item.itemCode) &&
                Objects.equals(description, item.description) &&
                Objects.equals(price, item.price) &&
                state == item.state &&
                Objects.equals(suppliers, item.suppliers) &&
                Objects.equals(priceReductions, item.priceReductions) &&
                Objects.equals(creationDate, item.creationDate) &&
                Objects.equals(creator, item.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemCode, description, price, state, suppliers, priceReductions, creationDate, creator);
    }

    @Override
    public String toString() {
        String item = "Item{id=" + id + ", itemCode='" + itemCode + '\'' + ", description='" + description + '\'' +
                ", price=" + price + ", state=" + state + ", creationDate=" + creationDate + ", creator=" + creator;
        if (this.suppliers != null && this.priceReductions != null) {
            return item.concat(", suppliers=" + suppliers + ", priceReductions=" + priceReductions + '}');
        } else if (this.suppliers != null && this.priceReductions == null) {
            return item.concat(", suppliers=" + suppliers + '}');
        } else if (this.suppliers == null && this.priceReductions != null) {
            return item.concat(", priceReductions=" + priceReductions + '}');
        }
        return item.concat("}");
    }
}