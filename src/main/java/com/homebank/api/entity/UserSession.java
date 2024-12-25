package com.homebank.api.entity;

import com.homebank.api.dto.SessionInfo;
import com.homebank.api.dto.UserInfo;
import lombok.Builder;
import lombok.Data;

/**
 * Represents a user session in the system.
 * This class stores details of a user's session including login status,
 * authentication token, and session timing details.
 * It is designed for managing and tracking user sessions.
 *
 * Attributes:
 * - username: The username associated with the user session.
 * - password: The password associated with the user session.
 * - isLoggedIn: Indicates whether the user is currently logged in.
 * - date: The date when the user session was created or logged in.
 * - loginTime: The specific time of login for the session.
 * - token: The authentication token assigned for the session.
 * - tokenExpiryTime: The expiration time for the authentication token.
 */
@Data
@Builder
public class UserSession {
    private Integer usuarioId;
    private UserInfo userInfo;
    private SessionInfo sessionInfo;
    private boolean isLoggedIn;
    private Integer sessionId;
    private String date;
    private String loginTime;
    private String token;
    private String tokenExpiryTime;
    private String remoteIp;
    private Integer ok;
    private String message;
}
