package com.revolut.repository;

import com.revolut.model.Transaction;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionRepositoryImpl implements TransactionRepository {
	private int nextId = 1;
	private static Map<Long, Transaction> data = new HashMap<>();

	public long createTransaction(double amount, long sender, long recipient) {
		long id = this.nextId++;
		Transaction transaction = new Transaction(amount, sender, recipient);
		transaction.setId(id);
		transaction.setTime(LocalDateTime.now());
		data.put(id, transaction);
		return id;
	}

	public List<Transaction> getAllTransactions() {
		return data.keySet().stream().sorted().map((id) -> data.get(id)).collect(Collectors.toList());
	}

	public Transaction getTransaction(long id) {
		return data.get(id);
	}
}