package com.revolut.services;

import com.revolut.model.Account;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccountServiceTest {
	private AccountService service;

	@Before
	public void setUp() throws Exception {
		service = new AccountService();
	}

	@Test
	public void createAccount() {
		service.createAccount(new Account(0));
		service.createAccount(new Account(100));
		assertEquals(2, service.getAllAccounts().size());
		assertEquals(0, service.getAllAccounts().get(0).getBalance(), 0.0);
		assertEquals(100, service.getAllAccounts().get(1).getBalance(), 0.0);
	}

	@Test
	public void createAccountWithNegativeBalance() {
		long id = service.createAccount(new Account(-100));
		assertEquals(-1, id);
	}

	@Test
	public void getAllAccounts() {
		service.createAccount(new Account(0));
		service.createAccount(new Account(50));
		service.createAccount(new Account(100));
		assertEquals(3, service.getAllAccounts().size());
	}

	@Test
	public void getAccount() {
		long id = service.createAccount(new Account(100));
		assertEquals(1, service.getAllAccounts().size());
		assertEquals(100, service.getAccount(id).getBalance(), 0.0);
	}
}