package es.bankia.n28.cifrado.model;

import org.springframework.stereotype.Component;

@Component
public class N28TOKEN_REPLY {

	   private String token;

	    public N28TOKEN_REPLY() {
	    }

	    public String getToken() {
	        return token;
	    }

	    public void setToken(String token) {
	        this.token = token;
	    }
}