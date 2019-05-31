package com.revolut.services;

import com.revolut.model.Account;
import com.revolut.repository.AccountRepository;
import com.revolut.repository.AccountRepositoryImpl;

import java.util.List;

/**
 * Сервисный слой для управления аккаунтами
 */
public class AccountService {
	private AccountRepository accountRepository = new AccountRepositoryImpl();

	/**
	 * Создать аккаунт
	 *
	 * @param account информация об аккаунте
	 * @return идентификатор созданного аккаунта или -1 в случае ошибки
	 */
	public long createAccount(Account account) {
		if (validate(account)) {
			return accountRepository.createAccount(account.getBalance());
		}
		return -1;
	}

	/**
	 * Удалить аккаунт
	 *
	 * @param account информация об аккаунте
	 * @return результат удаления
	 */
	public boolean deleteAccount(Account account) {
		return accountRepository.deleteAccount(account.getId());
	}

	/**
	 * Получить список всех аккаунтов
	 *
	 * @return список существующих аккаунтов
	 */
	public List<Account> getAllAccounts() {
		return accountRepository.getAllAccounts();
	}

	/**
	 * Получить информацию об аккаунте
	 *
	 * @param id идентификатор аккаунта
	 * @return объект с информацией об аккаунте
	 */
	public Account getAccount(long id) {
		return accountRepository.getAccount(id);
	}

	private boolean validate(Account account) {
		return account != null && !(account.getBalance() < 0);
	}
}