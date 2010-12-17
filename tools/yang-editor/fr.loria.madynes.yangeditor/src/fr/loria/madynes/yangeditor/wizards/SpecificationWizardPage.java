package fr.loria.madynes.yangeditor.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.FileSelectionDialog;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;

public abstract class SpecificationWizardPage extends WizardPage {
	protected Text containerText, fileText, prefixText, organizationText, contactText;
	protected Table modulesText, submodulesText;
	protected ISelection selection;
	protected Composite container, addRemoveContainer;
	protected Label label, vide;
	protected GridData gd;
	protected Button browse, add, remove;
	protected GridLayout layout, addRemoveLayout;
	protected String fileName, containerName, prefixName, organizationName, contactName;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public SpecificationWizardPage(ISelection selection) {
		super("wizardPage");
		this.selection = selection;
		layout = new GridLayout();
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
	}

	protected void createContainer(Composite parent) {
		container = new Composite(parent, SWT.NULL);
		container.setLayout(layout);
	}

	protected void createContainerText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Parent project:");

		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button browse = new Button(container, SWT.PUSH);
		browse.setText("Browse...");
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse(containerText);
			}
		});
	}

	protected void createFileText() {
		label = new Label(container, SWT.NULL);
		label.setText("&File name:");

		fileText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		fileText.setLayoutData(gd);
		fileText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		vide = new Label(container, SWT.NULL);
	}

	protected void createPrefixText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Prefix:");
		prefixText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		prefixText.setLayoutData(gd);
		prefixText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		vide = new Label(container, SWT.NULL);
	}
	
	protected void createOrganizationText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Organization:");
		organizationText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		organizationText.setLayoutData(gd);
		organizationText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		vide = new Label(container, SWT.NULL);
	}
	
	protected void createContactText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Contact:");
		contactText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		contactText.setLayoutData(gd);
		contactText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
		vide = new Label(container, SWT.NULL);
	}

	protected void createModulesText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Modules:");
		modulesText = new Table(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		modulesText.setLayoutData(gd);
		addRemoveLayout = new GridLayout();
		addRemoveLayout.numColumns = 1;
		addRemoveLayout.verticalSpacing = 9;
		Composite addRemoveContainer = new Composite(container, SWT.NULL);
		addRemoveContainer.setLayout(addRemoveLayout);
		add = new Button(addRemoveContainer, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAdd(modulesText);
			}
		});
		remove = new Button(addRemoveContainer, SWT.PUSH);
		remove.setText("Remove");
		remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleRemove(modulesText);
			}
		});
	}
	
	protected void createSubmodulesText() {
		label = new Label(container, SWT.NULL);
		label.setText("&Submodules:");
		submodulesText = new Table(container, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
		gd = new GridData(GridData.FILL_BOTH);
		submodulesText.setLayoutData(gd);
		addRemoveLayout = new GridLayout();
		addRemoveLayout.numColumns = 1;
		addRemoveLayout.verticalSpacing = 9;
		addRemoveContainer = new Composite(container, SWT.NULL);
		addRemoveContainer.setLayout(addRemoveLayout);
		add = new Button(addRemoveContainer, SWT.PUSH);
		add.setText("Add");
		add.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleAdd(submodulesText);
			}
		});
		remove = new Button(addRemoveContainer, SWT.PUSH);
		remove.setText("Remove");
		remove.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleRemove(submodulesText);
			}
		});
	}
	
	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */

	private void initialize() {
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			if (obj instanceof IResource) {
				IContainer container;
				if (obj instanceof IContainer)
					container = (IContainer) obj;
				else
					container = ((IResource) obj).getParent();
				containerText.setText(container.getFullPath().toString());
			}
		}
		fileText.setText("new_file.yang");
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	protected void handleBrowse(Text champ) {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select new file container");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				champ.setText(((Path) result[0]).toString());
			}
		}
	}
	
	private void handleAdd(Table table) {
		ResourceSelectionDialog dialog = new ResourceSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), "Select a yang file");
		
		if(dialog.open() == ResourceSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			
			for(Object o : result) {
				System.out.println();
			}
		}
	}
	
	private void handleRemove(Table table) {
		
	}

	/**
	 * Ensures that both text fields are set.
	 */

	protected void dialogChanged() {
		this.fileName = fileText.getText();
		this.containerName = containerText.getText();
		this.contactName = contactText.getText();
		this.organizationName = organizationText.getText();
		this.prefixName = prefixText.getText();
		
		if(containerName == null || containerName.length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(containerName));

		if(getPrefix().length() == 0) {
			updateStatus("Prefix must be specified");
			return;
		}
		
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}
		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}
		if (fileName.length() == 0) {
			updateStatus("File name must be specified");
			return;
		}
		if (fileName.replace('\\', '/').indexOf('/', 1) > 0) {
			updateStatus("File name must be valid");
			return;
		}
		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase("yang") == false) {
				updateStatus("File extension must be \"yang\"");
				return;
			}
		}
		updateStatus(null);
	}

	protected void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getContainerName() {
		return this.containerName;
	}

	public String getFileName() {
		return this.fileName;
	}
	
	public String getPrefix() {
		return this.prefixName;
	}
	
	public String getOrganizationName() {
		return this.organizationName;
	}
	
	public String getContactName() {
		return this.contactName;
	}
	
	public abstract String getContents();
}
