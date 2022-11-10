package es.module2.imapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@EnableRabbit
@SpringBootApplication
public class ImapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImapiApplication.class, args);
	}

}
