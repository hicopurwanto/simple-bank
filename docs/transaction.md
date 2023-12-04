# Transaction API Spec

## Create Transactions

Endpoint : POST api/transactions/{idAccount}/transactions

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "toAccountId" : 1111111118,   // number
  "amount" : 100000           // number
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : 1,
    "accountId" : 1111111119,
    "toAccountId" : 1111111118,
    "amount" : 100000
  } 
}
```

Response Body (Gagal) :
```json
{
  "error": "Unauthorized"
}
```

```json
{
  "error": "data account tidak ditemukan"
}
```


## List Transactions

Endpoint : GET api/transactions/{idTransaction}/transactions

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : [
    {
      "id" : 1,
      "accountId" : 1111111119,
      "toAccountId" : 1111111118,
      "amount" : 100000
    }
  ] 
}
```

Response Body (Gagal) :
```json
{
  "error": "Unauthorized"
}
```
```json
{
  "error": "data Transactions tidak ditemukan"
}
```
