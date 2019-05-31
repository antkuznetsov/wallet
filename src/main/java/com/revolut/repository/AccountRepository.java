package com.revolut.repository;

import com.revolut.model.Account;

import java.util.List;

/**
 * Обеспечивает доступ к данным об аккаунтах
 */
public interface AccountRepository {
	/**
	 * Создать аккаунт
	 *
	 * @param balance начальный баланс
	 * @return идентификатор созданного аккаунта
	 */
	long createAccount(double balance);

	/**
	 * Получить информацию об аккаунте
	 *
	 * @param id идентификатор аккаунта
	 * @return объект с информацией об аккаунте
	 */
	Account getAccount(long id);

	/**
	 * Удалить аккаунт
	 *
	 * @param id идентификатор аккаунта
	 * @return результат удаления
	 */
	boolean deleteAccount(long id);

	/**
	 * Получить список всех аккаунтов
	 *
	 * @return список существующих аккаунтов
	 */
	List<Account> getAllAccounts();
}