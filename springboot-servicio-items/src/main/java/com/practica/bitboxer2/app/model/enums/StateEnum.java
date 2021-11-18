package com.practica.bitboxer2.app.model.enums;

public enum StateEnum {

    ACTIVE(1L, "Active"), INACTIVE(2L, "Inactive");

    private Long id;

    private String name;

    private StateEnum(Long id, String state) {
        this.id = id;
        this.name = state;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static StateEnum getIdState(Long id) {
        for (StateEnum state : values()) {
            if (state.id.equals(id))
                return state;
        }
        return null;
    }

    public static StateEnum getNameState(String name) {
        for (StateEnum state : values()) {
            if (state.name.equals(name))
                return state;
        }
        return null;
    }
}