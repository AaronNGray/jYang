package fr.loria.madynes.yangeditor.wizards;

public class YangSubmoduleWizard extends SpecificationWizard {
	public void addPages() {
		page = new YangSubmoduleWizardPage(selection);
		this.addPage(page);
	}
}
