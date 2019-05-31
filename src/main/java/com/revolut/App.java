package com.revolut;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.revolut.model.Account;
import com.revolut.model.Transaction;
import com.revolut.services.AccountService;
import com.revolut.services.TransactionService;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static spark.Spark.*;

public class App {
	private static final String API_PATH = "/api/v1/";

	private static final int HTTP_OK = 200;
	private static final int HTTP_BAD_REQUEST = 400;
	private static final int HTTP_NOT_FOUND = 404;

	public static void main(String[] args) {
		AccountService accountService = new AccountService();
		TransactionService transactionService = new TransactionService();

		/*
		 * Добавить аккаунт
		 */
		post(API_PATH + "accounts/", (request, response) -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Account account = mapper.readValue(request.body(), Account.class);
				long id = accountService.createAccount(account);
				response.status(HTTP_OK);
				response.type("application/json");
				return id;
			} catch (JsonParseException jpe) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});

		/*
		 * Удалить аккаунт
		 */
		delete(API_PATH + "accounts/", (request, response) -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Account account = mapper.readValue(request.body(), Account.class);

				if (accountService.deleteAccount(account)) {
					response.status(HTTP_OK);
				} else {
					response.status(HTTP_BAD_REQUEST);
				}

				response.type("application/json");

				return "";
			} catch (NumberFormatException e) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});

		/*
		 * Получить список всех аккаунтов
		 */
		get(API_PATH + "accounts/", (request, response) -> {
			response.status(HTTP_OK);
			response.type("application/json");
			return dataToJson(accountService.getAllAccounts());
		});

		/*
		 * Получить информацию об аккаунте
		 */
		get(API_PATH + "accounts/:id/", (request, response) -> {
			try {
				Account account = accountService.getAccount(Long.valueOf(request.params(":id")));

				if (account == null) {
					response.status(HTTP_NOT_FOUND);
					return "";
				}

				response.status(HTTP_OK);
				response.type("application/json");

				return dataToJson(account);
			} catch (NumberFormatException e) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});

		/*
		 * Добавить транзакцию
		 */
		post(API_PATH + "transactions/", (request, response) -> {
			try {
				ObjectMapper mapper = new ObjectMapper();
				Transaction transaction = mapper.readValue(request.body(), Transaction.class);
				long id = transactionService.createTransaction(transaction);
				if (id > 0) {
					response.status(HTTP_OK);
					response.type("application/json");
					return id;
				} else {
					response.status(HTTP_BAD_REQUEST);
					return "";
				}

			} catch (JsonParseException jpe) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});

		/*
		 * Получить список всех транзакций
		 */
		get(API_PATH + "transactions/", (request, response) -> {
			response.status(HTTP_OK);
			response.type("application/json");
			return dataToJson(transactionService.getAllTransactions());
		});

		/*
		 * Получить информацию о транзакции
		 */
		get(API_PATH + "transactions/:id/", (request, response) -> {
			try {
				Transaction transaction = transactionService.getTransaction(Long.valueOf(request.params(":id")));

				if (transaction == null) {
					response.status(HTTP_NOT_FOUND);
					return "";
				}

				response.status(HTTP_OK);
				response.type("application/json");

				return dataToJson(transaction);
			} catch (NumberFormatException e) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});

		/*
		 * Получить информацию транзакциях аккаунта
		 */
		get(API_PATH + "accounts/:id/history/", (request, response) -> {
			try {
				List<Transaction> history = transactionService.getHistory(Long.valueOf(request.params(":id")));

				if (history == null) {
					response.status(HTTP_BAD_REQUEST);
					return "";
				}

				response.status(HTTP_OK);
				response.type("application/json");
				return dataToJson(transactionService.getHistory(Long.valueOf(request.params(":id"))));
			} catch (NumberFormatException e) {
				response.status(HTTP_BAD_REQUEST);
				return "";
			}
		});
	}

	private static String dataToJson(Object data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, data);
			return sw.toString();
		} catch (IOException e) {
			throw new RuntimeException("IOException from a StringWriter!");
		}
	}
}