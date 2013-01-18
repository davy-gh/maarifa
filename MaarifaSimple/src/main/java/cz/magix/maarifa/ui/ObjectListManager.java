package cz.magix.maarifa.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.event.Action;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

import cz.magix.maarifa.model.object.AbstractObject;
import cz.magix.maarifa.ui.window.ObjectSearchWindow;
import cz.magix.maarifa.ui.window.RelationshipEditorWindow;

@Component
public class ObjectListManager extends VerticalLayout {
	// Serialization stuff
	private static final long serialVersionUID = 1L;

	@Autowired
	private Logger log;

	// Neo4j injection
	@Autowired
	private Neo4jTemplate neo4j;

	@Autowired
	private ObjectEditor userEditor;

	@Autowired
	private ObjectSearchWindow objectSearchWindow;

	@Autowired
	private RelationshipEditorWindow relationshipEditorWindow;

	// Components
	private final Table objectsTable;
	private final BeanContainer<Long, AbstractObject> beanContainer;

	// Actions Constants
	private static final Action ACTION_DELETE = new Action("Delete");
	private static final Action ACTION_CREATE_CHANNEL = new Action("Create Channel");
	private static final Action ACTION_CREATE_FILTER = new Action("Create Filter");

	private static final Action[] ACTIONS_BASIC = new Action[] { ACTION_CREATE_CHANNEL, ACTION_CREATE_FILTER, ACTION_DELETE };

	private String textFilter;

	/**
	 * The constructor should first build the main layout, set the composition root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual editor.
	 */
	public ObjectListManager() {
		// Init of table and data container
		beanContainer = new BeanContainer<Long, AbstractObject>(AbstractObject.class);

		// Use the name property as the item ID of the bean
		// TODO: magic constants
		beanContainer.setBeanIdProperty("nodeId");

		objectsTable = new Table(null, beanContainer);
	}

