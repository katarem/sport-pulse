package com.bytecodes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableCaching
@EnableFeignClients
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration.class
})

public class MsStandingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsStandingsApplication.class, args);
	}

}
