package com.collins.fileserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.CommandLineRunner;

import com.collins.fileserver.storage.StorageProperties;
import com.collins.fileserver.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CollinsFileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollinsFileServerApplication.class, args);
	}
	
	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
	
	
}
