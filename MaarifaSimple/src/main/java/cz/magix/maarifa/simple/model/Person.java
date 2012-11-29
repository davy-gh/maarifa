package cz.magix.maarifa.simple.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.neo4j.annotation.NodeEntity;

import cz.magix.maarifa.simple.ui.annotation.UiParams;

@NodeEntity
public class Person extends AbstractObject {
	@NotNull
	@UiParams(position=1)
	private String firstName;
	@UiParams(position=2)
	private String middleName;
	@UiParams(position=3)
	private String lastName;
	
	@UiParams(position=4)
	private Date dateOfBirth;
	@UiParams(position=5)
	private String birthNumber;
	
	/*
	 * Getters & Setters
	 */
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getBirthNumber() {
		return birthNumber;
	}
	public void setBirthNumber(String birthNumber) {
		this.birthNumber = birthNumber;
	}
}
