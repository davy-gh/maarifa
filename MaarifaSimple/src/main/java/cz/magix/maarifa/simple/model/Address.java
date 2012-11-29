package cz.magix.maarifa.simple.model;

public class Address extends AbstractObject {
	private String street;
	private int descriptioningNumber;
	private int orientationNumber;
	
	private String city;
	private String state;
	private String country;
	
	/*
	 * Getters & Setters
	 */
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public int getDescriptioningNumber() {
		return descriptioningNumber;
	}
	public void setDescriptioningNumber(int descriptioningNumber) {
		this.descriptioningNumber = descriptioningNumber;
	}
	public int getOrientationNumber() {
		return orientationNumber;
	}
	public void setOrientationNumber(int orientationNumber) {
		this.orientationNumber = orientationNumber;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}	
}
