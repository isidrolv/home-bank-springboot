package com.homebank.api.service;

import com.homebank.api.dao.LoginDAO;
import com.homebank.api.dao.SessionDAO;
import com.homebank.api.dao.UserDAO;
import com.homebank.api.dto.LoginRequest;
import com.homebank.api.dto.LoginResponse;
import com.homebank.api.dto.SessionInfo;
import com.homebank.api.dto.UserInfo;
import com.homebank.api.entity.UserSession;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Optional;

@Service
public class LoginService {
    public static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    public static final int TOKEN_LENGTH = 32;
    private final LoginDAO loginDAO;
    private final UserDAO userDAO; // Added UserDAO dependency
    private final SessionDAO sessionDAO; // Added SessionDAO dependency

    public LoginService(LoginDAO loginDAO, UserDAO userDAO, SessionDAO sessionDAO) {
        this.loginDAO = loginDAO;
        this.userDAO = userDAO; // Assign UserDAO
        this.sessionDAO = sessionDAO; // Assign SessionDAO
    }

    public String generateToken() {
        SecureRandom random = new SecureRandom();
        StringBuilder token = new StringBuilder(TOKEN_LENGTH);

        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            token.append(CHARACTERS.charAt(index));
        }

        return token.toString();
    }

    public UserSession getUserSession(LoginRequest loginRequest) throws SQLException {
        SessionMapper mapper = new SessionMapper();

        Optional<LoginResponse> optionalLoginResponse = loginDAO.login(loginRequest);
        LoginResponse loginInfo = optionalLoginResponse.orElseGet(() -> LoginResponse.builder().build());
        if (loginInfo.getOk() == 0) {
            Optional<UserInfo> optionalUserInfo = userDAO.getUserInfo(loginInfo.getUsuarioId());
            UserInfo userInfo = optionalUserInfo.orElseGet(() -> UserInfo.builder().build());
            Optional<SessionInfo> optionalSessionInfo = sessionDAO.getSessionInfo(loginInfo.getSesionId());
            SessionInfo sessionInfo = optionalSessionInfo.orElseGet(() -> SessionInfo.builder().build());
            return mapper.map(loginInfo, userInfo, sessionInfo);
        } else {
            throw new SQLException(loginInfo.getMsg());
        }
    }

}