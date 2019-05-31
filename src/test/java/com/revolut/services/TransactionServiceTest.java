package com.revolut.services;

import com.revolut.model.Account;
import com.revolut.model.Transaction;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TransactionServiceTest {
	private AccountService accountService;
	private TransactionService transactionService;

	@Before
	public void setUp() throws Exception {
		accountService = new AccountService();
		transactionService = new TransactionService();
	}

	@Test
	public void createTransaction() {
		long a1 = accountService.createAccount(new Account(500));
		long a2 = accountService.createAccount(new Account(0));

		transactionService.createTransaction(new Transaction(100, a1, a2));

		assertEquals(1, transactionService.getAllTransactions().size());
		assertEquals(100, transactionService.getAllTransactions().get(0).getAmount(), 0.0);
		assertEquals(400, accountService.getAccount(a1).getBalance(), 0.0);
		assertEquals(100, accountService.getAccount(a2).getBalance(), 0.0);
	}

	@Test
	public void createTransactionWithoutMoney() {
		long a1 = accountService.createAccount(new Account(0));
		long a2 = accountService.createAccount(new Account(0));

		long id = transactionService.createTransaction(new Transaction(100, a1, a2));

		assertEquals(-1, id);
		assertEquals(0, transactionService.getAllTransactions().size());
		assertEquals(0, accountService.getAccount(a1).getBalance(), 0.0);
		assertEquals(0, accountService.getAccount(a2).getBalance(), 0.0);
	}

	@Test
	public void getAllTransactions() {
		long a1 = accountService.createAccount(new Account(500));
		long a2 = accountService.createAccount(new Account(0));

		transactionService.createTransaction(new Transaction(70, a1, a2));
		transactionService.createTransaction(new Transaction(30, a1, a2));

		assertEquals(2, transactionService.getAllTransactions().size());
	}

	@Test
	public void getTransaction() {
		long a1 = accountService.createAccount(new Account(100));
		long a2 = accountService.createAccount(new Account(0));

		long id = transactionService.createTransaction(new Transaction(50, a1, a2));

		assertEquals(50, transactionService.getTransaction(id).getAmount(), 0.0);
		assertEquals(a1, transactionService.getTransaction(id).getSender(), 0.0);
		assertEquals(a2, transactionService.getTransaction(id).getRecipient(), 0.0);
	}
}