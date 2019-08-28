package com.loyaltypartner.vendocustomers.model;

import java.io.Serializable;

public class SoapUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3126238242909311699L;
	
	private String username;
	private String source;
	
	public SoapUser() {
		
	}
	
	public SoapUser(String username, String source) {
		this.username = username;
		this.source = source;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
}
