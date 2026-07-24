package com.homebank.api.campaign;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

public record CampaignRequest(
        @NotBlank @Size(max = 1200) String brief,
        @NotBlank @Size(max = 600) String audience,
        @NotBlank @Size(max = 900) String productDetails,
        @NotBlank @Size(max = 120) String tone,
        @NotEmpty @Size(max = 8) List<@NotBlank @Size(max = 60) String> channels
) {
}
