package cz.magix.maarifa.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.StartNode;

import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Select;
import com.vaadin.ui.Tree;

import cz.magix.maarifa.ui.annotation.UiParams;

public class ClassTreeCreator<T> {

	/**
	 * TODO: doc it
	 * 
	 * @param propertiesForm
	 * @param baseClass
	 * @param caption
	 * @return
	 */
	public Tree createTree(final BeanValidationForm<T> propertiesForm, final Class<T> baseClass) {
		final Tree classTree = new Tree();

		// Check all possible outgoing relationship
		Reflections reflections = new Reflections(baseClass.getPackage().getName());
		Set<Class<? extends T>> modelClasses = reflections.getSubTypesOf(baseClass);

		// TODO: vybrat jen ty, pomoci ktery lze spojit dva oznacene objekty !!!
		// TODO: polozky ve strome by to chtelo setridit
		for (Class<? extends T> item : modelClasses) {
			classTree.addItem(item);
			classTree.setChildrenAllowed(item, false);
			classTree.setItemCaption(item, item.getSimpleName());

			// Check for branches/deeper packages
			if (item.getPackage().getName().startsWith(baseClass.getPackage().getName())) {
				String rest = item.getPackage().getName().substring(baseClass.getPackage().getName().length());

				// Must not be null and must be larger than 1 (because substring
				// lower)
				if (rest != null && rest.length() > 1) {
					// For multiple levels
					String[] levels = rest.substring(1).split("\\.");

					for (int i = 0; i < levels.length; i++) {
						System.out.println("The rest of package name: " + rest);
						classTree.addItem(levels[i]);
						classTree.setChildrenAllowed(levels[i], true);

						// Set parent to previous level
						if (i > 0) {
							classTree.setParent(levels[i], levels[i - 1]);
						}

						// If this is the last level put our class here
						if ((i + 1) == levels.length) {
							classTree.setParent(item, levels[i]);
						}
					}
				}
			}
		}

		classTree.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
		classTree.setSizeFull();
		classTree.setImmediate(true);

		classTree.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (classTree.getValue() instanceof Class) {
					@SuppressWarnings("unchecked")
					Class<? extends T> objectClass = (Class<? extends T>) classTree.getValue();
					createFormFields(propertiesForm, objectClass);
					System.out.println("Spravne");
				} else {
					if (propertiesForm != null) {
						propertiesForm.removeAllProperties();
					}
				}
			}
		});

		return classTree;
	}

	/**
	 * TODO: doc it
	 * 
	 * @param form
	 * @param modelClass
	 * @return
	 */
	private T createFormFields(final BeanValidationForm<T> form, final Class<? extends T> modelClass) {
		// If one from two parameters is null it must not continue
		if (form == null || modelClass == null) {
			return null;
		}

		// Remove all fields
		form.removeAllProperties();

		T object = null;

		try {
			// Data source
			object = (T) modelClass.newInstance();
			BeanItem<T> item = new BeanItem<T>(object);
			form.setItemDataSource(item);

			// Create set of named properties
			Set<String> propertySet = new HashSet<String>();
			for (Object property : form.getItemDataSource().getItemPropertyIds()) {
				if (property instanceof String) {

					if (!("nodeId".equals((String) property) || "nodeDescription".equals((String) property))) {
						System.out.println("Property1: " + (String) property);
						propertySet.add((String) property);
					}
				}
			}

			// Create relevant field list
			List<Field> fieldList = new ArrayList<Field>();

			// TODO: neni to moc elegantni
			Class<?> testedClass = modelClass;
			do {
				for (Field field : testedClass.getDeclaredFields()) {
					System.out.println("Property2: " + field.getName());

					if (propertySet.contains(field.getName())) {
						propertySet.remove(field.getName());

						// Check if doesn't have unproproate annotations
						if (!(field.isAnnotationPresent(GraphId.class) || field.isAnnotationPresent(StartNode.class) || field.isAnnotationPresent(EndNode.class))) {
							fieldList.add(field);
						}
					}
				}

				if (testedClass.getSuperclass() != null) {
					testedClass = testedClass.getSuperclass();
				} else {
					break;
				}
			} while (!propertySet.isEmpty());

			// Visible fields and sorted fields
			Map<Integer, String> visibleProperties = new HashMap<Integer, String>();
			for (Field field : fieldList) {
				System.out.println("Property3: " + field.getName());

				if (field.isAnnotationPresent(UiParams.class)) {
					UiParams uiParams = field.getAnnotation(UiParams.class);
					int position = uiParams.position();

					while (visibleProperties.containsKey(position)) {
						position++;
					}
					visibleProperties.put(position, field.getName());

				} else {
					// Find the lowest position and put it there
					int lowestPosition = 0;
					for (int i : visibleProperties.keySet()) {
						if (lowestPosition < i) {
							lowestPosition = i;
						}
					}

					visibleProperties.put(++lowestPosition, field.getName());
				}
			}

			// Sort by keys
			List<Integer> propKeys = new ArrayList<Integer>();
			List<String> propValues = new ArrayList<String>();
			propKeys.addAll(visibleProperties.keySet());

			Collections.sort(propKeys);
			for (int prop : propKeys) {
				propValues.add(visibleProperties.get(prop));
			}

			form.setVisibleItemProperties(propValues);

			return object;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return object;
	}
}
