package com.example.dao;

import com.example.model.BudgetEntity;
import com.example.model.EntityType;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OperationsDao implements Dao {
    private static final String SAVE_QUERY = "INSERT INTO home_budget(type, description, amount, date) values(?, ?, ?, ?)";
    private static final String GET_ALL_QUERY = "SELECT * FROM home_budget WHERE type = ?";
    private static final String GET_ALL_FROM_TO_DATE_QUERY = "SELECT * FROM home_budget WHERE type = ? AND date > ? AND date < ?";
    private static final String GET_ALL_FROM_AMOUNT_QUERY = "SELECT * FROM home_budget WHERE type = ? AND amount > ?";
    private Connection connection;

    public OperationsDao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zadanie?characterEncoding=utf8",
                    "root", "admin");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int save(BudgetEntity budgetEntity) throws SQLException {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY);
            preparedStatement.setString(1, budgetEntity.getType());
            preparedStatement.setString(2, budgetEntity.getDescription());
            preparedStatement.setInt(3, budgetEntity.getAmount());
            preparedStatement.setDate(4, getSqlDate(budgetEntity.getDate()));
            return preparedStatement.executeUpdate();
    }

    @Override
    public List<BudgetEntity> findAll(EntityType type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUERY);
        preparedStatement.setString(1, type.getName());
        ResultSet resultSet = preparedStatement.executeQuery();
        return returnResultList(resultSet);
    }

    @Override
    public List<BudgetEntity> findAllFromToDate(LocalDate dateFrom, LocalDate dateTo, EntityType type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_TO_DATE_QUERY);
        preparedStatement.setString(1, type.getName());
        preparedStatement.setDate(2, getSqlDate(dateFrom));
        preparedStatement.setDate(3, getSqlDate(dateTo));
        ResultSet resultSet = preparedStatement.executeQuery();
        return returnResultList(resultSet);
    }

    @Override
    public List<BudgetEntity> findAllFromAmount(int amount, EntityType type) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_FROM_AMOUNT_QUERY);
        preparedStatement.setString(1, type.getName());
        preparedStatement.setInt(2, amount);
        ResultSet resultSet = preparedStatement.executeQuery();
        return returnResultList(resultSet);
    }

    private List<BudgetEntity> returnResultList(ResultSet resultSet) throws SQLException {
        List<BudgetEntity> result = new ArrayList<>();
        while (resultSet.next()) {
            Date date = resultSet.getDate("date");
            result.add(new BudgetEntity(resultSet.getString("type"), resultSet.getString("description"),
                    resultSet.getInt("amount"), date.toLocalDate()));
        }
        return result;
    }

    private Date getSqlDate(LocalDate date) {
        return Date.valueOf(date);
    }

    @Override
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
