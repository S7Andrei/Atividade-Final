package com.unicesumar.entities;

import java.util.UUID;

public class Product extends Entity {
    private final String name;
    private final double price;
    private String customId;

    public Product(UUID uuid, String name, double price) {
        super(uuid);
        this.name = name;
        this.price = price;
    }

    public Product(String name, double price) {
        super(UUID.randomUUID());
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public String getCustomId() {
        return customId;
    }

    public void setCustomId(String customId) {
        this.customId = customId;
    }

    @Override
    public String toString() {
        return String.format("%s - %.2f", this.name, this.price);
    }
}
