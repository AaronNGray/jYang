package fr.loria.madynes.yangeditor.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class YangSubmoduleWizardPage extends SpecificationWizardPage {
	protected Text belongsToText;

	public YangSubmoduleWizardPage(ISelection selection) {
		super(selection);
		setTitle("Yang Editor Submodule");
		setDescription("This wizard creates a new submodule with *.yang extension that can be opened by a Yang Editor.");
	}
	
	protected void createBelongsToText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Belongs to:");
		belongsToText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		belongsToText.setLayoutData(gd);
		belongsToText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		vide = new Label(container, SWT.NULL);
	}
	
	public String getBelongsToName() {
		return belongsToText.getText();
	}
	
	public void createControl(Composite parent) {
		createContainer(parent);
		createContainerText();
		createFileText();
		createBelongsToText();
		createPrefixText();
		createOrganizationText();
		createContactText();
		createModulesText();
		createSubmodulesText();
		super.createControl(parent);
	}
}
