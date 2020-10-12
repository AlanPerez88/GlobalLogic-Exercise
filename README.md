# GlobalLogic-Exercise
Prueba practica de evaluación para Global Logic (Creación de usuarios)


`Java 8`
`Gradle`
`Spock`
`JacocoTestReport`

#### _Tasks_
```
gradle clean
```
```
gradle build
```
```
gradle bootRun
```
```
gradle jacocoTestReport
```




#### _Test the app_


* Crear usuario **POST**
Se validan los formatos para Email, Password. Una vez creado el usuario se genera un token (JWT) que es retornado en la salida del servicio
```
http://localhost:9001/user
{
    "name": "Alan",
    "email": "Alan@test.com",
    "password": "123Aa",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "countrycode": "75"
        },
        {
            "number": "68484108",
            "citycode": "9",
            "countrycode": "56"
        }
    ]
}
```


* Login. **POST**
Se valida que el usario ya exista en BD, si existe se valida token y se actualiza fecha de login.

```
http://localhost:9001/api/login
Enviar token generado al crear usuario en Authorization Header
{
    "email": "Alan@test.com",
    "password": "123Aa"
}
```


* Logout. **POST**
Se valida que el usario ya exista en BD, si existe se valida token y se actualiza isActive, dando a enteder que el usuario no esta activo.

```
http://localhost:9001/api/logout
Enviar token generado al crear usuario en Authorization Header
{
    "email": "Alan@test.com",
    "password": "123Aa"
}
```

* Update **PUT**
Actualizacion de usuario.
```
http://localhost:9001/user
{
    "id": 1,
    "name": "Alan",
    "password": "123Aa",
    "email": "Alan@test.com",
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBbGFuQHRlc3QuY29tIiwicGFzc3dvcmQiOiIxMjNBYSIsImlzcyI6ImNvbS5hcGVyZXouZXhlcmNpc2UiLCJleHAiOjE2MDI0NzQwNjB9.0AbUoJuLdcRZrdV2nQRq-uAGTp7g3J7CPjyoxuGbxPI",
    "created": "2020-10-12T03:40:00.834+00:00",
    "phones": [
        {
            "id": 2,
            "number": 1234567,
            "citycode": 1,
            "countrycode": 75
        },
        {
            "id": 3,
            "number": 68484108,
            "citycode": 9,
            "countrycode": 56
        }
    ]
}
```


* Get Token **POST**
Servicio que tiene la funcionalidad de entregar un token valido para un usuario ya existente.
```
http://localhost:9001/api/getToken
{
    "email": "Alan@test.com",
    "password": "123Aa"
}
```

