package com.example.AI_Interview_Sys.controller;

import com.example.AI_Interview_Sys.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/interview")
@CrossOrigin(origins = "*")
public class InterviewController {

    private final GeminiService geminiService;

    @Autowired
    public InterviewController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    // 🔹 API to generate interview question
    @GetMapping("/question")
    public String getQuestion() {
        try {
            return geminiService.generateQuestion();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating question: " + e.getMessage();
        }
    }

    // 🔹 API to evaluate answer
    @PostMapping("/evaluate")
    public String evaluateAnswer(@RequestBody Map<String, String> data) {

        String question = data.get("question");
        String answer = data.get("answer");

        if (question == null || answer == null) {
            return "Error: Question and Answer are required";
        }

        try {
            return geminiService.evaluateAnswer(question, answer);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error evaluating answer: " + e.getMessage();
        }
    }
}