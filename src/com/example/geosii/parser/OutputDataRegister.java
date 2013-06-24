package com.example.geosii.parser;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OutputDataRegister {

	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("IDUsuario")
	private String idUser;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("IDDispositivo")
	private String idDevice;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("Nombre")
	private String Name;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("Horarios")
	private List<Schedule> schedule;
	
	public String getIdUser() {
		return idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	public String getIdDevice() {
		return idDevice;
	}
	public void setIdDevice(String idDevice) {
		this.idDevice = idDevice;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<Schedule> getSchedule() {
		return schedule;
	}
	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	
}
