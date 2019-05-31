package com.revolut.services;

import com.revolut.model.Account;
import com.revolut.model.Transaction;
import com.revolut.repository.AccountRepository;
import com.revolut.repository.AccountRepositoryImpl;
import com.revolut.repository.TransactionRepository;
import com.revolut.repository.TransactionRepositoryImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервисный слой для управления транзакциями
 */
public class TransactionService {
	private AccountRepository accountRepository = new AccountRepositoryImpl();
	private TransactionRepository transactionRepository = new TransactionRepositoryImpl();

	/**
	 * Создать транзакцию
	 *
	 * @param transaction информация о транзакции
	 * @return идентификатор созданной транзакции или -1 в случае ошибки
	 */
	public long createTransaction(Transaction transaction) {
		if (validate(transaction)) {
			if (addOutgoingPayment(transaction.getSender(), transaction.getAmount())) {
				addIncomingPayment(transaction.getRecipient(), transaction.getAmount());

				return transactionRepository
						.createTransaction(transaction.getAmount(), transaction.getSender(), transaction.getRecipient());
			}
		}
		return -1;
	}

	/**
	 * Получить список всех транзакций
	 *
	 * @return список транзакций
	 */
	public List<Transaction> getAllTransactions() {
		return transactionRepository.getAllTransactions();
	}

	/**
	 * Получить информацию о транзакции
	 *
	 * @param id идентификатор транзакции
	 * @return объект с информацией о транзакции
	 */
	public Transaction getTransaction(long id) {
		return transactionRepository.getTransaction(id);
	}

	/**
	 * Получить все транзакции аккаунта
	 *
	 * @param accountId ижентификатор аккаунта
	 * @return список транзакций
	 */
	public List<Transaction> getHistory(long accountId) {
		if (accountRepository.getAccount(accountId) == null) {
			return null;
		}

		return transactionRepository.getAllTransactions().stream()
				.filter(t -> t.getRecipient() == accountId || t.getSender() == accountId)
				.collect(Collectors.toList());
	}

	private boolean validate(Transaction transaction) {
		if (transaction == null || transaction.getAmount() <= 0 && transaction.getSender() <= 0
				|| transaction.getRecipient() <= 0) {
			return false;
		}

		return accountRepository.getAccount(transaction.getSender()) != null
				&& accountRepository.getAccount(transaction.getRecipient()) != null;
	}

	private boolean addOutgoingPayment(long accountId, double amount) {
		Account account = accountRepository.getAccount(accountId);

		if (account.getBalance() - amount > 0) {
			account.setBalance(account.getBalance() - amount);
			return true;
		}

		return false;
	}

	private void addIncomingPayment(long accountId, double amount) {
		Account account = accountRepository.getAccount(accountId);
		account.setBalance(account.getBalance() + amount);
	}
}