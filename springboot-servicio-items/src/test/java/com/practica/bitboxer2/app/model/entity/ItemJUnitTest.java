package com.practica.bitboxer2.app.model.entity;

import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemJUnitTest {

    private Item item1 = null;

    private Item item2 = null;

    @BeforeEach
    void initMethodTest() {
        User user1 = new User(1L, "Francisco", "Carrasco", "Fran", "1234");
        this.item1 = new Item(1L, "asd213", "Alta", 59.95, new Date(), user1);
        User user2 = new User(2L, "Antonia", "Fernández", "Antonia", "1234");
        this.item2 = new Item(2L, "qwr456", "Alta", 79.95, new Date(), user2);
    }

    @Test
    void testIdItem() {
        Item item = new Item();
        item.setId(2L);
        assertNotNull(this.item1.getId(), () -> "El id del item no puede ser nulo.");
        assertNotEquals(item.getId(), this.item1.getId());
    }

    @Test
    void testItemCodeItem() {
        assertNotNull(this.item2.getItemCode(), () -> "El item_code no puede ser nulo.");
        assertFalse(this.item2.getItemCode().equals(this.item1.getItemCode()));
        assertNotEquals("qwe456", this.item2.getItemCode());

        Item item = new Item();
        item.setItemCode("qwr456");
        assertEquals(item.getItemCode(), this.item2.getItemCode());
    }

    @Test
    void testDescriptionItem() {
        Item item = new Item();
        item.setDescription("alta");
        assertNotNull(this.item1.getDescription(), () -> "La descripcion no puede ser nulo.");
        assertFalse(item.getDescription().equals(this.item1.getDescription()));

        item.setDescription("Alta");
        assertEquals(item.getDescription(), this.item1.getDescription());
    }

    @Test
    void testPriceItem() {
        Item item = new Item();
        item.setPrice(79.95);
        assertNotNull(this.item2.getPrice(), () -> "El precio no puede ser nulo.");
        assertTrue(this.item2.getPrice().equals(item.getPrice()));

        item.setPrice(75.95);
        assertNotEquals(item.getPrice(), this.item2.getPrice());
    }

    @Test
    void testStateItem() {
        Item item = new Item();
        item.setState(StateEnum.INACTIVE);

        assertNotNull(this.item1.getState(), () -> "El estado no puede ser nulo.");
        assertFalse(item.getState() == this.item1.getState());
        assertNotEquals(this.item1, this.item2);

        item.setState(StateEnum.ACTIVE);
        assertEquals(item.getState(), this.item1.getState());
    }

    @Test
    void testSuppliersItem() {
        assertNull(this.item2.getSuppliers(), () -> "Contiene suministradores.");
        assertTrue(this.item2.getSuppliers() == null);

        this.item2.addSupplier(new Supplier(1L, "supplier1", "France"));
        assertEquals(1, this.item2.getSuppliers().size());

        List<Supplier> suppliers = new ArrayList<>();
        suppliers.add(new Supplier(1L, "supplier1", "France"));
        assertEquals(suppliers, this.item2.getSuppliers());

        suppliers.add(new Supplier(2L, "supplier2", "Italy"));
        this.item2.setSuppliers(suppliers);

        List<Supplier> newSuppliers = new ArrayList<>();
        suppliers.forEach((el) -> newSuppliers.add(el));
        newSuppliers.add(new Supplier(3L, "supplier3", "Spain"));
        assertNotEquals(newSuppliers, this.item2.getSuppliers());
    }

    @Test
    void testPriceReductionsItem() {
        assertNull(this.item1.getPriceReductions(), () -> "Contiene precios reducidos.");
        assertTrue(this.item1.getPriceReductions() == null);

        List<PriceReduction> priceReductions = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 10, 15);
        Date dateInit1 = calendar.getTime();
        calendar.set(2021, 10, 21);
        Date dateEnd1 = calendar.getTime();
        priceReductions.add(new PriceReduction(1L, 19.95, dateInit1, dateEnd1));
        this.item1.addPriceReductions(new PriceReduction(1L, 19.95, dateInit1, dateEnd1));
        assertEquals(1, this.item1.getPriceReductions().size());
        assertEquals(priceReductions, this.item1.getPriceReductions());


        List<PriceReduction> newPriceReductions= new ArrayList<>();
        calendar.set(2021, 10, 16);
        Date dateInit2 = calendar.getTime();
        calendar.set(2021, 10, 20);
        Date dateEnd2 = calendar.getTime();
        priceReductions.forEach((el) -> newPriceReductions.add(el));
        newPriceReductions.add(new PriceReduction(2L, 25.95, dateInit2, dateEnd2));
        this.item1.setPriceReductions(priceReductions);
        assertNotEquals(newPriceReductions, this.item1.getPriceReductions());
    }

    @Test
    void testCreationDateItem() {
        assertNotNull(this.item2.getCreationDate(), () -> "La fecha de creación no puede ser nula.");
//        assertTrue(0 == new Date().compareTo(this.item2.getCreationDate()));
        assertEquals(this.item1.getCreationDate(), this.item2.getCreationDate());
        assertFalse(this.item1.hashCode() == this.item2.hashCode());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 11, 8);
        this.item2.setCreationDate(calendar.getTime());
        assertNotEquals(this.item1.getCreationDate(), this.item2.getCreationDate());
    }

    @Test
    void testCreatorItem() {
        User user = new User(2L, "Antonia", "Fernández", "Antonia", "1234");
        assertNotNull(this.item1.getCreator(), () -> "El usuario que creó el item no puede ser nulo.");
        assertFalse(user.equals(this.item1.getCreator()));

        this.item1.setCreator(user);
        assertEquals(user, this.item1.getCreator());
    }

    @Test
    void testEqualsItem() {
        User user = new User(2L, "Antonia", "Fernández", "Antonia", "1234");
        Item item = new Item(2L, "qwr456", "Alta", 79.95, new Date(), user);
        assertNotNull(item);
        assertTrue(this.item2.equals(item));
        assertEquals(item.toString(), this.item2.toString());
    }
}