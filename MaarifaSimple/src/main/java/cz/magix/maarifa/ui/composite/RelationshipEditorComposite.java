package cz.magix.maarifa.ui.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.reflections.Reflections;

import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Select;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import cz.magix.maarifa.model.AbstractRelationship;
import cz.magix.maarifa.ui.annotation.UiParams;

@org.springframework.stereotype.Component
public class RelationshipEditorComposite extends CustomComponent {
	@AutoGenerated
	private VerticalLayout mainLayout;

	@AutoGenerated
	private HorizontalLayout bottomToolbarLayout;

	@AutoGenerated
	private Button cancelButton;

	@AutoGenerated
	private Button selectButton;

	@AutoGenerated
	private BeanValidationForm<AbstractRelationship> propertiesForm;

	@AutoGenerated
	private Tree selectRelationshipTypeTree;

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public RelationshipEditorComposite() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// Check all possible outgoing relationship
		Reflections reflections = new Reflections(AbstractRelationship.class.getPackage().getName());
		Set<Class<? extends AbstractRelationship>> modelClasses = reflections.getSubTypesOf(AbstractRelationship.class);

		//final ComboBox typeOfObjectComboBox = new ComboBox("Select new type of object", modelClasses);

		System.out.println("Celkový počet tříd: " + modelClasses.size());
		
		for (Class<? extends AbstractRelationship> item : modelClasses) {
			selectRelationshipTypeTree.addItem(item);
			selectRelationshipTypeTree.setItemCaption(item, item.getSimpleName());
		}

		selectRelationshipTypeTree.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
		//selectRelationshipTypeTree.setSizeFull();
		selectRelationshipTypeTree.setImmediate(true);

		selectRelationshipTypeTree.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				@SuppressWarnings("unchecked")
				Class<AbstractRelationship> objectClass = (Class<AbstractRelationship>) selectRelationshipTypeTree.getValue();
				createFormFields(propertiesForm, objectClass);
				System.out.println("Spravne");
			}
		});

		// From subset check all posiible incoming relationships

		// Fill tree by possible relationships

		//

		// TODO add user code here
	}

	private AbstractRelationship createFormFields(BeanValidationForm<AbstractRelationship> form, Class<AbstractRelationship> modelClass) {
		// Remove all fields
		form.removeAllProperties();

		if (modelClass == null) {
			return null;
		}

		AbstractRelationship object = null;

		try {
			object = modelClass.newInstance();

			// Data source
			BeanItem<AbstractRelationship> item = new BeanItem<AbstractRelationship>(object);
			form.setItemDataSource(item);

			// Visible fields and sorted fields
			Map<Integer, String> visibleProperties = new HashMap<Integer, String>();
			for (Object property : form.getItemDataSource().getItemPropertyIds()) {
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

			form.setVisibleItemProperties(propValues);

			return object;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return object;
	}

	
	/*
	 * Getters & Setters
	 */
	public Button getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(Button cancelButton) {
		this.cancelButton = cancelButton;
	}

	public Button getSelectButton() {
		return selectButton;
	}

	public void setSelectButton(Button selectButton) {
		this.selectButton = selectButton;
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// selectRelationshipTypeTree
		selectRelationshipTypeTree = new Tree();
		selectRelationshipTypeTree.setImmediate(false);
		selectRelationshipTypeTree.setWidth("100.0%");
		selectRelationshipTypeTree.setHeight("100.0%");
		mainLayout.addComponent(selectRelationshipTypeTree);
		mainLayout.setExpandRatio(selectRelationshipTypeTree, 1.0f);
		
		// propertiesForm
		propertiesForm = new BeanValidationForm<AbstractRelationship>(AbstractRelationship.class);
		propertiesForm.setImmediate(false);
		propertiesForm.setWidth("-1px");
		propertiesForm.setHeight("-1px");
		mainLayout.addComponent(propertiesForm);
		
		// bottomToolbarLayout
		bottomToolbarLayout = buildBottomToolbarLayout();
		mainLayout.addComponent(bottomToolbarLayout);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildBottomToolbarLayout() {
		// common part: create layout
		bottomToolbarLayout = new HorizontalLayout();
		bottomToolbarLayout.setImmediate(false);
		bottomToolbarLayout.setWidth("-1px");
		bottomToolbarLayout.setHeight("-1px");
		bottomToolbarLayout.setMargin(false);
		
		// selectButton
		selectButton = new Button();
		selectButton.setCaption("Select");
		selectButton.setImmediate(true);
		selectButton.setWidth("-1px");
		selectButton.setHeight("-1px");
		bottomToolbarLayout.addComponent(selectButton);
		
		// cancelButton
		cancelButton = new Button();
		cancelButton.setCaption("Cancel");
		cancelButton.setImmediate(true);
		cancelButton.setWidth("-1px");
		cancelButton.setHeight("-1px");
		bottomToolbarLayout.addComponent(cancelButton);
		
		return bottomToolbarLayout;
	}
}