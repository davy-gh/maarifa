package cz.magix.maarifa.simple.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

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
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
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
import cz.magix.maarifa.simple.ui.annotation.UiParams;

@Component
public class ObjectSearch extends Window implements FormFieldFactory {
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private Logger log;

	// Neo4j injection
	@Autowired
	private Neo4jTemplate neo4j;

	@Autowired
	private ObjectListManager objectListManager;

	// final Form editorForm;
	private final BeanValidationForm<AbstractObject> editorForm;
	private final Button saveButton;
	private final Button cancelButton;
	private final ComboBox typeOfObject;

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
	public ObjectSearch() {
		setModal(true);
		setSizeUndefined();
		getContent().setSizeUndefined();

		// Add ComboBox to the top
		typeOfObject = createComboFields(); 
		addComponent(typeOfObject);

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
					BeanContainer<Long, AbstractObject> beanContainer = (BeanContainer<Long, AbstractObject>) objectListManager.getUserTable().getContainerDataSource();

					if (beanItem != null) {
						//TODO: predelat na annotace
						Transaction tx = neo4j.getGraphDatabaseService().beginTx();

						try {
							AbstractObject bean = neo4j.save(beanItem.getBean());

							if (bean != null) {
								beanContainer.addBean(bean);
								tx.success();
							} else {
								tx.failure();
							}

						} finally {
							tx.finish();
						}
					}
				} catch (EmptyValueException e) {
					return;
				} catch (InvalidValueException e) {
					return;
				}

				close();
				resetForm();	
			}
		});

		cancelButton = new Button("Cancel");
		cancelButton.addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				editorForm.discard();
				close();
				resetForm();	
			}
		});

		editorForm.getFooter().addComponent(saveButton);
		editorForm.getFooter().addComponent(cancelButton);
		
		// Listener
		addListener(new ComponentAttachListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void componentAttachedToContainer(ComponentAttachEvent event) {
				// TODO Auto-generated method stub
				log.info("Attached");
			}
		});
	}

	/**
	 * Create main selecting combo box
	 * 
	 * @return
	 */
	private ComboBox createComboFields() {
		Reflections reflections = new Reflections(AbstractObject.class.getPackage().getName());
		Set<Class<? extends AbstractObject>> modelClasses = reflections.getSubTypesOf(AbstractObject.class);

		final ComboBox typeOfObjectComboBox = new ComboBox("Select new type of object", modelClasses);

		for (Class<? extends AbstractObject> item : modelClasses) {
			typeOfObjectComboBox.addItem(item);
			typeOfObjectComboBox.setItemCaption(item, item.getSimpleName());
		}

		typeOfObjectComboBox.setItemCaptionMode(Select.ITEM_CAPTION_MODE_EXPLICIT_DEFAULTS_ID);
		typeOfObjectComboBox.setSizeFull();
		typeOfObjectComboBox.setImmediate(true);

		typeOfObjectComboBox.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				@SuppressWarnings("unchecked")
				Class<AbstractObject> objectClass = (Class<AbstractObject>) typeOfObjectComboBox.getValue();
				createFormFields(editorForm, objectClass);
				System.out.println("Spravne");
			}
		});

		return typeOfObjectComboBox;
	}

	/**
	 * (re)create form fields
	 * 
	 * @param editorForm
	 * @param modelClass
	 * @return
	 */
	private AbstractObject createFormFields(Form editorForm, Class<AbstractObject> modelClass) {
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
	public Field createField(Item item, Object propertyId, com.vaadin.ui.Component uiContext) {
		String pid = (String) propertyId;

		// Working values
		Field field;

		// Identify the fields by name
		if ("country".equals(pid)) {
			// field = new TextField("Name");
			// } else if ("city".equals(pid)) {
			//TODO: add list of official countries bye ISO something, + suitable list
			Select select = new Select("Country");
			select.addItem("Czech Republic");
			select.addItem("USA");
			select.addItem("Afghanistan");
			select.addItem("UK");
			select.addItem("Taiwan");
			select.setNewItemsAllowed(true);

			field = select;

		} else {
			field = DefaultFieldFactory.get().createField(item, propertyId, uiContext);
		}

		// Last specific checks
		if (field instanceof TextField) {
			((TextField) field).setNullRepresentation("");
		}

		return field;
	}

	/**
	 * /TODO: doc it
	 */
	private void resetForm() {
		typeOfObject.setValue(null);
	}
	
	
	/**
	 * TODO: set to edit
	 */
	public void resetToEdit() {
		// Get actual value
		Object object = objectListManager.getUserTable().getValue();
		
		// Set class
		typeOfObject.setValue(object.getClass());

		// Set value
		editorForm.setValue(object);
	}
}
