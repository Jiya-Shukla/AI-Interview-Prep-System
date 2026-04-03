package com.example.AI_Interview_Sys.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 🔹 Generate Question
    public String generateQuestion() {
        String prompt = "Generate one Java interview question for a fresher.";
        return callGemini(prompt);
    }

    // 🔹 Evaluate Answer
    public String evaluateAnswer(String question, String answer) {

        String prompt = "Evaluate this answer. Give score out of 10 and feedback.\n\n"
                + "Question: " + question + "\n"
                + "Answer: " + answer;

        return callGemini(prompt);
    }

    // 🔹 Core Gemini API Call
    private String callGemini(String prompt) {

        // ✅ Use stable model (important fix)
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = """
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ]
        }
        """.formatted(prompt);

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    Map.class
            );

            // ✅ Extract only useful text (important improvement)
            Map responseBody = response.getBody();

            if (responseBody != null) {
                var candidates = (java.util.List) responseBody.get("candidates");
                if (candidates != null && !candidates.isEmpty()) {
                    Map first = (Map) candidates.get(0);
                    Map content = (Map) first.get("content");
                    var parts = (java.util.List) content.get("parts");
                    Map part = (Map) parts.get(0);

                    return part.get("text").toString();
                }
            }

            return "No response from Gemini";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}