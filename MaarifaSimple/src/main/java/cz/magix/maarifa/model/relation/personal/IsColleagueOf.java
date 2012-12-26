package cz.magix.maarifa.model.relation.personal;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.model.object.Person;

@RelationshipEntity
public class IsColleagueOf extends AbstractRelationship {
	@StartNode
	Person colleagueA;
	
	@EndNode
	Person colleagueB;
	
	public IsColleagueOf() {
		setPairRelationship(IsColleagueOf.class);
	}
}