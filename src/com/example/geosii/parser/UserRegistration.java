package com.example.geosii.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistration {
	
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("Error")
	private com.example.geosii.parser.Error error;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("Datos")
	private OutputDataRegister OutputDataRegister;
	
	public com.example.geosii.parser.Error getError() {
		return error;
	}
	public void setError(com.example.geosii.parser.Error error) {
		this.error = error;
	}
	public OutputDataRegister getOutputDataRegister() {
		return OutputDataRegister;
	}
	public void setOutputDataRegister(OutputDataRegister outputDataRegister) {
		OutputDataRegister = outputDataRegister;
	}
	
	

}
