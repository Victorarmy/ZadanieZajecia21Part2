package com.example.model;

import java.util.NoSuchElementException;

public enum EntityType {

    EXPENSE("Wydatek"),
    INCOME("Przychód");
    private String name;

    EntityType(String name) {
        this.name = name;
    }

    public static EntityType getByName(String type) {
        for (EntityType entityType : EntityType.values()) {
            if (entityType.getName().equals(type)) {
                return entityType;
            }
        }
        throw new NoSuchElementException();
    }

    public String getName() {
        return name;
    }
}
