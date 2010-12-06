package fr.loria.madynes.yangeditor.wizards;

public class YangModuleWizard extends SpecificationWizard {
	public void addPages() {
		page = new YangModuleWizardPage(selection);
		this.addPage(page);
	}
}