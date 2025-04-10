package org.example.mobile.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.mobile.dto.request.ChatGPTRequest;
import org.example.mobile.dto.response.ChatGPTResponse;
import org.example.mobile.entity.Medicine;
import org.example.mobile.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate restTemplate;

    @Override
    public String checkDrugInteractions(Medicine newMedicine, List<Medicine> existingMedicines) {
        if (newMedicine == null || existingMedicines == null || existingMedicines.isEmpty()) {
            log.warn("Chưa cung cấp thuốc.");
            return null;
        }

        String existingMedicineNames = existingMedicines.stream()
                .map(Medicine::getName)
                .collect(Collectors.joining(", "));

        String userPrompt = buildUserPrompt(newMedicine.getName(), existingMedicineNames);
        ChatGPTRequest request = buildChatRequest(userPrompt);
        try {
            ResponseEntity<ChatGPTResponse> responseEntity = sendRequestToOpenAI(request);
            ChatGPTResponse response = responseEntity.getBody();
            
            if (response == null || response.getChoices() == null || response.getChoices().isEmpty() 
                    || response.getChoices().get(0) == null || response.getChoices().get(0).getMessage() == null) {
                log.error("Phản hồi không hợp lệ từ OpenAI API");
                return "Không thể kiểm tra tương tác thuốc.";
            }
            
            String content = response.getChoices().get(0).getMessage().getContent();
            
            if ("NONE".equalsIgnoreCase(content.trim())) {
                log.info("Không có tương tác thuốc");
                return null;
            }
            
            log.info("Tương tác thuốc: {}", content);
            return content;
        } catch (RestClientException e) {
            log.error("Lỗi call api checkDrugInteractions", e);
            return "Không thể kiểm tra tương tác thuốc.";
        }
    }

    private String buildUserPrompt(String newMedicineName, String existingMedicineNames) {
        return String.format(
                "Analyze potential drug interactions between %s (new medication) and the following existing medications: %s. " +
                "IMPORTANT: Be extremely precise and factual. Only report documented interactions with strong medical evidence. " +
                "Format your response exactly like this example: 'Panadol Extra 500mg chứa (paracetamol và caffeine) có thể tương tác với (sodium warfarin) trong warfarin dẫn tới tăng nguy cơ chảy máu.' " +
                "Respond in Vietnamese. " +
                "If there are NO PROVEN interactions or only theoretical/weak interactions, respond ONLY with 'NONE'. " +
                "Never invent or exaggerate interactions. " +
                "If there are multiple proven interactions, analyze only the single MOST CLINICALLY SIGNIFICANT interaction and return just that one. " +
                "The response must be exactly ONE SENTENCE following this exact format: " +
                "First medication name, its active ingredients in parentheses, the specific interacting ingredients from the second medication in parentheses, second medication name, and the clinical outcome.",
                newMedicineName, existingMedicineNames
        );
    }

    private ChatGPTRequest buildChatRequest(String prompt) {
        ChatGPTRequest.Message systemMessage = new ChatGPTRequest.Message("system",
                "You are a pharmaceutical expert analyzing drug interactions with focus on active ingredients. " +
                "You must be scientifically accurate and rely only on well-documented medical evidence. " +
                "CRITICAL INSTRUCTION: If no significant interaction exists between medications, you MUST respond with ONLY the word 'NONE'. DO NOT invent interactions. " +
                "When interactions do exist, respond exactly in this format: '[Medicine A] chứa (active ingredients X) có thể tương tác với (active ingredients Y) trong [Medicine B] dẫn tới [clinical outcome].' " +
                "IMPORTANT: Return ONLY ONE interaction - the most clinically significant one with strong evidence. " +
                "If multiple interactions exist, choose only the most important one based on severity and clinical relevance. " +
                "Keep response as a SINGLE SENTENCE only. " +
                "The sentence must follow this exact structure: first medicine name, its active ingredients, interacting ingredients from second medicine, second medicine name, and clinical outcome. " +
                "Always place active ingredients inside parentheses. " +
                "Use simple Vietnamese terms. " +
                "Your primary goal is accuracy - responding with 'NONE' is preferred over reporting an unproven interaction. " +
                "Do not include multiple interactions, line breaks, or any text beyond the single interaction sentence. " +
                "No disclaimers or additional information.");

        ChatGPTRequest.Message userMessage = new ChatGPTRequest.Message("user", prompt);

        ChatGPTRequest request = new ChatGPTRequest();
        request.setModel(model);
        request.setMessages(List.of(systemMessage, userMessage));

        return request;
    }

    private ResponseEntity<ChatGPTResponse> sendRequestToOpenAI(ChatGPTRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(openaiApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);
        log.info("Đang gửi yêu cầu tới OpenAI API: {}", model);

        return restTemplate.exchange(apiURL, HttpMethod.POST, entity, ChatGPTResponse.class);
    }
}
