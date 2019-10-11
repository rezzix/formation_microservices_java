package com.formation.microservice.domain;

import java.util.Date;

public class Sale {
	
	private long id;
	
	private double amount;
	private String currency = "Eur";
	private Date saveDate;
	
	private Customer customer;
	 
	
	public Sale(double amount, String currency) {
		this.amount = amount;
		this.currency = currency;
		this.saveDate = new Date();
	}
	
	public Sale() {}
	
	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public double getAmount() {
		return amount;
	}


	public void setAmount(double amount) {
		this.amount = amount;
	}


	public String getCurrency() {
		return currency;
	}


	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public Date getSaveDate() {
		return saveDate;
	}


	public void setSaveDate(Date saveDate) {
		this.saveDate = saveDate;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
