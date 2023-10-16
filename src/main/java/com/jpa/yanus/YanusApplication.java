package com.jpa.yanus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:10001/")
public class YanusApplication {
	public static void main(String[] args) {
		SpringApplication.run(YanusApplication.class, args);
	}

}
