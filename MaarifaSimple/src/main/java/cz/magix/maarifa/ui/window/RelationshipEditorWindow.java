package cz.magix.maarifa.ui.window;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;

import cz.magix.maarifa.ui.composite.RelationshipEditorComposite;

@org.springframework.stereotype.Component
public class RelationshipEditorWindow extends Window {
	private static final long serialVersionUID = 1L;

	@Autowired
	private RelationshipEditorComposite relationshipEditorComposite;
	
	@PostConstruct
	public void init() {
		// Set caption
		setCaption("Edit relationship between two objects");
		
		// set size and position
		setWidth("300px");
		center();

		// Set content
		setContent(relationshipEditorComposite);
		
		relationshipEditorComposite.getCancelButton().addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
	}
}
