package cz.magix.maarifa.simple.model.object;

import javax.annotation.Nonnegative;

import org.springframework.data.neo4j.annotation.NodeEntity;

import cz.magix.maarifa.simple.model.AbstractObject;
import cz.magix.maarifa.simple.ui.annotation.UiParams;

@NodeEntity
public class Address extends AbstractObject {
	@UiParams(position=1)
	private String country;

	@UiParams(position=2)
	private String street;
	@UiParams(position=3)
	@Nonnegative
	private int descriptioningNumber;
	@UiParams(position=4)
	@Nonnegative
	private int orientationNumber;
	
	@UiParams(position=5)
	private String city;
	@UiParams(position=6)
	private String state;
	
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
