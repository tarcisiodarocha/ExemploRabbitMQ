# ExemploRabbitMQ

Exemplo introdutório de um produtor (`Emissor`) e um consumidor (`Receptor`) usando uma fila RabbitMQ.

## Pré-requisitos

- Java 17 ou superior
- Maven 3.8 ou superior
- RabbitMQ em execução, localmente ou em um servidor acessível

O exemplo carrega exclusivamente o arquivo local `config/rabbitmq.properties`. Edite `config/rabbitmq.properties` com os dados do servidor. Esse arquivo é obrigatório e é ignorado pelo Git para que credenciais não sejam enviadas ao repositório.

## Compilar

```bash
mvn clean compile assembly:single
```

## Executar

Inicie primeiro o consumidor e, em outro terminal, o produtor:

```bash
java -cp target/ExemploRabbitMQJava-1.0-SNAPSHOT-jar-with-dependencies.jar br.ufs.dcomp.ExemploRabbitMQ.Receptor
java -cp target/ExemploRabbitMQJava-1.0-SNAPSHOT-jar-with-dependencies.jar br.ufs.dcomp.ExemploRabbitMQ.Emissor
```

A fila e as mensagens são persistentes. O consumidor confirma uma mensagem somente depois de exibi-la.
