package cz.magix.maarifa.ui;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Window;

@org.springframework.stereotype.Component
public class WrappingWindow extends Window {
	private static final long serialVersionUID = 1L;

	public void setCustomComponent(CustomComponent customComponent) {
		setContent(customComponent);
	}
}
