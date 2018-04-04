package com.bazalytskyi.coursework.client;

import com.bazalytskyi.coursework.entities.UserDto;
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
        UserDto obj = new UserDto();
        obj.setEmail("vbazalickyi");
        obj.setPassword("111");
        obj.setUsername("Baza");
        obj.setRole("ROLE_ADMIN");

        HttpEntity<UserDto> requestEntity = new HttpEntity<UserDto>(obj, headers);
        restTemplate.put(url, requestEntity);
    }

    public static void main(String args[]) {
        RestClient client = new RestClient();
        client.addUser();

    }
}
