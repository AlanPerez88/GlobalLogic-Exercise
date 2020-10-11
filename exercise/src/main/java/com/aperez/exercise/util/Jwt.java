package com.aperez.exercise.util;

import com.aperez.exercise.dto.UserDto;
import com.aperez.exercise.exception.JwtException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static java.time.ZoneOffset.UTC;

@Slf4j
@Component
public class Jwt {

    @Value("${jwt.expirationtime}")
    private Long expirationTime;

    byte[] jwtSecretKey = new byte[64];

    public static final String ISSUER = "com.aperez.exercise";


    public String makeToken(String subject, Map<String, Object> claims) {
        String result = "";
        Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(expirationTime).toInstant(UTC));
        result = Jwts.builder().
                setClaims(claims).
                setSubject(subject).
                setExpiration(expiration).
                setIssuer(ISSUER).
                signWith(SignatureAlgorithm.HS256, jwtSecretKey).
                compact();
        return result;
    }


    public void validateToken(String authorization, UserDto userDto) throws JwtException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(authorization);
        String issuer = claims.getBody().getIssuer();
        String subject = claims.getBody().getSubject();
        Object password = claims.getBody().get("password");
        log.debug("verify user: " + subject);
        if (!ISSUER.equals(issuer) || !subject.equals(userDto.getEmail()) || !password.equals(userDto.getPassword())) {
            throw new JwtException("Token invalido");
        }
    }


}
