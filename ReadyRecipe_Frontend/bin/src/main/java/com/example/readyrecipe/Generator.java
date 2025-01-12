package com.example.readyrecipe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

@Service
public class Generator {

    @Value("${backend.api.url}")
    private String backendApiUrl;

    public ResponseEntity<String> generate(List<Double> nutritionInput, List<String> ingredients, Map<String, Object> params) {
        RestTemplate restTemplate = new RestTemplate();

        JSONObject request = new JSONObject();
        request.put("nutrition_input", nutritionInput);
        request.put("ingredients", ingredients);
        request.put("params", params);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);

        return restTemplate.postForEntity(backendApiUrl, entity, String.class);
    }
}