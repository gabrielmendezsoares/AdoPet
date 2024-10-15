# AdoPet - Backend

**AdoPet** é um sistema de gestão de adoção de animais que conecta animais resgatados a futuros adotantes, proporcionando uma maneira fácil e eficiente para a adoção de animais. Este repositório contém o backend do projeto, desenvolvido em Java utilizando Spring Boot.

## Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **H2 Database** (para desenvolvimento)
- **PostgreSQL** (para produção)
- **Swagger** (para documentação da API)
- **JUnit e Mockito** (para testes)
- **JaCoCo** (para cobertura de testes)

## Configuração do Ambiente

### Pré-requisitos

- JDK 21
- Maven
- PostgreSQL (opcional, para produção)

### Clonando o Repositório

```bash
git clone https://github.com/gabrielmendezsoares/AdoPet.git
cd AdoPet/backend
```

### Executando a Aplicação

Para rodar o aplicativo, utilize o seguinte comando:

```bash
mvn spring-boot:run
```

### Configuração do Banco de Dados

A configuração do banco de dados pode ser encontrada no arquivo src/main/resources/application-development.properties e src/main/resources/application-production.properties. Para uso com o H2 em desenvolvimento, não é necessário fazer nenhuma configuração adicional. Para usar o PostgreSQL, configure as propriedades do banco de dados no application-production.properties.

## Testes

Os testes podem ser executados usando o Maven:

```bash
mvn test
```

### Cobertura de Testes

A cobertura de testes é medida usando JaCoCo. Para gerar o relatório, use o seguinte comando:

```bash
mvn clean test jacoco:report
```

O relatório será gerado na pasta target/site/jacoco.

## Documentação da API

A documentação da API pode ser acessada através do Swagger, disponível em:

```
http://localhost:9090/api-docs/
```
