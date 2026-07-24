package com.homebank.api.campaign;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OpenAiCampaignService {
    private static final String INSTRUCTIONS = """
            You are a senior campaign strategist and copy director. Turn the supplied brief into one
            focused, ownable campaign direction. Be concise, specific, commercially useful, and true
            to the requested tone. Avoid clichés, unsupported claims, and generic filler. Produce
            exactly three meaningfully different copy variants and a practical launch checklist.
            The two image prompts must describe a coherent campaign art direction, including composition,
            lighting, palette, subject, camera or illustration details, negative constraints, and clean
            negative space for typography. Do not ask questions. Return only the requested schema.
            """;

    private final ObjectMapper mapper;
    private final OpenAiProperties properties;
    private final HttpClient httpClient;

    public OpenAiCampaignService(ObjectMapper mapper, OpenAiProperties properties) {
        this.mapper = mapper;
        this.properties = properties;
        this.httpClient = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(20)).build();
    }

    public CampaignResult generate(CampaignRequest request) {
        requireApiKey();
        JsonNode campaignJson = createCampaign(request);
        return new CampaignResult(
                campaignJson.path("conceptName").asText(),
                campaignJson.path("concept").asText(),
                campaignJson.path("strategicAngle").asText(),
                mapper.convertValue(campaignJson.path("copyVariants"),
                        mapper.getTypeFactory().constructCollectionType(List.class, CampaignResult.CopyVariant.class)),
                mapper.convertValue(campaignJson.path("launchChecklist"),
                        mapper.getTypeFactory().constructCollectionType(List.class, CampaignResult.ChecklistItem.class)),
                generateImages(campaignJson.path("imageDirections"))
        );
    }

    private JsonNode createCampaign(CampaignRequest request) {
        JsonNode response = postResponse(Map.of(
                "model", properties.textModel(),
                "store", false,
                "instructions", INSTRUCTIONS,
                "input", briefInput(request),
                "text", Map.of("format", campaignSchema())
        ));
        String outputText = response.path("output").findValues("text").stream()
                .map(JsonNode::asText)
                .filter(text -> !text.isBlank())
                .findFirst()
                .orElseThrow(() -> new CampaignGenerationException("OpenAI returned no campaign text."));
        try {
            return mapper.readTree(outputText);
        } catch (IOException exception) {
            throw new CampaignGenerationException("OpenAI returned campaign data that could not be read.", exception);
        }
    }

    private List<CampaignResult.CampaignImage> generateImages(JsonNode directions) {
        List<CampaignResult.CampaignImage> images = new ArrayList<>();
        directions.forEach(direction -> {
            String title = direction.path("title").asText();
            String prompt = direction.path("prompt").asText();
            try {
                JsonNode response = postResponse(Map.of(
                        "model", properties.imageModel(),
                        "store", false,
                        "input", "Create this campaign key visual. Do not render logos or text. " + prompt,
                        "tools", List.of(Map.of(
                                "type", "image_generation",
                                "quality", properties.imageQuality(),
                                "size", properties.imageSize()
                        )),
                        "tool_choice", Map.of("type", "image_generation")
                ));
                String base64 = response.path("output").findValues("result").stream()
                        .map(JsonNode::asText)
                        .filter(value -> !value.isBlank())
                        .findFirst()
                        .orElseThrow(() -> new CampaignGenerationException("No image was returned."));
                images.add(new CampaignResult.CampaignImage(
                        title, prompt, "data:image/png;base64," + base64, null));
            } catch (CampaignGenerationException exception) {
                images.add(new CampaignResult.CampaignImage(title, prompt, null, exception.getMessage()));
            }
        });
        return images;
    }

    private JsonNode postResponse(Map<String, Object> payload) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(properties.baseUrl() + "/responses"))
                    .timeout(Duration.ofMinutes(3))
                    .header("Authorization", "Bearer " + properties.apiKey())
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(mapper.writeValueAsString(payload)))
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode body = mapper.readTree(response.body());
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new CampaignGenerationException(
                        body.path("error").path("message").asText("OpenAI request failed."));
            }
            return body;
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new CampaignGenerationException("Campaign generation was interrupted.", exception);
        } catch (IOException exception) {
            throw new CampaignGenerationException("Could not reach OpenAI. Please try again.", exception);
        }
    }

    private String briefInput(CampaignRequest request) {
        return """
                Campaign brief:
                %s

                Target audience:
                %s

                Product details:
                %s

                Tone:
                %s

                Desired channels:
                %s
                """.formatted(
                request.brief().trim(),
                request.audience().trim(),
                request.productDetails().trim(),
                request.tone().trim(),
                String.join(", ", request.channels())
        );
    }

    private Map<String, Object> campaignSchema() {
        Map<String, Object> string = Map.of("type", "string");
        Map<String, Object> copyVariant = objectSchema(
                Map.of("label", string, "headline", string, "body", string, "channelFit", string),
                List.of("label", "headline", "body", "channelFit"));
        Map<String, Object> checklistItem = objectSchema(
                Map.of("phase", string, "task", string),
                List.of("phase", "task"));
        Map<String, Object> imageDirection = objectSchema(
                Map.of("title", string, "prompt", string),
                List.of("title", "prompt"));

        return Map.of(
                "type", "json_schema",
                "name", "campaign_studio_result",
                "strict", true,
                "schema", objectSchema(
                        Map.of(
                                "conceptName", string,
                                "concept", string,
                                "strategicAngle", string,
                                "copyVariants", arraySchema(copyVariant, 3, 3),
                                "launchChecklist", arraySchema(checklistItem, 6, 10),
                                "imageDirections", arraySchema(imageDirection, 2, 2)
                        ),
                        List.of("conceptName", "concept", "strategicAngle", "copyVariants",
                                "launchChecklist", "imageDirections"))
        );
    }

    private Map<String, Object> objectSchema(Map<String, Object> properties, List<String> required) {
        return Map.of(
                "type", "object",
                "additionalProperties", false,
                "properties", properties,
                "required", required
        );
    }

    private Map<String, Object> arraySchema(Map<String, Object> items, int minimum, int maximum) {
        return Map.of("type", "array", "minItems", minimum, "maxItems", maximum, "items", items);
    }

    private void requireApiKey() {
        if (properties.apiKey() == null || properties.apiKey().isBlank()) {
            throw new CampaignGenerationException(
                    "OPENAI_API_KEY is not configured on the server. Add it and restart the application.");
        }
    }
}
