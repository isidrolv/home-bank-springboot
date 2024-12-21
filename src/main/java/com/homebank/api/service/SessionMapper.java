package com.homebank.api.service;

import com.homebank.api.dto.LoginResponse;
import com.homebank.api.dto.SessionInfo;
import com.homebank.api.dto.UserInfo;
import com.homebank.api.entity.UserSession;

public class SessionMapper {
    public UserSession map(LoginResponse login, UserInfo userInfo, SessionInfo sessionInfo) {
        return UserSession.builder()
                .username(userInfo.getUsername())
                .date(sessionInfo.getFecha() + "")
                .token("@")
                .isLoggedIn(login.getSesionId() > 0)
                .build();
    }

}
