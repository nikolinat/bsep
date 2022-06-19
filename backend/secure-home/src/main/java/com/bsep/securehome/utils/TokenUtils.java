package com.bsep.securehome.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TokenUtils {
    @Value("admin-bsep")
    private String APP_NAME;

    @Value("somesecret")
    public String SECRET;

    @Value("3600000")
    private int EXPIRES_IN;

    @Value("Authorization")
    private String AUTH_HEADER;

    private static final String AUDIENCE_WEB = "web";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_TABLET = "tablet";

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    private final SecureRandom secureRandom = new SecureRandom();

    public String generateToken(String username, String role, Integer id, String cookieSecureContent) {
        String secureContentHash = generateSecureContentHash(cookieSecureContent);
        return Jwts.builder()
                .claim("role", role)
                .claim("id", id)
                .claim("cookieSecureContent", secureContentHash)
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setAudience(generateAudience())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    public String generateCookieContent() {
        byte[] randomContent = new byte[50];
        this.secureRandom.nextBytes(randomContent);
        return DatatypeConverter.printHexBinary(randomContent);
    }

    private String generateAudience() {
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + EXPIRES_IN);
    }

    public Boolean validateToken(String token, UserDetails userDetails, String secureContent) {
        String algorithm = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getHeader().getAlgorithm();
        final String username = getUsernameFromToken(token);

        boolean isUsernameValid = username != null && username.equals(userDetails.getUsername());
        boolean isAlgorithmValid = algorithm.equals("HS512");

        boolean isSecureContentValid = false;
        if (secureContent != null) {
            isSecureContentValid = validateTokenSecureContent(secureContent, token);
        }
        //Zakomentarisi posle
        isSecureContentValid = true;
        return isUsernameValid && isAlgorithmValid && isSecureContentValid;
    }

    private boolean validateTokenSecureContent(String secureContent, String token) {
        String secureContentHash = generateSecureContentHash(secureContent);
        String secureContentFromToken = getSecureContentFromToken(token);
        return secureContentFromToken.equals(secureContentHash);
    }

    private String generateSecureContentHash(String userFingerprint) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
            return DatatypeConverter.printHexBinary(userFingerprintDigest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getSecureContentFromToken(String token) {
        String fingerprint;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            fingerprint = claims.get("cookieSecureContent", String.class);
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            fingerprint = null;
        }
        return fingerprint;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String getSecureContentFromCookie(HttpServletRequest request) {
        String secureContent = null;
        if (request.getCookies() != null && request.getCookies().length > 0) {
            List<Cookie> cookies = Arrays.stream(request.getCookies()).collect(Collectors.toList());
            Optional<Cookie> cookie = cookies.stream().filter(c -> "SecureContent".equals(c.getName())).findFirst();

            if (cookie.isPresent()) {
                secureContent = cookie.get().getValue();
            }
        }
        return secureContent;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    public int getExpiredIn() {
        return EXPIRES_IN;
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private Boolean ignoreTokenExpiration(String token) {
        String audience = this.getAudienceFromToken(token);
        return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
}
