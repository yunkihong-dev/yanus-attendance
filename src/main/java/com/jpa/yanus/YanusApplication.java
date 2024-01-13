package com.jpa.yanus;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class YanusApplication {
	@PostConstruct
	public void started() {
		// timezone KST 셋팅
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}
	public static void main(String[] args) {
		SpringApplication.run(YanusApplication.class, args);
	}
}
