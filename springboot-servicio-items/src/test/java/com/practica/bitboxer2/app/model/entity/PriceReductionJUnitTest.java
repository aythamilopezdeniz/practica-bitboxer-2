package com.practica.bitboxer2.app.model.entity;

import com.practica.bitboxer2.app.model.enums.StateEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class PriceReductionJUnitTest {

    private PriceReduction priceReduction1 = null;

    private PriceReduction priceReduction2 = null;

    @BeforeEach
    void initMethodTest() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2021, 10, 15);
        Date dateInit1 = calendar.getTime();
        calendar.set(2021, 10, 16);
        Date dateInit2 = calendar.getTime();
        calendar.set(2021, 10, 21);
        Date dateEnd1 = calendar.getTime();
        calendar.set(2021, 10, 20);
        Date dateEnd2 = calendar.getTime();
        this.priceReduction1 = new PriceReduction(1L, 12.95, dateInit1, dateEnd1);
        this.priceReduction2 = new PriceReduction(2L, 15.95, dateInit2, dateEnd2);
    }

    @Test
    void testIdPriceReduction() {
        assertNotNull(this.priceReduction1.getId(), () -> "El id del precio reducido no puede ser nulo.");
        assertFalse(this.priceReduction1.getId().equals(this.priceReduction2.getId()));
        assertEquals(1L, this.priceReduction1.getId());

        PriceReduction priceReduction = new PriceReduction();
        priceReduction.setId(1L);
        assertEquals(this.priceReduction1.getId(), priceReduction.getId());
    }

    @Test
    void testDiscountPriceReduction() {
        PriceReduction priceReduction = new PriceReduction();
        priceReduction.setReductionPrice(15.95);
        assertNotNull(this.priceReduction2.getReductionPrice(), () -> "El precio de descuento no puede ser nulo.");
        assertTrue(this.priceReduction2.getReductionPrice().equals(priceReduction.getReductionPrice()));
        assertNotEquals(this.priceReduction2.hashCode(), priceReduction.hashCode());
        assertEquals(15.95, this.priceReduction2.getReductionPrice());
    }

    @Test
    void testStartDatePriceReduction() {
        assertNotNull(this.priceReduction1.getStartDate(), () -> "La fecha de comienzo no puede ser nula.");
        assertFalse(this.priceReduction1.getStartDate().equals("15/10/2021"));
        assertTrue(-1 == this.priceReduction1.getStartDate().compareTo(this.priceReduction1.getEndDate()));

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(2021, 10, 18);
        this.priceReduction1.setStartDate(calendar.getTime());
        String date = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE);
        assertEquals("2021/10/18", date);
        assertTrue(calendar.getTime().toString().equals(this.priceReduction1.getStartDate().toString()));
    }

    @Test
    void testEndDatePriceReduction() {
        assertNotNull(this.priceReduction2.getEndDate(), () -> "La fecha de fin no puede ser nula.");
        assertFalse(this.priceReduction2.getEndDate().equals("2021/10/21"));
        assertTrue(1 == this.priceReduction2.getEndDate().compareTo(this.priceReduction2.getStartDate()));

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(2021, 10, 30);
        this.priceReduction2.setEndDate(calendar.getTime());
        String date = calendar.get(Calendar.YEAR) + "/" + calendar.get(Calendar.MONTH) + "/" + calendar.get(Calendar.DATE);
        assertEquals("2021/10/30", date);
        assertTrue(calendar.getTime().toString().equals(this.priceReduction2.getEndDate().toString()));
    }

    @Test
    void testStatePriceReduction() {
        assertNotNull(this.priceReduction1.getState(), () -> "El estado del precio reducido no puede ser nulo.");
        assertTrue(StateEnum.INACTIVE == this.priceReduction1.getState());
        assertEquals(this.priceReduction1.getState(), this.priceReduction2.getState());

        this.priceReduction1.setState(StateEnum.ACTIVE);
        assertNotEquals(this.priceReduction1.getState(), this.priceReduction2.getState());
    }

    @Test
    void testToString() {
        assertFalse(this.priceReduction1.equals(this.priceReduction2));
        assertNotEquals(this.priceReduction1.toString(), this.priceReduction2.toString());

        GregorianCalendar dateInit1 = new GregorianCalendar();
        GregorianCalendar dateEnd1 = new GregorianCalendar();
        dateInit1.set(2021, 10, 15);
        dateEnd1.set(2021, 10, 21);
        PriceReduction priceReduction = new PriceReduction(1L, 12.95, dateInit1.getTime(), dateEnd1.getTime());
        assertEquals(this.priceReduction1.toString(), priceReduction.toString());
    }
}