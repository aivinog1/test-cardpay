package com.aivinog1.cardpay;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author aivinog1
 * @since 06.09.2019
 */
@SpringBootApplication
public class App {

    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).logStartupInfo(false).run(args);
    }

}
