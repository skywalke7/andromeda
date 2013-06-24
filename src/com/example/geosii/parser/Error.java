package com.example.geosii.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

	@JsonProperty("No")
	private String no;
	@JsonProperty("Descripcion")
	private String description;
	
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
