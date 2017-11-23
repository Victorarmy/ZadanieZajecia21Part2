package com.example.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


public class BudgetEntity {
    private Long id;
    private String type;
    private String description;
    private int amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public BudgetEntity() {
    }

    public BudgetEntity(String type, String description, int amount, LocalDate date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return  type  + " opis: " + description + ", kwota: " + amount +
                ", data: " + date;
    }
}