	@PostConstruct
	public void init() {
		// Fill up the data container
		EndResult<AbstractObject> result = neo4j.findAll(AbstractObject.class);
		for (AbstractObject bean : result) {
			System.out.println("Bean class name: " + bean.getClass().getName());
			beanContainer.addBean(bean);
		}

		/*
		 * Basic settings of root VerticalLayout
		 */
		setMargin(true);
		setSpacing(true);
		setSizeFull();

		/*
		 * User Panel Fields and save
		 */
		final HorizontalLayout objectManagerToolbar = new HorizontalLayout();
		objectManagerToolbar.setWidth("100%");
		addComponent(objectManagerToolbar);
		setExpandRatio(objectManagerToolbar, 0);

		// Create new user button
		final Button newUserButton = new Button("Add object");
		newUserButton.setDescription("Add new object");
		newUserButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				userEditor.setPositionX(100);
				userEditor.setPositionY(100);

				getApplication().getMainWindow().addWindow(userEditor);
			}
		});
		objectManagerToolbar.addComponent(newUserButton);

		// Create edit button
		final Button editUserButton = new Button("Edit object");
		editUserButton.setDescription("Edit existing object");
		editUserButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			// @SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				userEditor.setPositionX(100);
				userEditor.setPositionY(100);

				getApplication().getMainWindow().addWindow(userEditor);

				// Reset to edit
				userEditor.resetToEdit();
			}
		});
		editUserButton.setEnabled(false);
		objectManagerToolbar.addComponent(editUserButton);

		// Create delete button
		final Button deleteUserButton = new Button("Delete object");
		deleteUserButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			// @SuppressWarnings("unchecked")
			@Override
			@Transactional
			public void buttonClick(ClickEvent event) {
				if (objectsTable.getValue() != null) {
					log.info("Deleting: " + objectsTable.getValue().getClass().getCanonicalName());

					neo4j.delete(objectsTable.getValue());
				}
			}
		});
		deleteUserButton.setEnabled(false);
		objectManagerToolbar.addComponent(deleteUserButton);

		// Create find button
		final Button findButton = new Button("Find object");
		findButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			// @SuppressWarnings("unchecked")
			@Override
			@Transactional
			public void buttonClick(ClickEvent event) {
				getApplication().getMainWindow().addWindow(objectSearchWindow);
			}
		});
		objectManagerToolbar.addComponent(findButton);

		// Create find button
		final Button createRelationshipButton = new Button("Create relationship...");
		createRelationshipButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			// @SuppressWarnings("unchecked")
			@Override
			@Transactional
			public void buttonClick(ClickEvent event) {
				// check exactly two relationship must be selected
				if (objectsTable.getValue() instanceof Collection) {
					@SuppressWarnings("unchecked")
					Set<Object> selectedIdSet = (Set<Object>) objectsTable.getValue(); 
					if (selectedIdSet.  size() == 2) {
						getApplication().getMainWindow().addWindow(relationshipEditorWindow);
					}
					else {
						getWindow().showNotification("Exactly two object must be selected", Notification.TYPE_WARNING_MESSAGE);
					}
				} else {
					getWindow().showNotification("Exactly two object must be selected", Notification.TYPE_WARNING_MESSAGE);
				}
			}
		});
		objectManagerToolbar.addComponent(createRelationshipButton);

		// Search field
		final TextField searchObjectField = new TextField();
		searchObjectField.setInputPrompt("Search by indexed field");
		searchObjectField.addListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(TextChangeEvent event) {
				textFilter = event.getText();
				updateUserFilters();
			}
		});
		objectManagerToolbar.addComponent(searchObjectField);
		objectManagerToolbar.setExpandRatio(searchObjectField, 1);
		objectManagerToolbar.setComponentAlignment(searchObjectField, Alignment.TOP_RIGHT);

		/*
		 * User Table
		 */
		// size
		objectsTable.setSizeFull();

		// Set atrributes like - selectable etc.
		objectsTable.setSelectable(true);
		objectsTable.setMultiSelect(true);
		objectsTable.setImmediate(true); // react at once when something is
										// selected

		// turn on column reordering and collapsing
		objectsTable.setColumnReorderingAllowed(true);
		objectsTable.setColumnCollapsingAllowed(true);

		objectsTable.addListener(new Property.ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				setModificationsEnabled(event.getProperty().getValue() != null);
			}

			private void setModificationsEnabled(boolean b) {
				deleteUserButton.setEnabled(b);
				editUserButton.setEnabled(b);
			}
		});

		// Actions (a.k.a context menu)
		objectsTable.addActionHandler(new Action.Handler() {
			private static final long serialVersionUID = 1L;

			public Action[] getActions(Object target, Object sender) {
				List<Action> actionList = new ArrayList<Action>(Arrays.asList(ACTIONS_BASIC));

				if (target != null) {
					// }
				}

				return actionList.toArray(new Action[actionList.size()]);
			}

			public void handleAction(Action action, Object sender, Object target) {
			}
		});

		// Finally add to the table
		addComponent(objectsTable);
		setExpandRatio(objectsTable, 1);
	}

	/*
	 * Private methods
	 */
	private void updateUserFilters() {
		// userContainer.setApplyFiltersImmediately(false);
		// userContainer.removeAllContainerFilters();

		// if (departmentFilter != null) {
		// // two level hierarchy at max in our demo
		// if (departmentFilter.getParent() == null) {
		// usersContainer.addContainerFilter(new Equal("department.parent",
		// departmentFilter));
		// } else {
		// usersContainer.addContainerFilter(new Equal("department",
		// departmentFilter));
		// }
		// }

		if (textFilter != null && !textFilter.equals("")) {
			// Or or = new Or(new Like(User.NAME, textFilter + "%", false), new
			// Like(User.PASSWORD, textFilter + "%", false));
			// userContainer.addContainerFilter(or);
		}
		//
		// userContainer.applyFilters();
	}

	/*
	 * Getters & Setters
	 */
	public Table getObjectTable() {
		return objectsTable;
	}
}
