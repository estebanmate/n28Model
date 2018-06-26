package com.cora.token.model;

import java.time.ZonedDateTime;

/**
 * Modelo de construcción del Token
 *
 */
public final class CoraTokenDetails {

    private final String id;
    private final String username;
    private final String plattform;
    private final ZonedDateTime issuedDate;
    private final ZonedDateTime expirationDate;

    private CoraTokenDetails(String id, String username, String plattform, ZonedDateTime issuedDate, ZonedDateTime expirationDate) {
        this.id = id;
        this.username = username;
        this.plattform = plattform;
        this.issuedDate = issuedDate;
        this.expirationDate = expirationDate;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPlattform() {
		return plattform;
	}

	public ZonedDateTime getIssuedDate() {
        return issuedDate;
    }

    public ZonedDateTime getExpirationDate() {
        return expirationDate;
    }

    /**
     * Builder for the {@link CoraTokenDetails}.
     */
    public static class Builder {

        private String id;
        private String username;
        private String plattform;
        private ZonedDateTime issuedDate;
        private ZonedDateTime expirationDate;

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withPlattform(String plattform) {
            this.plattform = plattform;
            return this;
        }

        public Builder withIssuedDate(ZonedDateTime issuedDate) {
            this.issuedDate = issuedDate;
            return this;
        }

        public Builder withExpirationDate(ZonedDateTime expirationDate) {
            this.expirationDate = expirationDate;
            return this;
        }

        public CoraTokenDetails build() {
            return new CoraTokenDetails(id, username, plattform, issuedDate, expirationDate);
        }
    }
}