package com.sit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sit.model.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Long>{
	
	
	
	List<Budget> findAll();

}