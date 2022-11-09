package es.module2.imapi.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {


    // @Value("${rabbitmq.routing.json.key}")
    // private String routingJsonKey;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(Intrusion msg) {
        rabbitTemplate.convertAndSend(exchange, msg);
    }

}