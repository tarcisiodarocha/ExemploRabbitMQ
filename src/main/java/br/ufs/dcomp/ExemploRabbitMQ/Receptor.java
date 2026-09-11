package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Receptor {

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

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // (queue-name, durable, exclusive, auto-delete, params)
    channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    
    System.out.println(" [*] Esperando recebimento de mensagens...");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)           throws IOException {

        String message = new String(body, StandardCharsets.UTF_8);
        System.out.println(" [x] Mensagem recebida: '" + message + "'");

        // Confirma somente depois de processar a mensagem.
        channel.basicAck(envelope.getDeliveryTag(), false);
      }
    };
    // (queue-name, autoAck, consumer)
    channel.basicConsume(QUEUE_NAME, false, consumer);
  }
}
