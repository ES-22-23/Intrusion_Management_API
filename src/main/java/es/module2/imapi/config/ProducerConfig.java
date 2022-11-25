package es.module2.imapi.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Configuration
public class ProducerConfig {
    private static final Logger log = LoggerFactory.getLogger(ProducerConfig.class);

    @Value("${rabbitmq.cam.queue.name}")
    private String cam_queue;

    @Value("${rabbitmq.alarm.queue.name}")
    private String alarm_queue;

    // @Value("${rabbitmq.exchange.name}")
    // private String exchange;

    // @Value("${rabbitmq.routing.key}")
    // private String routingKey;

    @Bean
    public Queue cam_queue() {
        return new Queue(cam_queue,true);
    }


    @Bean
    public Queue alarm_queue() {
        return new Queue(alarm_queue,true);
    }
    
    // @Bean
    // TopicExchange exchange(){
    //     return new TopicExchange(exchange);
    // }


    // // binding between queue and exchange using routing key
    // @Bean
    // public Binding binding(){
    //     return BindingBuilder
    //             .bind(queue())
    //             .to(exchange())
    //             .with(routingKey);
    // }

    // @Bean
    // public MessageConverter converter(){
    //     return new Jackson2JsonMessageConverter();
    // }

    // @Bean
    // public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
    //     log.info("Config -> rabbit template");
    //     RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    //     rabbitTemplate.setMessageConverter(converter());
    //     return rabbitTemplate;
    // }

}