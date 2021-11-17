package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.Item;
import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.entity.Supplier;
import com.practica.bitboxer2.app.model.entity.User;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ItemRepositoryIntegrationTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindAll() {
        List<Item> itemList = itemRepository.findAll();
        assertFalse(itemList.isEmpty());
        assertEquals(4, itemList.size());
    }

    @Test
    void testFindByItemCode() {
        Optional<Item> item = itemRepository.findByItemCode("aSd213");
        assertTrue(item.isPresent());
        assertEquals("asd213", item.orElseThrow().getItemCode());
    }

    @Test
    void testFindByState() {
        List<Optional<Item>> listItems = itemRepository.findByState(StateEnum.ACTIVE);
        assertFalse(listItems.isEmpty());
        assertEquals(4, listItems.size());

        Item item = listItems.get(0).orElseThrow();
        item.setState(StateEnum.INACTIVE);
        itemRepository.save(item);

        listItems = itemRepository.findByState(StateEnum.ACTIVE);
        assertFalse(listItems.isEmpty());
        assertEquals(3, listItems.size());
    }

    @Test
    void testFindBySuppliers() {
        List<Optional<Supplier>> suppliers = itemRepository.findBySuppliers(1L);
        assertFalse(suppliers.isEmpty());
        assertEquals(3, suppliers.size());

        suppliers = itemRepository.findBySuppliers(5L);
        assertTrue(suppliers.isEmpty());
        assertEquals(0, suppliers.size());
    }

    @Test
    void testFindByPriceReductions() {
        List<Optional<PriceReduction>> priceReductions = itemRepository.findByPriceReductions(1L);
        assertFalse(priceReductions.isEmpty());
        assertEquals(2, priceReductions.size());

        priceReductions = itemRepository.findByPriceReductions(2L);
        assertTrue(priceReductions.isEmpty());
        assertEquals(0, priceReductions.size());
    }

    @Test
    void testSaveItem() throws ParseException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String actual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(format.parse(actual));

        Optional<User> user = userRepository.findById(1L);
        Item item = new Item(5L, "ybk160", "Alta", 80.95, calendar.getTime(), user.orElseThrow());

        item = itemRepository.save(item);
        assertNotNull(item);
        Optional<Item> itemSave = itemRepository.findByItemCode("ybK160");
        assertTrue(itemSave.isPresent());
        assertEquals("ybk160", itemSave.orElseThrow().getItemCode());
    }

    @Test
    void testUpdateItem() {
        Optional<Item> item = itemRepository.findById(1L);
        item.orElseThrow().setDescription("Nueva descripción");
        item.orElseThrow().setState(StateEnum.INACTIVE);

        Item itemSave = itemRepository.save(item.orElseThrow());
        assertNotNull(itemSave);
        assertEquals("Nueva descripción", itemSave.getDescription());
        assertEquals(StateEnum.INACTIVE, itemSave.getState());
    }

    @Test
    void testDeleteItem() {
        Optional<Item> item = itemRepository.findById(4L);
        itemRepository.delete(item.orElseThrow());
        assertThrows(NoSuchElementException.class, () -> itemRepository.findById(4L).orElseThrow());
//        assertEquals(3, itemRepository.findAll().size());
    }
}