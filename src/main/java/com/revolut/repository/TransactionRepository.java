package com.revolut.repository;

import com.revolut.model.Transaction;

import java.util.List;

/**
 * Обеспечивает доступ к данным о транзакциях
 */
public interface TransactionRepository {
	/**
	 * Создать транзакцию
	 *
	 * @param amount    размер платежа
	 * @param sender    идентификатор отправителя
	 * @param recipient идентификатор получателя
	 * @return идентификатор созданной транзакции
	 */
	long createTransaction(double amount, long sender, long recipient);

	/**
	 * Получить информацию о транзакции
	 *
	 * @param id идентификатор транзакции
	 * @return объект с информацией о транзакции
	 */
	Transaction getTransaction(long id);

	/**
	 * Получить список всех транзакций
	 *
	 * @return список транзакций
	 */
	List<Transaction> getAllTransactions();
}