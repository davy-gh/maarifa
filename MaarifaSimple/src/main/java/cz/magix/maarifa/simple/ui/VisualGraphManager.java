package cz.magix.maarifa.simple.ui;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@Component
public class VisualGraphManager extends VerticalLayout {
	// Serialization stuff
	private static final long serialVersionUID = 1L;

	/**
	 * The constructor should first build the main layout, set the composition root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual editor.
	 */
	public VisualGraphManager() {
	}

	@PostConstruct
	public void init() {
		/*
		 * Settings of this manager
		 */
		setMargin(false);
		setSpacing(false);
		setSizeFull();

		/*
		 * Tag Tree toolbar (left-upper part)
		 */
		// Create tag tree horizontal toolbar
		HorizontalLayout tagToolbar = new HorizontalLayout();
		tagToolbar.setSizeUndefined();

		// Create new user button
		final Button addTagButton = new Button("Add tag");
		addTagButton.setDescription("Add new tag");
		addTagButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		tagToolbar.addComponent(addTagButton);

		// Create edit button
		final Button editTagButton = new Button("Edit tag");
		editTagButton.setDescription("Edit tag");
		editTagButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		editTagButton.setEnabled(false);
		tagToolbar.addComponent(editTagButton);

		// Create delete button
		final Button removeTagButton = new Button("Remove tag");
		removeTagButton.addListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
			}
		});
		removeTagButton.setEnabled(false);
		tagToolbar.addComponent(removeTagButton);

		// Search field
		final TextField searchTagField = new TextField();
		searchTagField.setInputPrompt("Search by name");
		searchTagField.addListener(new TextChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void textChange(TextChangeEvent event) {
				// textFilter = event.getText();
				// updateUserFilters();
			}
		});
		tagToolbar.addComponent(searchTagField);
		tagToolbar.setWidth("100%");
		tagToolbar.setExpandRatio(searchTagField, 1);
		tagToolbar.setComponentAlignment(searchTagField, Alignment.TOP_RIGHT);

		// Finally add tag toolbar to component
		addComponent(tagToolbar);
		setExpandRatio(tagToolbar, 0f);
	}
}
