package fr.loria.madynes.yangeditor.wizards;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

public class YangSubmoduleWizardPage extends SpecificationWizardPage {
	protected Text belongsToText;
	protected String belongsToName;

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
		browse = new Button(container, SWT.PUSH);
		browse.setText("Browse...");
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleFileBrowse(belongsToText);
			}
		});
	}
	
	protected void handleFileBrowse(Text champ) {
		ResourceSelectionDialog dialog = new ResourceSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Select a yang file");
		
		if(dialog.open() == ResourceSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			champ.setText(((File)result[0]).getFullPath().toString());
		}
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
	
	protected void dialogChanged() {
		this.belongsToName = belongsToText.getText();
		
		if(belongsToName.length() == 0) {
			updateStatus("Belongs to must be specified");
			return;
		}
		
		super.dialogChanged();
	}

	public String getContents() {
		return "submodule "
		+ fileName.substring(0, fileName.lastIndexOf('.'))
		+ " { belongs-to " + belongsToName
		+ " { prefix " + prefixName + "; } "
		+ ((organizationName.equals("")) ? "" : ("organization \"" + organizationName + "\"; "))
		+ ((contactName.equals("")) ? "" : ("contact \"" + contactName)) 
		+ "\";}";
	}
}
