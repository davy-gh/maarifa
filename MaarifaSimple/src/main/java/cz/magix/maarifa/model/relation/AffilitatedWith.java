package cz.magix.maarifa.model.relation;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.object.Organization;
import cz.magix.maarifa.model.object.Person;

@RelationshipEntity
public class AffilitatedWith extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	Organization organization;
}
