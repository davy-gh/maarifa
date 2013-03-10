package cz.magix.maarifa.ui.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.TextField;

import cz.magix.maarifa.model.object.AbstractObject;
import cz.magix.maarifa.ui.annotation.UiParams;

@org.springframework.stereotype.Component
public class MaarifaFieldFactory implements FormFieldFactory {
	private static final long serialVersionUID = 1L;

	/**
	 * (re)create form fields
	 * 
	 * @param editorForm
	 * @param modelClass
	 * @return
	 */
	public static AbstractObject createFormFields(Form editorForm, Class<AbstractObject> modelClass) {
		// Remove all fields
		editorForm.removeAllProperties();

		if (modelClass == null) {
			return null;
		}

		AbstractObject object = null;

		try {
			object = modelClass.newInstance();

			// Data source
			BeanItem<AbstractObject> item = new BeanItem<AbstractObject>(object);
			editorForm.setItemDataSource(item);

			// Visible fields and sorted fields
			Map<Integer, String> visibleProperties = new HashMap<Integer, String>();
			for (Object property : editorForm.getItemDataSource().getItemPropertyIds()) {
				if (property instanceof String) {
					if (!("nodeId".equals((String) property) || "nodeDescription".equals((String) property))) {
						try {
							java.lang.reflect.Field classField = object.getClass().getDeclaredField((String) property);

							if (classField.isAnnotationPresent(UiParams.class)) {
								UiParams uiParams = classField.getAnnotation(UiParams.class);
								int position = uiParams.position();

								while (visibleProperties.containsKey(position)) {
									position++;
								}
								visibleProperties.put(position, (String) property);

							} else {
								// Find the lowest position and put it there
								int lowestPosition = 0;
								for (int i : visibleProperties.keySet()) {
									if (lowestPosition < i) {
										lowestPosition = i;
									}
								}

								visibleProperties.put(++lowestPosition, (String) property);
							}

						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
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

			editorForm.setVisibleItemProperties(propValues);

			return object;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return object;
	}

	/**
	 * Create fields one by one
	 * 
	 * @param name
	 * @return
	 */
	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		// String id = (String) propertyId;
		Class<?> type = item.getItemProperty(propertyId).getType();

		// Working values
		Field field;

		// Identify the fields by name
		if (type.isEnum()) {
			// Select select = new Select(NameUtil.normalizeCamelCase(id));
			//
			// for (Object enumItem : type.getEnumConstants()) {
			// //
			// select.addItem(NameUtil.normalizeUpperCase(enumItem.toString()));
			// select.addItem(enumItem.toString());
			// }
			//
			// field = select;
			return null;
		} else {
			field = DefaultFieldFactory.get().createField(item, propertyId, uiContext);
		}

		// Last specific checks
		if (field instanceof TextField) {
			((TextField) field).setNullRepresentation("");
		}

		return field;
	}
}
