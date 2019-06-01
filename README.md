# Wallet
RESTful API (including data model and the backing implementation)
for money transfers between accounts.

To run the application:

`mvn compile && mvn exec:java`

## Accounts

#### Account list
`GET /api/v1/accounts/`

Retrieve a list of all accounts.

Example request:

`curl http://localhost:4567/api/v1/accounts/`

Example response:

```json
[{
  "id" : 1,
  "balance" : 500.0
}, {
  "id" : 2,
  "balance" : 100.0
}]
```

Response JSON Object:
* id (long) — the ID of the account
* balance (double) — the balance of the account


#### Account details
`GET /api/v2/accounts/(long: id)/`

Retrieve details of a single account.

Response JSON Object:

```json
{
  "id" : 1,
  "balance" : 500.0
}
```

Status Codes:	
* 200 OK – no error
* 404 Not Found – There is no Account with this ID

#### Account history
`GET /api/v2/accounts/(long: id)/history/`

Retrieve a list of all transaction for single account.

#### Create account
`POST /api/v2/accounts/`

Create new account.

Query Parameters:
* balance (double) — starting balance (optional)

Status Codes:	
* 200 OK – no error
* 400 Bad Request – something wrong

#### Delete account
`DELETE /api/v2/accounts/(long: id)/`

Status Codes:	
* 200 OK – no error
* 400 Bad Request – there is no Account with this ID

## Transactions

#### Transaction list
`GET /api/v1/transactions/`

Retrieve a list of all transactions.

Example request:

`curl http://localhost:4567/api/v1/transactions/`

#### Transaction details
`GET /api/v2/transactions/(long: id)/`

Retrieve details of a single transaction.

Status Codes:	
* 200 OK – no error
* 404 Not Found – There is no Transaction with this ID

#### Create transaction
`POST /api/v2/transactions/`

Query Parameters:
* amount (double) — count of money for transfer
* sender (long) — account of moneys sender
* recipient (long) — account of moneys recipient
