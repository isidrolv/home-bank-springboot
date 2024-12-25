package com.homebank.api.service;

import com.homebank.api.dto.LoginResponse;
import com.homebank.api.dto.SessionInfo;
import com.homebank.api.dto.UserInfo;
import com.homebank.api.entity.UserSession;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

@Component
public class SessionMapper {
    @Value("${jwt.secret.key}")
    private String strSecretKey;

    public UserSession map(LoginResponse login, UserInfo userInfo, SessionInfo sessionInfo) {
        userInfo.setPasswd(saltString(userInfo.getPasswd()));
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return UserSession.builder()
                .usuarioId(login.getUsuarioId())
                .userInfo(userInfo)
                .sessionInfo(sessionInfo)
                .isLoggedIn(login.getSesionId() > 0)
                .sessionId(login.getSesionId())
                .date(sessionInfo.getFecha() + "")
                .token(generateToken(userInfo, sessionInfo))
                .tokenExpiryTime(sessionInfo.getHoraInicio().plusMinutes(30).format(dtf))
                .loginTime(sessionInfo.getHoraInicio().format(dtf))
                .isLoggedIn(login.getSesionId() > 0)
                .remoteIp(sessionInfo.getRemoteIp())
                .ok(login.getOk())
                .message(login.getMsg())
                .build();
    }

    private String saltString(String passwd) {
        return Base64.getEncoder()
                .encodeToString(passwd.getBytes());

    }

    private String generateToken(UserInfo userInfo, SessionInfo sessionInfo) {
        byte[] secretBytes = strSecretKey.getBytes();
        return Jwts.builder()
                .setSubject(userInfo.getUsername())
                .claim("userId", userInfo.getUsuarioId())
                .claim("sessionId", sessionInfo.getSesionId())
                .setIssuedAt(convertToDate(sessionInfo.getFecha()))
                .setExpiration(convertToDate(sessionInfo.getFecha().plusDays(1))) // Token valid for 1 day
                .signWith(Keys.hmacShaKeyFor(secretBytes), SignatureAlgorithm.HS512)
                .compact();
    }

    private static Date convertToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
    }

}
