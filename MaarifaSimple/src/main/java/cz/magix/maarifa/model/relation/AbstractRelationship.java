package cz.magix.maarifa.model.relation;

import java.util.Date;

public abstract class AbstractRelationship {
	protected Class<? extends AbstractRelationship> pairRelationship;
	
	private Date validFrom;
	
	private Date validTo;

	/*
	 * Getters & Setters
	 */
	public Date getValidFrom() {
		return validFrom;
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
