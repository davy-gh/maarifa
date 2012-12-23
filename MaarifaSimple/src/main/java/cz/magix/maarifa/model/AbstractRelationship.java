package cz.magix.maarifa.model;

import java.util.Date;

public abstract class AbstractRelationship {
	private Class<? extends AbstractRelationship> pairRelationship;
	
	private Date validFrom;
	
	private Date validTo;

	/*
	 * Getters & Setters
	 */
	public Date getValidFrom() {
		return validFrom;
	}

	public Class<? extends AbstractRelationship> getPairRelationship() {
		return pairRelationship;
	}

	public void setPairRelationship(Class<? extends AbstractRelationship> pairRelationship) {
		this.pairRelationship = pairRelationship;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidTo() {
		return validTo;
	}

	public void setValidTo(Date validTo) {
		this.validTo = validTo;
	}	
}
