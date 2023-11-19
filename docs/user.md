# User API Spec

## Register User

Endpoint : POST /api/users

Request Body : 
```json
{
  "username" : "nobicode",
  "password" : "rahasia",
  "name" : "Hico Purwokerto"
}
```

Response Body (Berhasil) :
```json
{
  "data" : "OK"
}
```

Response Body (Gagal) : 
```json
{
  "errors" : "username tidak boleh kosong, ???"
}
```

## Login User

Endpoint : POST /api/auth/login

Request Body :
```json
{
  "username" : "nobicode",
  "password" : "rahasia"
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "token" : "TOKEN",
    "expiredAt" : 123456789 // miliseconds
  }
}
```

Response Body (Gagal) :
```json
{
  "errors" : "username atau password salah"
}
```

## Get User

Endpoint : GET /api/users/current

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : {
    "username" : "nobicode",
    "name" : "Hico Purwokerto"
  }
}
```

Response Body (Gagal) :
```json
{
  "errors" : "Unauthorized"
}
```

## Update User

Endpoint : PATCH /api/users/current

Request Header :
- X-API-TOKEN : Token (Mandatory)

Request Body :
```json
{
  "name" : "Hico Purwokerto", // isi jika perlu untuk update "name"
  "password" : "new password" // isi jika perlu untuk update "password"
}
```

Response Body (Berhasil) :
```json
{
  "data" : {
    "username" : "nobicode",
    "name" : "Hico Purwokerto"
  }
}
```

Response Body (Gagal) :
```json
{
  "errors" : "Unauthorized"
}
```

## Logout User

Endpoint : DELETE /api/auth/logout

Request Header :
- X-API-TOKEN : Token (Mandatory)

Response Body (Berhasil) :
```json
{
  "data" : "OK"
}
```