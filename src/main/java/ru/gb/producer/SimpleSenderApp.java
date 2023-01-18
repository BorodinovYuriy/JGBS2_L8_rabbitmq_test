package ru.gb.producer;

import com.rabbitmq.client.*;

public class SimpleSenderApp {
    private final static String QUEUE_NAME = "myQueue";
    private final static String EXCHANGER_NAME = "myQueue_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //объявляем обменник(труба)
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.DIRECT);
            //объединяем обменник с очередью
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //привязываем очередь и эксченджер
            channel.queueBind(QUEUE_NAME, EXCHANGER_NAME, "java");

            String message = "myQueue_Моё сообщение";
            channel.basicPublish(EXCHANGER_NAME, "java", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
























