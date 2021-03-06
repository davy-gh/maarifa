package cz.magix.maarifa.ui.composite;

import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;

import cz.magix.maarifa.model.relation.AbstractRelationship;
import cz.magix.maarifa.util.ClassTreeCreator;

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

	//TODO: pridat panel i pro form
	//TODO: expand ratio napul
	@AutoGenerated
	private BeanValidationForm<AbstractRelationship> propertiesForm;

	@AutoGenerated
	private Panel treePanel;

	@AutoGenerated
	private VerticalLayout treePanelLayout;

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
		// top-level component properties
		setSizeFull();

		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		propertiesForm = new BeanValidationForm<AbstractRelationship>(AbstractRelationship.class);
		
		// treePanel
		treePanel = buildTreePanel();
		mainLayout.addComponent(treePanel);
		mainLayout.setExpandRatio(treePanel, 1.0f);

		// propertyForm
		propertiesForm.setImmediate(true);
		mainLayout.addComponent(propertiesForm);
		
		// bottomToolbarLayout
		bottomToolbarLayout = buildBottomToolbarLayout();
		mainLayout.addComponent(bottomToolbarLayout);
		
		return mainLayout;
	}



	@AutoGenerated
	private Panel buildTreePanel() {
		// common part: create layout
		treePanel = new Panel();
		treePanel.setImmediate(true);
		treePanel.setSizeFull();
		treePanel.setScrollable(true);
		
		// treePanelLayout
		treePanelLayout = buildTreePanelLayout();
		treePanel.setContent(treePanelLayout);
		
		return treePanel;
	}



	@AutoGenerated
	private VerticalLayout buildTreePanelLayout() {
		// common part: create layout
		treePanelLayout = new VerticalLayout();
		treePanelLayout.setImmediate(false);
		treePanelLayout.setSizeUndefined();
		treePanelLayout.setMargin(false);
		
		// selectRelationshipTypeTree
		ClassTreeCreator<AbstractRelationship> treeCreator = new ClassTreeCreator<AbstractRelationship>();
		selectRelationshipTypeTree = treeCreator.createTree(propertiesForm, AbstractRelationship.class);
		selectRelationshipTypeTree.setSizeFull();
		treePanelLayout.addComponent(selectRelationshipTypeTree);
		
		return treePanelLayout;
	}



	@AutoGenerated
	private HorizontalLayout buildBottomToolbarLayout() {
		// common part: create layout
		bottomToolbarLayout = new HorizontalLayout();
		bottomToolbarLayout.setImmediate(false);
		bottomToolbarLayout.setMargin(false);
		
		// selectButton
		selectButton = new Button();
		selectButton.setCaption("Select");
		selectButton.setImmediate(true);
		selectButton.setWidth(Sizeable.SIZE_UNDEFINED, 0);
		bottomToolbarLayout.addComponent(selectButton);
		
		// cancelButton
		cancelButton = new Button();
		cancelButton.setCaption("Cancel");
		cancelButton.setImmediate(true);
		bottomToolbarLayout.addComponent(cancelButton);
		
		return bottomToolbarLayout;
	}
}
