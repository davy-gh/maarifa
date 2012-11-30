package cz.magix.maarifa.simple.model.relation;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.simple.model.Address;
import cz.magix.maarifa.simple.model.Person;

@RelationshipEntity
public class LiveOn extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	Address address;
}
