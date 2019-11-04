package com.odm.app;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    private static Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        // note here, we retrieve the trace id (and log it)
        // if we made an outbound api call, we should append the header
        logger.info("Greeting! Greeting! request id:" + MDC.get(LoggingFilter.REQUEST_ID_MARKER));
        
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}