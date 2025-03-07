package hrachov.prod.siteparser.controller;

import hrachov.prod.siteparser.service.DogService;
import hrachov.prod.siteparser.service.JokeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private JokeService jokeService;
    @Autowired
    private DogService dogService;

    // random joke
    @GetMapping("/joke")
    public String getJoke(Model model) {
        String joke = jokeService.getRandomJoke();
        model.addAttribute("joke", joke);
        return "joke";
    }

    // random dog image
    @GetMapping("/dog")
    public String getDogImage(Model model) {
        String imageUrl = dogService.getRandomDogImage();
        if (imageUrl != null) {
            model.addAttribute("imageUrl", imageUrl);
        } else {
            model.addAttribute("imageUrl", "error.jpg"); //or some default image for error handling
        }

        return "dog";
    }
}
