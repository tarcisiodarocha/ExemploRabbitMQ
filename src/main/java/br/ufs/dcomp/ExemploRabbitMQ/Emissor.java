package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Emissor {

  private static final String QUEUE_NAME = "minha-fila";

  public static void main(String[] argv) throws Exception {
    Properties properties = new Properties();
    try (Reader reader = Files.newBufferedReader(
        Path.of("config", "rabbitmq.properties"), StandardCharsets.UTF_8)) {
      properties.load(reader);
    }

    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(properties.getProperty("rabbitmq.host"));
    factory.setUsername(properties.getProperty("rabbitmq.user"));
    factory.setPassword(properties.getProperty("rabbitmq.password"));
    factory.setVirtualHost("/");

    try (Connection connection = factory.newConnection();
         Channel channel = connection.createChannel()) {
      // (queue-name, durable, exclusive, auto-delete, params)
      channel.queueDeclare(QUEUE_NAME, true, false, false, null);

      String message = "Olá!!!";

      // A fila e a mensagem são persistentes.
      channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,
          message.getBytes(StandardCharsets.UTF_8));
      System.out.println(" [x] Mensagem enviada: '" + message + "'");
    }
  }
}
