package com.homebank.api.campaign;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {
    private final OpenAiCampaignService campaignService;

    public CampaignController(OpenAiCampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping("/generate")
    public CampaignResult generate(@Valid @RequestBody CampaignRequest request) {
        return campaignService.generate(request);
    }

    @ExceptionHandler(CampaignGenerationException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String, String> campaignError(CampaignGenerationException exception) {
        return Map.of("message", exception.getMessage());
    }
}
