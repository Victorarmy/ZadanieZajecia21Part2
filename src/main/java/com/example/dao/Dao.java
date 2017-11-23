package com.example.dao;

import com.example.model.BudgetEntity;
import com.example.model.EntityType;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface Dao {
    int save(BudgetEntity budgetEntity) throws SQLException;

    List<BudgetEntity> findAll(EntityType type) throws SQLException;

    List<BudgetEntity> findAllFromToDate(LocalDate dateFrom, LocalDate dateTo, EntityType type) throws SQLException;

    List<BudgetEntity> findAllFromAmount(int amount, EntityType type) throws SQLException;

    void closeConnection();
}
