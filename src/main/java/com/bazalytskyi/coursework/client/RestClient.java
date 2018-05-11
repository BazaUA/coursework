package com.bazalytskyi.coursework.client;

import com.bazalytskyi.coursework.entities.UserEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class RestClient {
    public void addUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/signup";
        UserEntity obj = new UserEntity();
        obj.setEmail("baza@gmail.com");
        obj.setPassword("111");
        obj.setUsername("baza");


        HttpEntity<UserEntity> requestEntity = new HttpEntity<UserEntity>(obj, headers);
        restTemplate.put(url, requestEntity);
    }

    public static void main(String args[]) {
        RestClient client = new RestClient();
        client.addUser();

    }
}
