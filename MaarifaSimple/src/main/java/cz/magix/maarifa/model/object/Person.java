package cz.magix.maarifa.model.object;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;

import cz.magix.maarifa.model.constants.Country;
import cz.magix.maarifa.ui.annotation.UiParams;

@NodeEntity
public class Person extends AbstractObject {

	@UiParams(position = 1)
	private Country nationality;

	// TODO: implements this
	// @NoNewLineAfter
	@UiParams(position = 2)
	private String[] honorificPrefix;

	@NotNull
	@UiParams(position = 3)
	// @NoNewLineAfter
	@Indexed
	private String firstName;

	@NotNull
	@UiParams(position = 4)
	// @NoNewLineAfter
	@Indexed
	private String lastName;

	@UiParams(position = 5)
	private String[] honorificSuffix;

	@UiParams(position = 6)
	// TODO: implement this
	// @UiShowIf(gender=FEMALE)
	private String birthLastName;

	@UiParams(position = 7)
	private String[] additionalName;

	@UiParams(position = 8)
	private String[] aliasName;

	@UiParams(position = 9)
	private String birthNumber;

	@UiParams(position = 10)
	private Date birthDate;

	@UiParams(position = 11)
	private Date deathDate;

	@UiParams(position = 12)
	private Gender gender;

	@UiParams(position = 13)
	private FamilyStatus familyStatus;

	/*
	 * Getters & Setters
	 */
	public String[] getHonorificPrefix() {
		return honorificPrefix;
	}

	public void setHonorificPrefix(String[] honorificPrefix) {
		this.honorificPrefix = honorificPrefix;
	}

	public String[] getHonorificSuffix() {
		return honorificSuffix;
	}

	public void setHonorificSuffix(String[] honorificSuffix) {
		this.honorificSuffix = honorificSuffix;
	}

	public String getBirthLastName() {
		return birthLastName;
	}

	public void setBirthLastName(String birthLastName) {
		this.birthLastName = birthLastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public FamilyStatus getFamilyStatus() {
		return familyStatus;
	}

	public void setFamilyStatus(FamilyStatus familyStatus) {
		this.familyStatus = familyStatus;
	}

	public Country getNationality() {
		return nationality;
	}

	public void setNationality(Country nationality) {
		this.nationality = nationality;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public String getBirthNumber() {
		return birthNumber;
	}

	public void setBirthNumber(String birthNumber) {
		this.birthNumber = birthNumber;
	}

	public String[] getAdditionalName() {
		return additionalName;
	}

	public void setAdditionalName(String[] additionalName) {
		this.additionalName = additionalName;
	}

	public String[] getAliasName() {
		return aliasName;
	}

	public void setAliasName(String[] aliasName) {
		this.aliasName = aliasName;
	}

	/*
	 * Enums and inner classes
	 */
	public enum Gender {
		MALE, FEMALE;
	}

	public enum FamilyStatus {
		SINGLE, ENGAGED, MARRIED, PARTNERSHIP, DIVORCED, WIDOWED;
	}
}
