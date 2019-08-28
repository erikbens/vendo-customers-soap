package com.loyaltypartner.vendocustomers.model;

import java.io.Serializable;
import java.util.Date;

public class KtoCustomer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6737501408339981480L;
	
	private Long id;
	private String lastName;
	private String firstName;
	private Date birthDate;
	
	public KtoCustomer() {
	}

	public KtoCustomer(String lastName, String firstName, Date birthDate) {
		super();
		this.lastName = lastName;
		this.firstName = firstName;
		this.birthDate = birthDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Override
	public String toString() {
		return "KtoCustomer [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", birthDate=" + birthDate + "]";
	}
	
}
