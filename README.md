# Workshop-JavaFX Project

## Descrição

Este é um projeto desenvolvido em **JavaFX** que interage com um banco de dados **MySQL**. O sistema gerencia informações de vendedores (*seller*) e departamentos (*department*), permitindo operações como consulta, inserção, atualização e remoção de registros.

## Tecnologias Utilizadas

- **JavaFX** para interface gráfica
- **MySQL** para armazenamento de dados
- **JDBC** para conexão com o banco de dados
- **Maven** para gerenciamento de dependências

## Estrutura do Banco de Dados

O projeto utiliza duas tabelas principais:

### seller (Vendedores)

| Campo          | Tipo         | Descrição                  |
| -------------- | ------------ | -------------------------- |
| id             | INT (PK)     | Identificador único        |
| name           | VARCHAR(100) | Nome do vendedor           |
| email          | VARCHAR(100) | Email do vendedor          |
| birth\_date    | DATE         | Data de nascimento         |
| base\_salary   | DOUBLE       | Salário base               |
| department\_id | INT (FK)     | Referência ao departamento |

### department (Departamentos)

| Campo | Tipo         | Descrição            |
| ----- | ------------ | -------------------- |
| id    | INT (PK)     | Identificador único  |
| name  | VARCHAR(100) | Nome do departamento |

## Funcionalidades

- Listagem de vendedores e departamentos
- Cadastro, edição e remoção de vendedores
- Cadastro, edição e remoção de departamentos
- Integração com MySQL via JDBC

## Configuração e Execução

### Requisitos

- Java 17+
- MySQL instalado e configurado
- Maven instalado
- Driver mysql-connector

### Configuração do Banco de Dados

1. Criar o banco de dados:
   ```sql
   CREATE DATABASE javafx_db;
   ```
2. Criar as tabelas:
   ```sql
   CREATE TABLE department (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL
   );

   CREATE TABLE seller (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       email VARCHAR(100) NOT NULL,
       birth_date DATE NOT NULL,
       base_salary DOUBLE NOT NULL,
       department_id INT,
       FOREIGN KEY (department_id) REFERENCES department(id)
   );
   ```
3. Configurar a conexão no arquivo de propriedades (`database.properties` ou similar):
   ```properties
   db.url=jdbc:mysql://localhost:3306/javafx_db
   db.user=root
   db.password=senha
   ```

### Execução do Projeto

1. Clonar o repositório:
   ```sh
   git clone <URL_DO_REPOSITORIO>
   ```

