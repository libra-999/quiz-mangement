package org.example.tol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableScheduling
public class TolApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(TolApplication.class, args);
        System.out.println("Local IP: " + InetAddress.getLocalHost().getHostAddress());
    }
}
