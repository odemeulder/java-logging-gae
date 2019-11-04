package com.odm.app;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Greeting {
    
    private static Logger LOGGER = LoggerFactory.getLogger(Greeting.class);

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        LOGGER.info("building Greeting");
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}