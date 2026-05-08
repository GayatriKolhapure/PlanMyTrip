package com.sit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sit.dto.BudgetRequestDto;
import com.sit.model.Budget;
import com.sit.service.BudgetService;


@RestController
@RequestMapping("/budget")
public class BudgetController {
	
	 @Autowired
	    private BudgetService budgetService;

	 @PostMapping("/calculate")
	 public Budget calculateBudget(@RequestBody BudgetRequestDto request) {
	     return budgetService.calculateBudget(request.getTripId(), request.getTotalBudget());
	 }
	    
	    @GetMapping("/all")
	    public List<Budget> getAllBudgets() {

	        return budgetService.getAllBudgets();
	    }
	    @GetMapping("/get/{id}")
	    public Budget getBudgetById(@PathVariable Long id) {
	        return budgetService.getBudgetById(id);
	    }
	    @DeleteMapping("/delete/{id}")
	    public String deleteBudget(
	            @PathVariable Long id) {

	        return budgetService.deleteBudget(id);
	    }
}
