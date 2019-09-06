package com.aivinog1.cardpay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).logStartupInfo(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        // @todo #1:30m needs to remove this log message. As a requirement for test our log should be clear.
        log.info("Started!");
    }
}
