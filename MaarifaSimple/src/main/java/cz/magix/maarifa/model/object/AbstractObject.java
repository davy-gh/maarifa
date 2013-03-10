package cz.magix.maarifa.model.object;

import java.beans.PropertyDescriptor;
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

	/*
	 * Helper methods
	 */
	public String getDescription() {
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

			PropertyDescriptor propertyDescriptor = BeanUtils.getPropertyDescriptor(this.getClass(), field.getName());

			if (propertyDescriptor.getReadMethod() != null) {
				try {
					output.append(propertyDescriptor.getReadMethod().invoke(this));
				} catch (BeansException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			output.append(", ");
		}

		return output.toString();
	}
}
