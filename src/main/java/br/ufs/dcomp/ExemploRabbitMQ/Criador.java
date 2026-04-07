package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Criador {

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("3.81.27.206"); // Alterar
    factory.setUsername("admin"); // Alterar
    factory.setPassword("password"); // Alterar
    factory.setVirtualHost("/");    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    channel.queueDeclare("Q1", false,   false,     false,       null);
    channel.queueDeclare("Q2", false,   false,     false,       null);
    channel.queueDeclare("Q3", false,   false,     false,       null);
    channel.queueDeclare("Q4", false,   false,     false,       null);
    channel.queueDeclare("Q5", false,   false,     false,       null);
    
    channel.exchangeDeclare("E1", "direct");
    
    channel.queueBind("Q1", "E1", "EC");
    channel.queueBind("Q2", "E1", "CC");
    channel.queueBind("Q3", "E1", "SI");
    channel.queueBind("Q4", "E1", "EC");
    channel.queueBind("Q5", "E1", "SI");


    channel.close();
    connection.close();
  }
}