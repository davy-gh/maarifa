package cz.magix.maarifa.simple.ui.window;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;

import cz.magix.maarifa.simple.ui.composite.ObjectSearchComposite;

@org.springframework.stereotype.Component
public class ObjectSearchWindow extends Window {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ObjectSearchComposite objectSearchComposite;
	
	@PostConstruct
	public void init() {
		setContent(objectSearchComposite);
		
		objectSearchComposite.getCancelButton().addListener(new ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				close();
			}
		});
	}
}
