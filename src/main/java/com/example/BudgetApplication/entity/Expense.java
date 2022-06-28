package com.example.BudgetApplication.entity;

import java.sql.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Expense entity inherits from Transaction class. Transactions are distinguished between either expense or income.
 * 
 * @author Benjamin Chang
 *
 */
@Entity
@DiscriminatorValue("expense")
public class Expense extends Transaction {

	public Expense() {
		super();
	}

	public Expense(String category, String description, double amount, Date date) {
		super(category, description, amount, date);
	}
}
