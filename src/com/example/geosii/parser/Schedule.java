package com.example.geosii.parser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Schedule {

	@JsonProperty("Dia")
	private String day = null;
	@JsonProperty("Inicio")
	private String start = null;
	@JsonProperty("Fin")
	private String end = null;
	
	public String getDia() {
		return day;
	}
	public void setDia(String day) {
		this.day = day;
	}
	public String getInicio() {
		return start;
	}
	public void setInicio(String start) {
		this.start = start;
	}
	public String getFin() {
		return end;
	}
	public void setFin(String end) {
		this.end = end;
	}
	
}
