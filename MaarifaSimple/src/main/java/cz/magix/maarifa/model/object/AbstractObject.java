package cz.magix.maarifa.model.object;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
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

	public String getNodeDescription() {
		return toString();
	}

	/*
	 * Overriden methods
	 */
	public String toString() {
		StringBuilder output = new StringBuilder();

		// Name of class
		output.append(this.getClass().getSimpleName());
		output.append(": ");

		for (Field field : this.getClass().getDeclaredFields()) {
			output.append(field.getName());
			output.append("=");

			try {
				output.append(BeanUtils.getPropertyDescriptor(this.getClass(), field.getName()).getReadMethod().invoke(this));
			} catch (IllegalArgumentException | IllegalAccessException | BeansException | InvocationTargetException e) {
				e.printStackTrace();
			}

			output.append(", ");
		}

		return output.toString();
	}
}
