package fr.loria.madynes.yangeditor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenDeclarationHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public OpenDeclarationHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);		
		ISelection selection = window.getActivePage().getSelection();
		
		if(selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			String name = textSelection.getText();
		}
		
		return null;
	}
}

