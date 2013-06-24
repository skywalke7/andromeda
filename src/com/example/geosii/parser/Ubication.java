package com.example.geosii.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ubication {
	
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("Error")
	private Error error = null ;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("DatosSalida")
	private OutputDataLocation outputDataLocation = null;
	
	public Error getError() {
		return error;
	}
	public void setError(Error error) {
		this.error = error;
	}
	public OutputDataLocation getOutputDataLocation() {
		return outputDataLocation;
	}
	public void setOutputDataLocation(OutputDataLocation outputDataLocation) {
		this.outputDataLocation = outputDataLocation;
	}
	
	

}
