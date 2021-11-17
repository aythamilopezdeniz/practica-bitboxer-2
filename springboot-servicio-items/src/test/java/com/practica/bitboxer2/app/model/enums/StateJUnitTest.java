package com.practica.bitboxer2.app.model.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StateJUnitTest {

    private final int NUM_STATE = 2;

    @Test
    public void testNumStates() {
        assertFalse(StateEnum.values().length < 2);
        assertEquals(NUM_STATE, StateEnum.values().length);
    }

    @Test
    void testIdState() {
        assertFalse(1L == StateEnum.INACTIVE.getId());
        assertEquals(1L, StateEnum.ACTIVE.getId());
    }

    @Test
    void testNameState() {
        assertFalse("Inactive" == StateEnum.ACTIVE.getName());
        assertEquals("Inactive", StateEnum.INACTIVE.getName());
    }

    @Test
    void testGetIdState() {
        assertFalse(StateEnum.ACTIVE == StateEnum.getIdState(2L));
        assertEquals(StateEnum.ACTIVE, StateEnum.getIdState(1L));
    }

    @Test
    void testGetNameState() {
        assertFalse(StateEnum.INACTIVE == StateEnum.getNameState("Active"));
        assertEquals(StateEnum.INACTIVE, StateEnum.getNameState("Inactive"));
    }
}