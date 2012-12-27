package cz.magix.maarifa.model.relation.organizational;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.object.Organization;
import cz.magix.maarifa.model.object.Person;
import cz.magix.maarifa.model.relation.AbstractRelationship;

@RelationshipEntity
public class MemberOf extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	Organization organization;
}
