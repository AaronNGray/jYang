package fr.loria.madynes.yangeditor.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

public class YangModuleWizardPage extends SpecificationWizardPage {
	public YangModuleWizardPage(ISelection selection) {
		super(selection);
		setTitle("Yang Editor Module");
		setDescription("This wizard creates a new module with *.yang extension that can be opened by a Yang Editor.");
	}
	
	public void createControl(Composite parent) {
		createContainer(parent);
		createContainerText();
		createFileText();
		createNamespaceText();
		createPrefixText();
		createOrganizationText();
		createContactText();
		createModulesText();
		createSubmodulesText();
		super.createControl(parent);
	}
}