package org.example.mobile.service.impl;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.entity.Medicine;
import org.example.mobile.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.model:gpt-4o-mini}")
    private String model;

    @Value("${openai.timeout:60}")
    private int timeout;

    @Override
    public String checkDrugInteractions(Medicine newMedicine, List<Medicine> existingMedicines) {
        if (existingMedicines == null || existingMedicines.isEmpty()) {
            return null;
        }

        try {
            OpenAiService service = new OpenAiService(openaiApiKey, Duration.ofSeconds(timeout));

            List<ChatMessage> messages = new ArrayList<>();
            
            ChatMessage systemMessage = new ChatMessage("system", 
                "You are a pharmaceutical expert analyzing potential drug interactions. " +
                "Respond in Vietnamese. Provide only the interaction information. " +
                "If there are no interactions, respond 'NONE'. " +
                "If there are interactions, respond with a complete explanation of the interaction risks."
            );
            messages.add(systemMessage);

            String existingMedicineNames = existingMedicines.stream()
                    .map(Medicine::getName)
                    .collect(Collectors.joining(", "));
            
            String userPrompt = String.format(
                "Analyze potential drug interactions between %s (new medication) and the following existing medications: %s. " +
                "Are there any known interactions or contraindications?",
                newMedicine.getName(), existingMedicineNames
            );
            
            ChatMessage userMessage = new ChatMessage("user", userPrompt);
            messages.add(userMessage);

            ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                    .messages(messages)
                    .model(model)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            log.info("Sending request to OpenAI API with model: {}", model);
            
            String response = service.createChatCompletion(completionRequest)
                    .getChoices().get(0).getMessage().getContent();
            
            log.info("Received response from OpenAI API");

            if ("NONE".equalsIgnoreCase(response.trim())) {
                return null;
            }
            
            return response;
        } catch (Exception e) {
            log.error("Error calling OpenAI API to check drug interactions", e);
            return "Không thể kiểm tra tương tác thuốc. Vui lòng liên hệ bác sĩ.";
        }
    }
} 