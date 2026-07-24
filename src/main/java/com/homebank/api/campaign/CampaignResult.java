package com.homebank.api.campaign;

import java.util.List;

public record CampaignResult(
        String conceptName,
        String concept,
        String strategicAngle,
        List<CopyVariant> copyVariants,
        List<ChecklistItem> launchChecklist,
        List<CampaignImage> images
) {
    public record CopyVariant(String label, String headline, String body, String channelFit) {}
    public record ChecklistItem(String phase, String task) {}
    public record CampaignImage(String title, String prompt, String dataUrl, String error) {}
}
