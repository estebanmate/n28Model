package es.bankia.n28.model;

/**
 * API model for the authentication token.
 *
 */
public class N28TokenRequest {

    private String token;

    public N28TokenRequest() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}