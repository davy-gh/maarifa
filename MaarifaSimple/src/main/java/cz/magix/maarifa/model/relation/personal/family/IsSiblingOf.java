package cz.magix.maarifa.model.relation.personal.family;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.object.Person;
import cz.magix.maarifa.model.relation.AbstractRelationship;

@RelationshipEntity
public class IsSiblingOf extends AbstractRelationship {
	@StartNode
	Person colleagueA;
	
	@EndNode
	Person colleagueB;
	
	public IsSiblingOf() {
		pairRelationship = IsSiblingOf.class;
	}
}
