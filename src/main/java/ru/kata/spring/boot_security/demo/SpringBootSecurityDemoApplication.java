package ru.kata.spring.boot_security.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import ru.kata.spring.boot_security.demo.model.User1;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
        String url = "http://94.198.50.185:7081/api/users";

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String sessionId = responseHeaders.getFirst("set-cookie");


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", sessionId);

        User1 requestBody = new User1(3L, "James", "Brown", (byte)23);
        User1 requestBody1 = new User1(3L, "Thomas", "Shelby", (byte)23);

        HttpEntity<User1> request = new HttpEntity<>(requestBody, headers);
        HttpEntity<User1> request1 = new HttpEntity<>(requestBody1, headers);

        StringBuilder stringBuilder = new StringBuilder();

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        stringBuilder.append(response.getBody());

        ResponseEntity<String> response1 = restTemplate.exchange(url, HttpMethod.PUT, request1, String.class);
        stringBuilder.append(response1.getBody());

        ResponseEntity<String> response2 = restTemplate.exchange(url + "/3", HttpMethod.DELETE, request1, String.class);
        stringBuilder.append(response2.getBody());

        System.out.println("CODE - " + stringBuilder);
    }

}
