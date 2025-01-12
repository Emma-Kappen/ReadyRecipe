package com.example.readyrecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class RecommendationController {

    @Autowired
    private Generator generator;

    @GetMapping("/")
    public String showForm(Model model) {
        return "form";
    }

    @PostMapping("/generate")
    public String generateRecommendations(@RequestParam String nutritionInput,
                                          @RequestParam String ingredients,
                                          @RequestParam String params,
                                          Model model) {
        List<Double> nutritionInputList = Stream.of(nutritionInput.split(","))
                                                .map(Double::parseDouble)
                                                .collect(Collectors.toList());
        List<String> ingredientsList = Stream.of(ingredients.split(","))
                                             .collect(Collectors.toList());
        Map<String, Object> paramsMap = Stream.of(params.split(","))
                                              .map(param -> param.split(":"))
                                              .collect(Collectors.toMap(p -> p[0], p -> p[1]));

        ResponseEntity<String> response = generator.generate(nutritionInputList, ingredientsList, paramsMap);
        model.addAttribute("response", response.getBody());
        return "result";
    }
}