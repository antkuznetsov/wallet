package com.revolut.model;

/**
 * Аккаунт
 */
public class Account {
	private long id;
	private double balance;

	public Account() {
	}

	public Account(double balance) {
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}