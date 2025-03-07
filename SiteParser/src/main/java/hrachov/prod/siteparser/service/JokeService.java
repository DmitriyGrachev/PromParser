package hrachov.prod.siteparser.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JokeService {
    private static final String BASE_URL = "https://v2.jokeapi.dev/joke/Programming?format=txt";

    public String getRandomJoke() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(BASE_URL, String.class);
        System.out.println(response);
        return response;
    }
}
