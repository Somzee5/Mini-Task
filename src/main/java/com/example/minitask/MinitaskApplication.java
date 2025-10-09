package com.example.minitask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MinitaskApplication {

	public static void main(String[] args) {
//        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
//        System.out.println(enc.encode("admin123"));
		SpringApplication.run(MinitaskApplication.class, args);



	}
}

//$2a$10$bJmPpQDYw77WLHaDvIuQIeWYtxZfo3tR1QI/J8otSAa8l2IEVujia