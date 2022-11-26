package es.module2.imapi.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.core.BindingBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

@EnableRabbit
@Configuration
// @PropertySources({
//     @PropertySource("classpath:rabbitMq.properties")
// })
public class ProducerConfig {

    private static final Logger log = LoggerFactory.getLogger(ProducerConfig.class);
    // @Value("${rabbitmq.cam.queue.name}")
    // private String cam_queue;

    // @Value("${rabbitmq.alarm.queue.name}")
    // private String alarm_queue;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;
    
    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${rabbitmq.alarm.exchange.name}")
    private String alarm_exchange;


    @Value("${rabbitmq.cam.exchange.name}")
    private String cam_exchange;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory=null;
        try{
            connectionFactory = new CachingConnectionFactory(new URI("amqps://"+username+":"+password+"@"+host));
            //connectionFactory.setUsername(username);
            //connectionFactory.setPassword(password);

            log.info("Creating connection factory with rabbit mq: "+ connectionFactory.toString());

            return connectionFactory;
        } catch (URISyntaxException e){
            log.error("URI ERROR");

        }
            return connectionFactory;
        
    }

    /**
     * Required for executing adminstration functions against an AMQP Broker
     */
    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }


    // /**
    //  * This queue will be declared. This means it will be created if it does not exist. Once declared, you can do something
    //  * like the following:
    //  * 
    //  * @RabbitListener(queues = "#{@myDurableQueue}")
    //  * @Transactional
    //  * public void handleMyDurableQueueMessage(CustomDurableDto myMessage) {
    //  *    // Anything you want! This can also return a non-void which will queue it back in to the queue attached to @RabbitListener
    //  * }
    //  */
    // @Bean
    // public Queue myDurableQueue() {
    //     // This queue has the following properties:
    //     // name: my_durable
    //     // durable: true
    //     // exclusive: false
    //     // auto_delete: false
    //     return new Queue("my_durable", true, false, false);
    // }

    // @Bean
    // public Queue cam_queue() {
    //     return new Queue("cam_queue",true);
    // }

    // @Bean
    // public Queue alarm_queue() {
    //     return new Queue("alarm_queue",true);
    // }


    // @Bean
    // public Queue alarm_queue() {
    //     return new Queue(alarm_queue,true);
    // }
    
    @Bean
    DirectExchange cam_exchange(){
        return new DirectExchange(cam_exchange);
    }

        @Bean
    DirectExchange alarm_exchange(){
        return new DirectExchange(alarm_exchange);
    }


    // // binding between queue and exchange using routing key
    // @Bean
    // public Binding cam_binding(){
    //     return BindingBuilder
    //             .bind(cam_queue())
    //             .to(cam_exchange())
    //             .with("cam.*");
    // }

    //     // binding between queue and exchange using routing key
    // @Bean
    // public Binding alarm_binding(){
    //     return BindingBuilder
    //             .bind(alarm_queue())
    //             .to(alarm_exchange())
    //             .with("alarm.*");
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