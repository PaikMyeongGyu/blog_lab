package com.bisang.redlock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class RedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedLockApplication.class, args);
    }
}
