package cz.magix.maarifa.simple.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;

import com.vaadin.Application;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Window;

import cz.magix.maarifa.simple.Neo4jUtils;

@Component(value = "maarifaSimpleApplication")
public class MaarifaSimpleApplication extends Application {
	private static final long serialVersionUID = 2L;

	private static final ThemeResource icon1 = new ThemeResource("../icons/feed1.png");
	private static final ThemeResource icon2 = new ThemeResource("../icons/feed2.png");
	
	@Autowired
	private Neo4jTemplate neo4j;
	
	@Autowired
	private ObjectListManager objectListManager;

	@Autowired
	private VisualGraphManager visualGraphManager;

	private TabSheet tabs;

	@Override
	public void init() {
		/*
		 * Very dirty hook
		 */
		//TODO: vyresit, tak aby to nebylo ta dirty
		Neo4jUtils.registerShutdownHook(neo4j.getGraphDatabaseService());
		
		/*
		 * Main Window - always first
		 */
		final Window mainWindow = new Window("Feed Filter");
		mainWindow.setSizeFull();
		mainWindow.getContent().setSizeFull();
		setMainWindow(mainWindow);

		/*
		 * Add tab component
		 */
		tabs = new TabSheet();
		tabs.setSizeFull();

		tabs.addTab(objectListManager, "Object List", icon1);
		tabs.addTab(visualGraphManager, "Graph Visualization", icon2);

		tabs.addListener(new TabSheet.SelectedTabChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void selectedTabChange(SelectedTabChangeEvent event) {
				TabSheet tabsheet = event.getTabSheet();
				Tab tab = tabsheet.getTab(tabsheet.getSelectedTab());
				if (tab != null) {
					mainWindow.getWindow().showNotification("Selected tab: " + tab.getCaption());
				}
			}
		});
		// mainWindow.setContent(tabs);
		mainWindow.addComponent(tabs);
	}
}
