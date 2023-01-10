# Biblioteca API
## Api da biblioteca da Dona Gertrudes
#
Executar o postgres(docker)
```sh
docker run --name postgres -e "POSTGRES_PASSWORD=123" -p 5432:5432 -d postgres
```
#
Executar a aplicação
```sh
./mvnw spring-boot:run
```
#
Swagger
```sh
http://localhost:8080/swagger-ui.html#/
```

#
Collection postman

[Biblioteca.postman_collection.zip](https://github.com/Benezes/biblioteca-gertrudes/files/10387205/Biblioteca.postman_collection.zip)
#

## Stack

- Java (v17)
- Maven (v17.0.5)
- Spring Boot (v2.7.7)
- Postgresql
- Swagger

#Paths

## Paths - Cliente

| Função | Caminho |
| ------ | ------ |
| GET | /clientes/ |
| POST | /clientes/ |
| PUT | /clientes/id |

### Buscando um cliente com páginação
GET /clientes/

### Inserção de novo cliente
POST /clientes
```
{
    "nome" : "string",
    "cpf" : "string",
    "cep" : "string",
    "numeroResidencia" : "string",
    "complemento" : "string",
    "numeroCelular" : "string",
    "email" : "string"
}
```

### Atualização de cliente por id
PUT /clientes/id:int
```
{
    "nome" : "string",
    "cpf" : "string",
    "cep" : "string",
    "numeroResidencia" : "string",
    "complemento" : "string",
    "numeroCelular" : "string",
    "email" : "string"
}
```



## Paths - Funcionario

| Função | Caminho |
| ------ | ------ |
| GET | /funcionarios/ |
| GET | /funcionarios/regiao |
| POST | /funcionarios/ |
| PUT | /funcionarios/id |
| DELETE | /funcionarios/id |


### Buscando um funcionário com páginação
GET /funcionarios/

### Buscando a incidência dos funcionarios por CEP
GET /funcionarios/regiao

### Inserção de novo funcionário

> Note: Os cargos aceitaveis são RECEPCIONISTA, BIBLIOTECARIO, GERENTE

POST /funcionarios
```
{
    "nome" : "string",
    "cpf" : "string",
    "cep" : "string",
    "numeroResidencia" : "string",
    "complemento" : "string",
    "cargo" : "string",
    "salario" : double
}
```

### Atualização de funcionário por id
PUT /funcionarios/id:int
```
{
    "nome" : "string",
    "cpf" : "string",
    "cep" : "string",
    "numeroResidencia" : "string",
    "complemento" : "string",
    "cargo" : "string",
    "salario" : double
}
```

### Deleta um funcionário por id
DELETE /funcionarios/id:int

