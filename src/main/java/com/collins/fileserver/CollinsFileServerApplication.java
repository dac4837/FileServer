package com.collins.fileserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import com.collins.fileserver.service.StorageProperties;
import com.collins.fileserver.service.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CollinsFileServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CollinsFileServerApplication.class, args);
	}
	
	@Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
	
	@Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.init();
        };
    }
	
	
}
