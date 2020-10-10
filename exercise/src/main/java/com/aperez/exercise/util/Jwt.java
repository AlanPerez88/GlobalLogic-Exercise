package com.aperez.exercise.util;

import com.aperez.exercise.exception.JwtException;
import com.aperez.exercise.exception.UserException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

import static java.time.ZoneOffset.UTC;

@Slf4j
@Component
public class Jwt {
    @Value("${jwt.secretkey}")
    private String jwtSecretKey;

    @Value("${jwt.expirationtime}")
    private Long expirationTime;

    public static final String ISSUER = "com.aperez.exercise";

    public String generateJwt(String user, String pass) {
        log.info("Generando jwt para usuario: {}", user);

        String jwt = Jwts.builder().setId(user)
                .setSubject(pass)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expirationTime)))
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(jwtSecretKey)).compact();

        return jwt;
    }

    public Claims getClaimsFromJwt(String jwt) throws JwtException {

            Claims claims = Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.encode(jwtSecretKey))
                    .parseClaimsJws(jwt)
                    .getBody();
       if(claims == null)
           throw new JwtException(2, "Token invalido");
        return claims;
    }

    public boolean isExpired(Date input) {
        return System.currentTimeMillis() > input.getTime();
    }

    public String makeToken(String userName, Map<String, Object> dataToEncripted) {
        String result = "";
        Date expiration = Date.from(LocalDateTime.now(UTC).plusMinutes(expirationTime).toInstant(UTC));
        result = Jwts.builder().
                setClaims(dataToEncripted).
                setSubject(userName).
                        setExpiration(expiration).
                        setIssuer(ISSUER).
                        signWith(SignatureAlgorithm.HS512, jwtSecretKey).
                        compact();
        return result;
    }
}
