package cz.magix.maarifa.model.relation.organizational;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.model.object.Person;
import cz.magix.maarifa.model.object.organization.EducationalOrganization;

@RelationshipEntity
public class AlumniOf extends AbstractRelationship {
	@StartNode
	Person person;
	
	@EndNode
	EducationalOrganization educationalOrganization;
}
