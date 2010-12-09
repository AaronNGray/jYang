package fr.loria.madynes.yangeditor.wizards;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class YangModuleWizardPage extends SpecificationWizardPage {
	protected Text namespaceText;
	
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
	
	protected void createNamespaceText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Namespace:");
		namespaceText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		namespaceText.setLayoutData(gd);
		namespaceText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		vide = new Label(container, SWT.NULL);
	}
	
	public String getNamespace() {
		return namespaceText.getText();
	}
	
	public String getContents() {
		String fileName = fileText.getText();
		String organizationName = organizationText.getText();
		String contactName = contactText.getText();
		
		return "module "
		+ fileName.substring(0, fileName.lastIndexOf('.'))
		+ " {\n\tnamespace\n\t\t\"" + namespaceText.getText()
		+ "\";\n\n\tprefix\n\t\t\"" + prefixText.getText()
		+ ((organizationName.equals("")) ? "" : ("\";\n\n\torganization\n\t\t\"" + organizationName))
		+ ((contactName.equals("")) ? "" : ("\";\n\n\tcontact\n\t\t\"" + contactName)) 
		+ "\";\n}";
	}
	
	protected void dialogChanged() {
		if(getNamespace().length() == 0) {
			updateStatus("Namespace must be specified");
			return;
		}
		
		super.dialogChanged();
	}
}