package com.homebank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogoutResponse {
    private Integer ok;         // OK INTEGER
    private String msg;         // MSG VARCHAR(65)
}
