package br.ufs.dcomp.ExemploRabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmissorChefia {

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("3.81.27.206"); // Alterar
    factory.setUsername("admin"); // Alterar
    factory.setPassword("password"); // Alterar
    factory.setVirtualHost("/");    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

                      //(queue-name, durable, exclusive, auto-delete, params); 
    String messageEC = "Olá EC!!!";
    String messageCC = "Olá CC!!!";
    String messageSI = "Olá SI!!!";
    
                    //  (exchange, routingKey, props, message-body             ); 
    channel.basicPublish("E1",       "EC", null,  messageEC.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + messageEC + "'");
    
    channel.basicPublish("E1",       "CC", null,  messageCC.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + messageCC + "'");
    
    channel.basicPublish("E1",       "SI", null,  messageSI.getBytes("UTF-8"));
    System.out.println(" [x] Mensagem enviada: '" + messageSI + "'");

    channel.close();
    connection.close();
  }
}