package es.module2.imapi.service;

import  es.module2.imapi.model.Intrusion;
import  es.module2.imapi.model.HealthStatus;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Producer {

    private static final Logger log = LoggerFactory.getLogger(Producer.class);

    // @Value("${rabbitmq.routing.json.key}")
    // private String routingJsonKey;

    @Value("${rabbitmq.cam.queue.name}")
    private String cam_queue;

    @Value("${rabbitmq.alarm.queue.name}")
    private String alarm_queue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Intrusion msg) {
        log.info("Producer -> Sending message" + msg.toString());

        rabbitTemplate.convertAndSend(cam_queue, msg.toString());
    }


    public void activate_alarms(String alarmId) {
        log.info("Producer -> Activating Alarms" + alarmId);
        log.info("Queue Name -> " + alarm_queue);

        rabbitTemplate.convertAndSend(alarm_queue, 
            "{" +
            " \"alarmId\":" + alarmId  +
            "}");
    }
}