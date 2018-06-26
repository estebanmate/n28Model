package com.cora.token.model;

import org.springframework.stereotype.Component;

/**
 * Credenciales de usuario enviadas para generar token (usuario y plataforma de origen).
 *
 */
@Component
public class UserCredentials {

    private String username;
    private String plattform;
    private String email;
    private String smsNumber;

    public UserCredentials() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getPlattform() {
		return plattform;
	}

	public void setPlattform(String plattform) {
		this.plattform = plattform;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSmsNumber() {
		return smsNumber;
	}

	public void setSmsNumber(String smsNumber) {
		this.smsNumber = smsNumber;
	}
	

}