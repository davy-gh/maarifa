package cz.magix.maarifa.simple.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Transaction;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.data.Validator.InvalidValueException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.FormFieldFactory;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import cz.magix.maarifa.simple.model.AbstractObject;
import cz.magix.maarifa.simple.model.Person;
import cz.magix.maarifa.simple.ui.annotation.UiParams;

@Component
public class ObjectEditor extends Window implements FormFieldFactory {
	private static final long serialVersionUID = 1L;

	// Neo4j injection
	@Autowired
	private Neo4jTemplate neo4j;
	
	@Autowired
	private ObjectListManager objectListManager;

	// final Form editorForm;
	final BeanValidationForm<AbstractObject> editorForm;
	final Button saveButton;
	final Button cancelButton;

	/**
	 * Class for editing users
	 * 
	 * @param neo4j
	 * 
	 * @param userItem
	 *            - if this parameter is null, it means new user
	 * @param userContainer
	 * @param groupTable
	 */
	public ObjectEditor() {
		// Dialog settings
		setCaption("Create/Edit Object");
		setSizeUndefined();
		getContent().setSizeUndefined();

		// Add ComboBox to the top
		addComponent(createComboFields());

		// Create form and add to the window
		editorForm = new BeanValidationForm<AbstractObject>(AbstractObject.class);
		// editorForm = new Form();
		editorForm.setImmediate(true);
		editorForm.setWriteThrough(false);
		editorForm.setSizeFull();
		editorForm.setFormFieldFactory(this);
		addComponent(editorForm);

		/*
		 * Buttons
		 */

		// Buttons in footer
		saveButton = new Button("Save");
		saveButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@SuppressWarnings("unchecked")
			@Override
			@Transactional("neo4jTransactionManager")
			public void buttonClick(ClickEvent event) {
				try {
					editorForm.commit();

					BeanItem<? extends AbstractObject> beanItem = (BeanItem<? extends AbstractObject>) editorForm.getItemDataSource();

					if (beanItem != null) {
						System.out.println("Co to je: " + beanItem.getClass().getName());

						Transaction tx = neo4j.getGraphDatabaseService().beginTx();

						try {
							BeanItemContainer<Person> beanItemContainer = (BeanItemContainer<Person>) objectListManager.getUserTable().getContainerDataSource();  
							
							Person bean = (Person) neo4j.save(beanItem.getBean());

							if (bean != null) {
								beanItemContainer.addBean(bean);
//								objectListManager.getUserTable().getContainerDataSource().
//								objectListManager.getUserTable().refreshRowCache();
								
								for (Object iid : objectListManager.getUserTable().getContainerDataSource().getItemIds()) {
									System.out.println("IID: " + iid);
								}
								
								tx.success();
							} else {
								tx.failure();
							}
							
						} finally {
							tx.finish();
						}

					} else {
						System.out.println("Je to null");
					}
				} catch (EmptyValueException e) {
					return;
				} catch (InvalidValueException e) {
					return;
				}

				close();
			}
		});

		cancelButton = new Button("Cancel");
		cancelButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				editorForm.discard();
				close();
			}
		});

		editorForm.getFooter().addComponent(saveButton);
		editorForm.getFooter().addComponent(cancelButton);
	}

	/**
	 * Create main selecting combo box
	 * 
	 * @return
	 */
	private ComboBox createComboFields() {
		Reflections reflections = new Reflections(AbstractObject.class.getPackage().getName());
		Set<Class<? extends AbstractObject>> modelClasses = reflections.getSubTypesOf(AbstractObject.class);

		final ComboBox typeOfObject = new ComboBox("Select new type of object", modelClasses);

		for (Class<? extends AbstractObject> item : modelClasses) {
			typeOfObject.addItem(item);
			typeOfObject.setItemCaption(item, item.getSimpleName());
		}

		typeOfObject.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
		typeOfObject.setSizeFull();
		typeOfObject.setImmediate(true);

		typeOfObject.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				@SuppressWarnings("unchecked")
				Class<AbstractObject> objectClass = (Class<AbstractObject>) typeOfObject.getValue();
				createFormFields(editorForm, objectClass);
				System.out.println("Spravne");
			}
		});

		return typeOfObject;
	}

	/**
	 * (re)create form fields
	 * 
	 * @param editorForm
	 * @param modelClass
	 * @return
	 */
	private AbstractObject createFormFields(Form editorForm, Class<AbstractObject> modelClass) {
		if (modelClass == null) {
			return null;
		}

		System.out.println(modelClass.getName());

		// Remove all fields
		editorForm.removeAllProperties();

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
					if (!"nodeId".equals((String) property)) {
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
	public Field createField(Item item, Object propertyId, com.vaadin.ui.Component uiContext) {
		Field field;
		// String pid = (String) propertyId;

		System.out.println("Property ID: " + propertyId);

		// Identify the fields by
		// if ("name".equals(pid)) {
		// field = new TextField("Name");
		// } else if ("city".equals(pid)) {
		// Select select = new Select("City");
		// select.addItem("Berlin");
		// select.addItem("Helsinki");
		// select.addItem("London");
		// select.addItem("New York");
		// select.addItem("Turku");
		// select.setNewItemsAllowed(true);
		//
		// field = select;
		//
		// } else {
		field = DefaultFieldFactory.get().createField(item, propertyId, uiContext);
		// }

		// Last specific checks
		if (field instanceof TextField) {
			((TextField) field).setNullRepresentation("");
		}

		return field;
	}
}
