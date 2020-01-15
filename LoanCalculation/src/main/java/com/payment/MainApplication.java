package com.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * author: Sahil Aggarwal
 */

@Slf4j
@SpringBootApplication
public class MainApplication {

    /**
     * Starting point for the application for calculating payment.
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);

        log.info("Main application Started");
    }

}
