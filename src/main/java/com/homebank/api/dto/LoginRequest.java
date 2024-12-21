package com.homebank.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String username; // USERNAME VARCHAR(32)
    private String passwd;   // PASSWD_ VARCHAR(32)
    private String remoteIp; // REMOTE_IP VARCHAR(15)
}