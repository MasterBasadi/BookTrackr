package com.booktrackr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookTrackrApplication { // Runs the code and hosts the website
    public static void main(String[] args) {
        SpringApplication.run(BookTrackrApplication.class, args);
        System.out.println("\n\nCLICK HERE ----> http://localhost:8080/");
        System.out.println("\nDATABASE ----> https://railway.com/project/6c61da6b-944e-4b3f-82c8-f0fc0b11d8f4/service/ace90580-6ff6-419f-aaab-5db08b443b73/data?environmentId=b50af979-90e0-4fbf-a260-6ad895ad567d \n\n");
    }
}