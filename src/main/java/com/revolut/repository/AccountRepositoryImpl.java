package com.revolut.repository;

import com.revolut.model.Account;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AccountRepositoryImpl implements AccountRepository {
	private int nextId = 1;
	private static Map<Long, Account> data = new HashMap<>();

	public long createAccount(double balance) {
		long id = this.nextId++;
		Account account = new Account();
		account.setId(id);
		account.setBalance(balance);
		data.put(id, account);
		return id;
	}

	public boolean deleteAccount(long id) {
		if (data.containsKey(id)) {
			data.remove(id);
			return true;
		}

		return false;
	}

	public Account getAccount(long id) {
		return data.get(id);
	}

	public List<Account> getAllAccounts() {
		return data.keySet().stream().sorted().map((id) -> data.get(id)).collect(Collectors.toList());
	}
}