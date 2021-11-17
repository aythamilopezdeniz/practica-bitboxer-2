package com.practica.bitboxer2.app.model.repository;

import com.practica.bitboxer2.app.model.entity.PriceReduction;
import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PriceReductionRepositoryIntegrationTest {

    @Autowired
    private PriceReductionRepository priceReductionRepository;

    @Test
    void testFindAll() {
        List<PriceReduction> priceReductions = priceReductionRepository.findAll();
        assertFalse(priceReductions.isEmpty());
        assertEquals(3, priceReductions.size());
    }

    @Test
    void testFindByState() {
        List<Optional<PriceReduction>> priceReductions = priceReductionRepository.findByState(StateEnum.INACTIVE);
        assertFalse(priceReductions.isEmpty());
        assertEquals(2, priceReductions.size());

        priceReductions = priceReductionRepository.findByState(StateEnum.ACTIVE);
        assertFalse(priceReductions.isEmpty());
        assertEquals(1, priceReductions.size());
        assertEquals("Active", priceReductions.get(0).orElseThrow().getState().getName());
    }

    @Test
    void testFindByStartDate() throws ParseException {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        String actual = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar.setTime(format.parse(actual));

//        System.out.println("actual " + actual);
        List<Optional<PriceReduction>> priceReductionsActive = priceReductionRepository.findByState(StateEnum.ACTIVE);
        assertFalse(priceReductionsActive.get(0).orElseThrow().getStartDate().equals(actual));
//        System.out.println("start date " + priceReductionsActive.get(0).orElseThrow().getStartDate());
//        System.out.println("calendar "+calendar.getTime());
        assertEquals(-1, priceReductionsActive.get(0).orElseThrow().getStartDate().compareTo(calendar.getTime()));

        priceReductionsActive.get(0).orElseThrow().setState(StateEnum.INACTIVE);
        PriceReduction priceReduction = priceReductionRepository.save(priceReductionsActive.get(0).orElseThrow());
        assertNotNull(priceReduction.getReductionPrice());

        List<Optional<PriceReduction>> priceReductionsInactive = priceReductionRepository.findByState(StateEnum.INACTIVE);
        assertFalse(priceReductionsInactive.isEmpty());
        assertEquals(3, priceReductionsInactive.size());
    }

    @Test
    void testSavePriceReduction() throws ParseException {
        GregorianCalendar calendar1 = new GregorianCalendar();
        GregorianCalendar calendar2 = new GregorianCalendar();
        calendar1.setTime(new Date());
        String actual1 = calendar1.get(Calendar.YEAR) + "-" + (calendar1.get(Calendar.MONTH) + 1) + "-" + calendar1.get(Calendar.DATE);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        calendar1.setTime(format.parse(actual1));
        String actual2 = calendar2.get(Calendar.YEAR) + "-" + (calendar2.get(Calendar.MONTH) + 1) + "-12";
        calendar2.setTime(format.parse(actual2));

        PriceReduction priceReduction = new PriceReduction(null, 7.95, calendar1.getTime(), calendar2.getTime());
        priceReductionRepository.save(priceReduction);
        Optional<PriceReduction> priceReductionSave = priceReductionRepository.findById(4L);
        assertTrue(priceReductionSave.isPresent());
        assertEquals(7.95, priceReductionSave.orElseThrow().getReductionPrice());
    }

    @Test
    void testUpdatePriceReduction() {
        Optional<PriceReduction> priceReduction = priceReductionRepository.findById(3L);
        priceReduction.orElseThrow().setReductionPrice(100.00);
        priceReduction.orElseThrow().setState(StateEnum.INACTIVE);

        priceReductionRepository.save(priceReduction.orElseThrow());
        assertEquals(100.00, priceReductionRepository.findById(3L).orElseThrow().getReductionPrice());
        assertEquals("Inactive", priceReductionRepository.findById(3L).orElseThrow().getState().getName());
    }

    @Test
    void testDeletePriceReduction() {
        Optional<PriceReduction> priceReduction = priceReductionRepository.findByState(StateEnum.ACTIVE).get(0);
        priceReductionRepository.delete(priceReduction.orElseThrow());
        assertThrows(DataIntegrityViolationException.class, () -> {
                   priceReductionRepository.findByState(StateEnum.ACTIVE).get(0).orElseThrow();
        });
    }
}