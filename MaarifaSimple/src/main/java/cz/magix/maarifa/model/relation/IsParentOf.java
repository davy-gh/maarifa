package cz.magix.maarifa.model.relation;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.model.object.Person;

@RelationshipEntity
public class IsParentOf extends AbstractRelationship {
	@StartNode
	Person parent;
	
	@EndNode
	Person child;
	
	public IsParentOf() {
		setPairRelationship(IsChildOf.class);
	}
}
