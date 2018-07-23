package es.bankia.n28.model;

import org.springframework.stereotype.Component;

@Component
public class N28TokenResponse {

	   private String token;

	    public N28TokenResponse() {
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }
}