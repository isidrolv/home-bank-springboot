package com.homebank.api.campaign;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CampaignRequestValidationTests {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void acceptsACompleteBrief() {
        CampaignRequest request = new CampaignRequest(
                "Launch a summer membership.",
                "Urban professionals",
                "Flexible classes and guest passes",
                "Warm and optimistic",
                List.of("Instagram", "Email")
        );

        assertThat(validator.validate(request)).isEmpty();
    }

    @Test
    void rejectsMissingCampaignInputs() {
        CampaignRequest request = new CampaignRequest("", "", "", "", List.of());

        assertThat(validator.validate(request)).hasSize(5);
    }
}
