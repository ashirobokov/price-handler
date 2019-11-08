package ru.ashirobokov.microservices.price_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.ashirobokov.microservices.receiver.PriceReceiver;

@ComponentScan("ru.ashirobokov.microservices")
@SpringBootApplication
public class PriceHandlerApplication {
	public final static Logger LOG = LoggerFactory.getLogger(PriceHandlerApplication.class);

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(PriceHandlerApplication.class, args);
		PriceReceiver receiver = applicationContext.getBean(PriceReceiver.class);
		receiver.getPrices();
//		receiver.getStrings();
	}

}
