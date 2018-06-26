package com.cora.token.model;

import org.springframework.beans.factory.annotation.Value;

public class CoraTokenSettings {

    /**
     * Secret for signing and verifying the token signature.
     */
    @Value("authentication.jwt.secret")
    private String secret;

    /**
     * Allowed clock skew for verifying the token signature (in seconds).
     */
    @Value("authentication.jwt.clockSkew")
    private Long clockSkew;

    /**
     * Identifies the recipients that the JWT token is intended for.
     */
    @Value("authentication.jwt.audience")
    private String audience;

    /**
     * Identifies the JWT token issuer.
     */
    @Value("authentication.jwt.issuer")
    private String issuer;

    /**
     * JWT claim for the plattform.
     */
    @Value("authentication.jwt.claimNames.plattform")
    private String plattformClaimName = "plattform";
    
    /**
     * How long the token is valid for (in seconds).
     */
    @Value("authentication.jwt.validFor")
    private Long validFor;
    

	public void setValidFor(Long validFor) {
		this.validFor = validFor;
	}

	public String getSecret() {
        return secret;
    }

    public Long getClockSkew() {
        return clockSkew;
    }

    public String getAudience() {
        return audience;
    }

    public String getIssuer() {
        return issuer;
    }
    public String getPlattformClaimName() {
        return plattformClaimName;
    }

    public Long getValidFor() {
		return validFor;
	}
    
}
