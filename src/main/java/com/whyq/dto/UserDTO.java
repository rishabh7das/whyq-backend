package com.whyq.dto;

public class UserDTO {
	
	private String email;
	private String name;
	private String password;
	private String phoneNumber;
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Override
	public String toString() {
		return "UserDTO [email=" + email + ", name=" + name + ", password=" + password + ", phoneNumber=" + phoneNumber
				+ "]";
	}
	
	

}
