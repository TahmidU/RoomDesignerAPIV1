package com.aarrd.room_designer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RoomDesignerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomDesignerApplication.class, args);
	}

}
