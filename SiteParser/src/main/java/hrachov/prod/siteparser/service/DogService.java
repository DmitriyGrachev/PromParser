package hrachov.prod.siteparser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Service
public class DogService {
    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";

    public String getRandomDogImage() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(BASE_URL, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode root = objectMapper.readTree(response);
            return root.path("message").asText();
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Or handle the error appropriately
        }
    }
}
