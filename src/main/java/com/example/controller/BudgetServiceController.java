package com.example.controller;

import com.example.exception.BudgetEntityAddFailedException;
import com.example.model.BudgetEntity;
import com.example.service.BudgetEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/service")
public class BudgetServiceController {

    @ExceptionHandler(value = BudgetEntityAddFailedException.class)
    public String handleException(BudgetEntityAddFailedException exception, Model model) {
        model.addAttribute("error", exception.getMessage());
        return "failedToCreateEntity";
    }

    private BudgetEntityService budgetEntityService;

    @Autowired
    public BudgetServiceController(BudgetEntityService budgetEntityService) {
        this.budgetEntityService = budgetEntityService;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("budgetEntity", new BudgetEntity());
        return "addForm";
    }

    @PostMapping("/add")
    public String addToDataBase(@ModelAttribute BudgetEntity budgetEntity) throws BudgetEntityAddFailedException {
        budgetEntityService.saveEntity(budgetEntity);
        return "successfulAdd";
    }

    @GetMapping("/view")
    public String getViewForm() {
        return "viewForm";
    }

    @PostMapping("/view")
    public String viewOption(@RequestParam String type,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                             @RequestParam(required = false) Integer fromAmount,
                             Model model) throws SQLException {

        boolean isTypeOnly = fromDate == null & fromAmount == null;

        List<BudgetEntity> budgetEntities = new ArrayList<>();

        if (isTypeOnly) {
            budgetEntities = budgetEntityService.findAll(type);
        }
        if (fromAmount != null) {
            budgetEntities = budgetEntityService.findAllFromAmount(type, fromAmount);
        } else if (fromDate != null) {
            budgetEntities = budgetEntityService.findAllInDate(type, fromDate, toDate);
        }

        model.addAttribute("budgetEntities", budgetEntities);
        return "result";
    }
}
