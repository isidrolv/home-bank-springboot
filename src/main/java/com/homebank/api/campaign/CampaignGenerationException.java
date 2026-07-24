package com.homebank.api.campaign;

public class CampaignGenerationException extends RuntimeException {
    public CampaignGenerationException(String message) {
        super(message);
    }

    public CampaignGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
