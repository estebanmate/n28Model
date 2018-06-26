package com.cora.token.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cora.token.exception.InvalidAuthenticationTokenException;
import com.cora.token.model.CoraTokenDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * Service which provides operations for authentication tokens.
 *
 */
@Service
public class CoraTokenService {

    /**
     * Secret for signing and verifying the token signature.
     */
    @Value("${authentication.jwt.secret}")
    private String secret;

    /**
     * Allowed clock skew for verifying the token signature (in seconds).
     */
    @Value("${authentication.jwt.clockSkew}")
    private String clockSkew;

    /**
     * Identifies the recipients that the JWT token is intended for.
     */
    @Value("${authentication.jwt.audience}")
    private String audience;

    /**
     * Identifies the JWT token issuer.
     */
    @Value("${authentication.jwt.issuer}")
    private String issuer;

    /**
     * JWT claim for the plattform.
     */
    @Value("${authentication.jwt.claimNames.plattform}")
    private String plattformClaimName;
    
    /**
     * How String the token is valid for (in seconds).
     */
    @Value("${authentication.jwt.validFor}")
    private String validFor;

    /**
     * Issue a token for a user with the given authorities.
     *
     * @param username
     * @param authorities
     * @return
     */
    public String getToken(String username, String plattform) {

        String id = generateTokenIdentifier();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        CoraTokenDetails authenticationTokenDetails = new CoraTokenDetails.Builder()
                .withId(id)
                .withUsername(username)
                .withPlattform(plattform)
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .build();

        return tokenGeneration(authenticationTokenDetails);
    }

    public String tokenGeneration(CoraTokenDetails authenticationTokenDetails) {

        return Jwts.builder()
                .setId(authenticationTokenDetails.getId())
                .setIssuer(issuer)
                .setAudience(audience)
                .setSubject(authenticationTokenDetails.getUsername())
                .setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
                .claim(plattformClaimName, authenticationTokenDetails.getPlattform())
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    
    public CoraTokenDetails tokenValidation(String token) {

        try {

            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .requireAudience(audience)
                    .setAllowedClockSkewSeconds(Long.valueOf(clockSkew))
                    .parseClaimsJws(token)
                    .getBody();

            return new CoraTokenDetails.Builder()
                    .withId(extractTokenIdFromClaims(claims))
                    .withUsername(extractUsernameFromClaims(claims))
                    .withPlattform(extractPlattformFromClaims(claims))
                    .withIssuedDate(extractIssuedDateFromClaims(claims))
                    .withExpirationDate(extractExpirationDateFromClaims(claims))
                    .build();

        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException e) {
            throw new InvalidAuthenticationTokenException("Token invalido", e);
        } catch (ExpiredJwtException e) {
            throw new InvalidAuthenticationTokenException("Token expirado", e);
        } catch (InvalidClaimException e) {
            throw new InvalidAuthenticationTokenException("Valor invalido para dato \"" + e.getClaimName() + "\"", e);
        } catch (Exception e) {
            throw new InvalidAuthenticationTokenException("Token invalido", e);
        }
    }

    /**
     * Extract the token identifier from the token claims.
     *
     * @param claims
     * @return Identifier of the JWT token
     */
    private String extractTokenIdFromClaims(@NotNull Claims claims) {
        return (String) claims.get(Claims.ID);
    }

    /**
     * Extract the username from the token claims.
     *
     * @param claims
     * @return Username from the JWT token
     */
    private String extractUsernameFromClaims(@NotNull Claims claims) {
        return claims.getSubject();
    }

    /**
     * Extract the plattform identifier from the token claims.
     *
     * @param claims
     * @return Identifier of the JWT token
     */
    private String extractPlattformFromClaims(@NotNull Claims claims) {
        return (String) claims.get(plattformClaimName);
    }
    
    /**
     * Extract the issued date from the token claims.
     *
     * @param claims
     * @return Issued date of the JWT token
     */
    private ZonedDateTime extractIssuedDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getIssuedAt().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Extract the expiration date from the token claims.
     *
     * @param claims
     * @return Expiration date of the JWT token
     */
    private ZonedDateTime extractExpirationDateFromClaims(@NotNull Claims claims) {
        return ZonedDateTime.ofInstant(claims.getExpiration().toInstant(), ZoneId.systemDefault());
    }

    /**
     * Calculate the expiration date for a token.
     *
     * @param issuedDate
     * @return
     */
    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(Long.valueOf(validFor));
    }

    /**
     * Generate a token identifier.
     *
     * @return
     */
    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
    
    
}