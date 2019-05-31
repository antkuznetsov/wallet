package com.revolut.model;

import java.time.LocalDateTime;

/**
 * Транзакция
 */
public class Transaction {
	private long id;
	private double amount;
	private LocalDateTime time;
	private long sender;
	private long recipient;

	public Transaction() {
	}

	public Transaction(double amount, long sender, long recipient) {
		this.amount = amount;
		this.sender = sender;
		this.recipient = recipient;
	}

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

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public long getSender() {
		return sender;
	}

	public void setSender(long sender) {
		this.sender = sender;
	}

	public long getRecipient() {
		return recipient;
	}

	public void setRecipient(long recipient) {
		this.recipient = recipient;
	}
}
