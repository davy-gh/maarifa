package cz.magix.maarifa.simple.model;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;

@NodeEntity
public class AbstractObject {
	@GraphId
	private transient Long nodeId;
	
	/*
	 * Getters & Setters
	 */
	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	/*
	 * Overriden methods
	 */
	public String toString() {
		// TODO: normalize name
		return this.getClass().getSimpleName();
	}
}
