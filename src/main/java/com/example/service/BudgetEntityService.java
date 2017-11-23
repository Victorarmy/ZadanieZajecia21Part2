package com.example.service;

import com.example.dao.Dao;
import com.example.exception.BudgetEntityAddFailedException;
import com.example.model.BudgetEntity;
import com.example.model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class BudgetEntityService {

    private Dao dao;

    @Autowired
    public BudgetEntityService(Dao dao) {
        this.dao = dao;
    }

    public List<BudgetEntity> findAll(String type) throws SQLException {
        return dao.findAll(EntityType.getByName(type));

    }

    public List<BudgetEntity> findAllFromAmount(String type, int fromAmount) throws SQLException {
            return dao.findAllFromAmount(fromAmount, EntityType.getByName(type));
    }

    public List<BudgetEntity> findAllInDate(String type, LocalDate fromDate, LocalDate toDate) throws SQLException {
            return dao.findAllFromToDate(fromDate, toDate, EntityType.getByName(type));
    }

    public void saveEntity(BudgetEntity budgetEntity) throws BudgetEntityAddFailedException {
        try {
            int result = dao.save(budgetEntity);
            if (result < 1) {
                throw new BudgetEntityAddFailedException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
