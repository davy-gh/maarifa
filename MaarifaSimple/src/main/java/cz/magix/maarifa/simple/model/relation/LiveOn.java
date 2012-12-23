package cz.magix.maarifa.simple.model.relation;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.simple.model.object.Address;
import cz.magix.maarifa.simple.model.object.Person;

@RelationshipEntity
public class LiveOn extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	Address address;
}
