# Contact API Spec

## Create Contact

Enpoint : POST api/contacts

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "firstName" : "Hico",
  "lastName" : "Purwokerto",
  "email" : "contoh@email.com",
  "phone" : "089988999888"
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "random-string",
    "firstName" : "Hico",
    "lastName" : "Purwokerto",
    "email" : "contoh@email.com",
    "phone" : "089988999888"
  }
}
```

Response Body (Gagal) :
```json
{
  "error": "format data salah"
}
```

## Update Contact

Enpoint : PUT api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "firstName" : "Hico",
  "lastName" : "Purwokerto",
  "email" : "contoh@email.com",
  "phone" : "089988999888"
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "random-string",
    "firstName" : "Hico",
    "lastName" : "Purwokerto",
    "email" : "contoh@email.com",
    "phone" : "089988999888"
  }
}
```

Response Body (Gagal) :
```json
{
  "error": "format data salah"
}
```

## Get Contact

Enpoint : GET api/contacts/{idContact}

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : {
    "id" : "random-string",
    "firstName" : "Hico",
    "lastName" : "Purwokerto",
    "email" : "contoh@email.com",
    "phone" : "089988999888"
  }
}
```

Response Body (Gagal) :
```json
{
  "error": "data contact tidak ditemukan"
}
```


## Search Contact

Enpoint : GET api/contacts

Query Param :
- name : String, contact first name or last name, using like query, optional
- phone : String, contact phone, using like query, optional
- email : String, contact email, using like query, optional
- page : Integer, start from 0, default 0
- size : Integer, default 10

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : [
    {
      "id" : "random-string",
      "firstName" : "Hico",
      "lastName" : "Purwokerto",
      "email" : "contoh@email.com",
      "phone" : "089988999888"
    }
  ],
  "paging" : {
    "currentPage" : 0,
    "totalPage" : 10,
    "size" : 10
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

## Remove Contact

Enpoint : DELETE api/contacts/{idContact}

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
  "error": "data contact tidak ditemukan"
}
```