package es.module2.imapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import  es.module2.imapi.model.Intrusion;

@Component
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    // @Value("${rabbitmq.routing.json.key}")
    // private String routingJsonKey;

    @Value("${rabbitmq.cam.exchange.name}")
    private String cam_exchange;

    @Value("${rabbitmq.alarm.exchange.name}")
    private String alarm_exchange;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Intrusion msg) {
        log.info("Producer -> Sending message" + msg.toString());

        rabbitTemplate.convertAndSend(cam_exchange, "cam",msg.toString());
    }


    public void activate_alarms(String propertyId) {
        log.info("Producer -> Activating Alarms");
        log.info("Queue Name -> " + alarm_exchange);
        log.info("Property Id -> " + propertyId);

        rabbitTemplate.convertAndSend(alarm_exchange, "alarm",
            "{" +
            " \"propertyId\":" + propertyId  +
            "}");
    }
}