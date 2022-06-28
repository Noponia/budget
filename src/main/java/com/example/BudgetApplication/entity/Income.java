package com.example.BudgetApplication.entity;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Income entity inherits from Transaction class. Transactions are distinguished between either expense or income.
 * 
 * @author Benjamin Chang
 *
 */
@Entity
@DiscriminatorValue("income")
public class Income extends Transaction {

	public Income() {
		super();
	}

	public Income(String category, String description, double amount, Date date) {
		super(category, description, amount, date);
	}
	
	
}
