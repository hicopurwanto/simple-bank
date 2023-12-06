# Account API Spec

## Create Account

Enpoint : POST api/accounts

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "username" : "userhico",
  "accountNumber" : "1111111119"    // sequence
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "1",               // sequence
    "username" : "userhico",
    "accountNumber" : "1111111119"
  }
}
```

Response Body (Gagal) :
```json
{
  "error": "format data salah"
}
```


## Get Account

Enpoint : GET api/accounts/{idAccount}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "1",
    "username" : "userhico",
    "accountNumber" : "1111111119"
  }
}
```

Response Body (Gagal) :
```json
{
  "error": "data account tidak ditemukan"
}
```


## Remove Account

Enpoint : DELETE api/accounts/{idAccount}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : "OK"
}
```

Response Body (Gagal) :
```json
{
  "error": "data account tidak ditemukan"
}
```