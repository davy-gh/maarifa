package cz.magix.maarifa.model.relation;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.model.object.Contact;
import cz.magix.maarifa.model.object.Person;

@RelationshipEntity
public class HasContact extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	Contact contact;
}
