package com.example.geosii.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationResult {
	
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("RegistroResult")
	private UserRegistration userRegistration = null;
	//Validar !!
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("UbicacionesResult")
	private Ubication ubication = null;
	
	public UserRegistration getUserRegistration() {
		return userRegistration;
	}
	public void setUserRegistration(UserRegistration userRegistration) {
		this.userRegistration = userRegistration;
	}
	public Ubication getUbication() {
		return ubication;
	}
	public void setUbication(Ubication ubication) {
		this.ubication = ubication;
	}
	
	

}
