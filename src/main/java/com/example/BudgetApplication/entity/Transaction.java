package com.example.BudgetApplication.entity;

import java.sql.Date;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Transaction entity is the parent class of Expense and Income. Each transaction is associated with a single profile.
 * As of the current version the following is supported:
 * Income categories: Job/Gift/Investment
 * Expense categories: Bill/Clothes/Entertainment/Food/Groceries/Travel/Other
 * 
 * @author Benjamin Chang
 * @version 1.0
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="discrimnator")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
	private String type;				// The type of transaction i.e income or expense
	private String category;			// Category of transaction e.g shopping, food
	private String description;			// Additional comment by user to provide additional information on the expense
	private double amount;				// Amount in $
	private Date date;					// Date in the format of YYYY/MM/DD
	
	@ManyToOne
	@JoinColumn(name = "fk_profile_id")
	private Profile profile;
	
	public Transaction(String category, String description, double amount, Date date) {
		super();
		this.category = category;
		this.description = description;
		this.amount = amount;
		this.date = date;
		this.type = this.getClass().getSimpleName();
	}

	public Transaction() {
		super();
	}

	public String getType() {
		return type;
	}
	
	public String setType(String type) {
		return this.type = type;
	}
	
	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", category=" + category + ", description=" + description
				+ ", amount=" + amount + ", date=" + date + ", profile=" + profile + "]";
	}

}
