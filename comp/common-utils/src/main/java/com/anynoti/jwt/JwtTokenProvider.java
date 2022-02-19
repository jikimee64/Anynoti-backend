package com.anynoti.jwt;

import com.anynoti.AuthorizationExtractor;
import com.anynoti.AuthorizationType;
import com.anynoti.enums.ProviderType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class JwtTokenProvider {
    private String secretKey;
    private long expireSeconds;
    private ObjectMapper objectMapper;

    public JwtTokenProvider(ObjectMapper objectMapper, String secretKey, long expireSeconds) {
        this.secretKey = secretKey;
        this.expireSeconds = expireSeconds;
        this.objectMapper = objectMapper;
    }

    public String createToken(String providerId, ProviderType providerType)
        throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiredAt = now.plus(expireSeconds, ChronoUnit.MINUTES);
        JwtPayloadDto jwtPayloadDto = JwtPayloadDto.builder()
            .providerId(providerId)
            .providerType(providerType)
            .build();

        Claims claims = Jwts.claims().setSubject(objectMapper.writeValueAsString(jwtPayloadDto));

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
            .setExpiration(Date.from(expiredAt.atZone(ZoneId.systemDefault()).toInstant()))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public String extractToken(HttpServletRequest request) {
        return AuthorizationExtractor.extract(request, AuthorizationType.BEARER);
    }

    public JwtPayloadDto extractJwtPayload(String jwtToken) throws JsonProcessingException {
        isValidToken(jwtToken);
        String subject = Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(jwtToken)
            .getBody()
            .getSubject();

        return objectMapper.readValue(subject, JwtPayloadDto.class);
    }

    public boolean isValidToken(String jwtToken) {
        if (!StringUtils.hasText(jwtToken)) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
                return true;
            } catch (ExpiredJwtException e) {
                log.error("Expired JWT token", e.getMessage());
            } catch (UnsupportedJwtException e) {
                log.error("Unsupported JWT token", e.getMessage());
            } catch (IllegalArgumentException e) {
                log.error("JWT claims string is empty.", e.getMessage());
            } catch (JwtException e) {
                log.error("Invalid JWT token", e.getMessage());
            }
        }
        return false;
    }

}