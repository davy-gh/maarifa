package cz.magix.maarifa.model.relation;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.model.object.Person;
import cz.magix.maarifa.model.object.contact.PostalAddress;

@RelationshipEntity
public class Located extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	PostalAddress address;
	
	private Location location;
	
	/*
	 * Getters & Setters
	 */
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PostalAddress getAddress() {
		return address;
	}

	public void setAddress(PostalAddress address) {
		this.address = address;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public enum Location {
		HOME, WORK, OTHER;
	}
}
