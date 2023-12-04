package com.jpa.yanus;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://52.78.57.113:10001/")
public class YanusApplication {
	public static void main(String[] args) {
		SpringApplication.run(YanusApplication.class, args);
	}
}
