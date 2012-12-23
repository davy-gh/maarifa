package cz.magix.maarifa.model.relation.personal;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.model.object.Person;
import cz.magix.maarifa.model.relation.IsParentOf;

@RelationshipEntity
public class IsChildOf extends AbstractRelationship {
	@StartNode
	Person child;
	
	@EndNode
	Person parent;
	
	public IsChildOf() {
		setPairRelationship(IsParentOf.class);
	}
}
