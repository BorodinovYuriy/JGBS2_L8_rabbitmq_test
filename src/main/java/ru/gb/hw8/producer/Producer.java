package ru.gb.hw8.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer {
    private static final String EXCHANGE_NAME = "DoubleDirect";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        for (int i = 0; i < 10; i++) {
            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

                channel.basicPublish(EXCHANGE_NAME,
                        "consumer_1_Theme(routingKey)",
                        null,
                        "consumer_1_message".getBytes("UTF-8"));

                channel.basicPublish(EXCHANGE_NAME,
                        "consumer_2_Theme(routingKey)",
                        null,
                        "consumer_2_message".getBytes("UTF-8"));

            }
        }

    }
}