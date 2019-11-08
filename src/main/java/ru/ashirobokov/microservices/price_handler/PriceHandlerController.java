package ru.ashirobokov.microservices.price_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PriceHandlerController {

    public final static Logger LOG = LoggerFactory.getLogger(PriceHandlerController.class);

    @RequestMapping("/")
    public String test() {
        LOG.info("PriceHandlerController...test");
        return " PriceHandlerController...test...test...test ";
    }

}
