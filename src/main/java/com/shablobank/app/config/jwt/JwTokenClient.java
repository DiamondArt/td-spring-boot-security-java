package com.shablobank.app.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Description;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwTokenClient implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    private static final long SECONDE = 1000;
    private static final long MINUTE = SECONDE * 60;
    private static final long HEURE = 60 * MINUTE;
    public static final long JWT_TOKEN_VALIDITY = 24 * HEURE;
    @Value("${jwt.secret.key}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    @Description("Here you specify tokens, for this expiration is ignored")
    private Boolean ignoreTokenExpiration(String token) {
        return false;
    }

    public String generateJwtToken(JwtUserDetailsImpl userDetails) {
        Claims claims = userDetails.getClaims();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Claims claims, String subject) {
        SignatureAlgorithm alg = SignatureAlgorithm.HS512;
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setHeaderParam("alg", alg.getValue())
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(alg, secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token) {
        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private org.slf4j.Logger getLogger() {
        return org.slf4j.LoggerFactory.getLogger(this.getClass().getSimpleName());
    }
}
