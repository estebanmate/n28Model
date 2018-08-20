package es.bankia.n28.cifrado.model;

/**
 * API model for the request token.
 *
 */
public class N28TOKEN_REQUEST {

    private String token;

    public N28TOKEN_REQUEST() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}