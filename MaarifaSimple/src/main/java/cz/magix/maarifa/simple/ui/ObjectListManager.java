package cz.magix.maarifa.simple.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
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

import cz.magix.maarifa.simple.model.AbstractObject;
import cz.magix.maarifa.simple.model.Person;

@Component
public class ObjectListManager extends VerticalLayout {
	// Serialization stuff
	private static final long serialVersionUID = 1L;

	// Neo4j injection
	@Autowired
	private Neo4jTemplate neo4j;
	
	@Autowired
	private ObjectEditor userEditor;
	
	// private UserBeanQuery userBeanQuery;
	public static final String PERSISTENCE_UNIT = "feedfilter";

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
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	public void init() {
		// UI components
		//BeanItemContainer<AbstractObject> beanItemContainer = new BeanItemContainer<AbstractObject>(AbstractObject.class);
		@SuppressWarnings({ "rawtypes" })
		BeanItemContainer beanItemContainer = new BeanItemContainer(Person.class);

		EndResult<Person> result = neo4j.findAll(Person.class);
		for (AbstractObject bean : result) {
			System.out.println("Bean class name: " + bean.getClass().getName());
			beanItemContainer.addBean((Person) bean);
		}
		
		final Table userTable = new Table(null, beanItemContainer);

		/*
		 * Basic settings of root VerticalLayout
		 */
		setMargin(true);
		setSpacing(true);
		setSizeFull();

		/*
		 * User Panel Fields and save
		 */
		final HorizontalLayout userToolbar = new HorizontalLayout();
		userToolbar.setWidth("100%");
		addComponent(userToolbar);
		setExpandRatio(userToolbar, 0);

		// Create new user button
		final Button newUserButton = new Button("Add object");
		newUserButton.setDescription("Add new object");
		newUserButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				//final ObjectEditor userEditor = new ObjectEditor(neo4j, null);
				userEditor.setPositionX(100);
				userEditor.setPositionY(100);

				getApplication().getMainWindow().addWindow(userEditor);
			}
		});
		userToolbar.addComponent(newUserButton);

		// Create edit button
		final Button editUserButton = new Button("Edit object");
		editUserButton.setDescription("Edit existing object");
		editUserButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

//			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
//				for (Long itemId : (Set<Long>) userTable.getValue()) {
//					getApplication().getMainWindow().addWindow(new UserEditor((EntityItem<User>) userTable.getItem(itemId), userContainer, groupTable));
//				}
			}
		});
		editUserButton.setEnabled(false);
		userToolbar.addComponent(editUserButton);

		// Create delete button
		final Button deleteUserButton = new Button("Delete user");
		deleteUserButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

//			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
//				for (Long itemId : (Set<Long>) userTable.getValue()) {
//					userContainer.removeItem(itemId);
//				}
			}
		});
		deleteUserButton.setEnabled(false);
		userToolbar.addComponent(deleteUserButton);

		// Search field
		final TextField searchUserField = new TextField();
		searchUserField.setInputPrompt("Search by name");
		searchUserField.addListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(TextChangeEvent event) {
				textFilter = event.getText();
				updateUserFilters();
			}
		});
		userToolbar.addComponent(searchUserField);
		userToolbar.setExpandRatio(searchUserField, 1);
		userToolbar.setComponentAlignment(searchUserField, Alignment.TOP_RIGHT);

		/*
		 * User Table
		 */
		// size
		userTable.setSizeFull();

		// Set atrributes like - selectable etc.
		userTable.setSelectable(true);
		userTable.setMultiSelect(true);
		userTable.setImmediate(true); // react at once when something is
										// selected

		// turn on column reordering and collapsing
		userTable.setColumnReorderingAllowed(true);
		userTable.setColumnCollapsingAllowed(true);

		// Order of columns
//		userTable.setVisibleColumns(new String[] { User.NAME, User.PASSWORD, User.REG_DATE, User.GROUPS, User.CHANNELS, User.FILTERS });

		userTable.addListener(new Property.ValueChangeListener() {
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
		userTable.addActionHandler(new Action.Handler() {
			private static final long serialVersionUID = 1L;

			public Action[] getActions(Object target, Object sender) {
				List<Action> actionList = new ArrayList<Action>(Arrays.asList(ACTIONS_BASIC));

				if (target != null) {
//					}
				}

				return actionList.toArray(new Action[actionList.size()]);
			}

			public void handleAction(Action action, Object sender, Object target) {
			}
		});

		// Finally add to the table
		addComponent(userTable);
		setExpandRatio(userTable, 1);
	}

	/*
	 * Private methods
	 */
	private void updateUserFilters() {
//		userContainer.setApplyFiltersImmediately(false);
//		userContainer.removeAllContainerFilters();

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
//			Or or = new Or(new Like(User.NAME, textFilter + "%", false), new Like(User.PASSWORD, textFilter + "%", false));
//			userContainer.addContainerFilter(or);
		}
//
//		userContainer.applyFilters();
	}
	
//	private void refreshData() {
//		
//	}
}
