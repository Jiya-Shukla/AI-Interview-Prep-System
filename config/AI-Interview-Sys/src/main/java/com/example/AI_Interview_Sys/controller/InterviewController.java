package com.example.AI_Interview_Sys.controller;

    import com.example.AI_Interview_Sys.service.OpenAIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

    @RestController
    @RequestMapping("/interview")
    @CrossOrigin
    public class InterviewController {

        @Autowired
        private OpenAIService openAIService;

        @GetMapping("/question")
        public String getQuestion() {
            return openAIService.generateQuestion();
        }

        @PostMapping("/evaluate")
        public String evaluateAnswer(@RequestBody Map<String, String> data) {

            String question = data.get("question");
            String answer = data.get("answer");

            return openAIService.evaluateAnswer(question, answer);
        }
    }

}
