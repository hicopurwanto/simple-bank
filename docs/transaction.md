# Address API Spec

## Create Address

Endpoint : POST api/contacts/{idContact}/addresses

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "street" : "Jalan Apa",
  "city" : "Kota",
  "province" : "Provinsi",
  "country" : "Negara",
  "postalCode" : "123456"
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "random-string",
    "street" : "Jalan Apa",
    "city" : "Kota",
    "province" : "Provinsi",
    "country" : "Negara",
    "postalCode" : "123456"
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
  "error": "data contact tidak ditemukan"
}
```

## Update Address

Endpoint : PUT api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "street" : "Jalan Apa",
  "city" : "Kota",
  "province" : "Provinsi",
  "country" : "Negara",
  "postalCode" : "123456"
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "random-string",
    "street" : "Jalan Apa",
    "city" : "Kota",
    "province" : "Provinsi",
    "country" : "Negara",
    "postalCode" : "123456"
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
  "error": "data address tidak ditemukan"
}
```

## Get Address

Endpoint : GET api/contacts/{idContact}/addresses/{idAddress}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "random-string",
    "street" : "Jalan Apa",
    "city" : "Kota",
    "province" : "Provinsi",
    "country" : "Negara",
    "postalCode" : "123456"
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
  "error": "data address tidak ditemukan"
}
```

## Remove Address

Endpoint : DELETE api/contacts/{idContact}/addresses/{idAddress}

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
  "errors" : "data address tidak ditemukan"
}
```

## List Address

Endpoint : GET api/contacts/{idContact}/addresses

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : [
    {
      "id" : "random-string",
      "street" : "Jalan Apa",
      "city" : "Kota",
      "province" : "Provinsi",
      "country" : "Negara",
      "postalCode" : "123456"
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
  "error": "data contact tidak ditemukan"
}
```
