package com.example.geosii.parser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
	
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("MensajeID")
	private String idMessage = null;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("TpoAccion")
	private String tpoAction = null;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("idAlerta")
	private String idAlert = null;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("tituloMsg")
	private String tittleMessage = null;
	@JsonIgnoreProperties (ignoreUnknown=true)
	@JsonProperty("txtMsg")
	private String txtMsg = null;
	private Long idStatus = null;
	private Integer countIdMessage = null;
	
	public String getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}
	public String getTpoAction() {
		return tpoAction;
	}
	public void setTpoAction(String tpoAction) {
		this.tpoAction = tpoAction;
	}
	public String getIdAlert() {
		return idAlert;
	}
	public void setIdAlert(String idAlert) {
		this.idAlert = idAlert;
	}
	public String getTittleMessage() {
		return tittleMessage;
	}
	public void setTittleMessage(String tittleMessage) {
		this.tittleMessage = tittleMessage;
	}
	public String getTxtMsg() {
		return txtMsg;
	}
	public void setTxtMsg(String txtMsg) {
		this.txtMsg = txtMsg;
	}
	public Long getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}
	public Integer getCountIdMessage() {
		return countIdMessage;
	}
	public void setCountIdMessage(Integer countIdMessage) {
		this.countIdMessage = countIdMessage;
	}

}
