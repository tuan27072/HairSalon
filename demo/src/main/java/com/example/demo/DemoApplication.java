package com.example.demo;

import com.example.demo.Service.SlotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Bean;

import java.time.LocalTime;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Student API", version = "1.0", description = "Information"))
@SecurityScheme(name = "api", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner run(SlotService slotService) {
		return args -> {
			// Generate slots from 8:00 AM to 8:00 PM, each 30 minutes
			LocalTime start = LocalTime.of(8, 0);
			LocalTime end = LocalTime.of(20, 0);
			LocalTime currentStartTime = start;

			while (currentStartTime.isBefore(end)) {
				LocalTime currentEndTime = currentStartTime.plusMinutes(30);
				String label = currentStartTime + " - " + currentEndTime;
				slotService.createSlotIfNotExists(currentStartTime, currentEndTime, label);
				currentStartTime = currentEndTime;  // Move to the next slot
			}
		};
	}
}
