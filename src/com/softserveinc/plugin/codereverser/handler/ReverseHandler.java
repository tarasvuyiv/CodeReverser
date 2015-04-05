package com.softserveinc.plugin.codereverser.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IStorage;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import com.softserveinc.plugin.codereverser.utils.StringInput;
import com.softserveinc.plugin.codereverser.utils.StringStorage;

public class ReverseHandler extends AbstractHandler {

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {

        ISelection sel = HandlerUtil.getActiveMenuSelection(event);
        IStructuredSelection selection = (IStructuredSelection) sel;

        generateOutput(selection.getFirstElement());

        return null;
    }

    private void generateOutput(Object firstElement) {
        ICompilationUnit compilationUnit = (ICompilationUnit) firstElement;
        try {
            final IWorkbench workbench = PlatformUI.getWorkbench();
            if (workbench.getActiveWorkbenchWindow() == null
                    || workbench.getActiveWorkbenchWindow().getActivePage() == null) {
                return;
            }
            final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
                    .getActivePage();

            String reversedSourceText = reverseCode(compilationUnit.getSource());

            IStorage storage = new StringStorage(reversedSourceText,
                    compilationUnit.getCorrespondingResource().getName());
            IStorageEditorInput input = new StringInput(storage);

            if (page != null)
                page.openEditor(input, "org.eclipse.ui.DefaultTextEditor");
        } catch (PartInitException e) {
        } catch (JavaModelException e) {
            e.printStackTrace();
        }
    }

    private String reverseCode(String sourceCode) {
        String[] codeLines = sourceCode.split("\n");
        String reversedSourceCode = "";

        for (String codeLine : codeLines) {
            StringBuilder reversedCodeLine = new StringBuilder();
            codeLine = codeLine.replaceAll("\\s+$", "");

            if (!codeLine.isEmpty()) {

                for (int i = 0; codeLine.charAt(i) == ' '; i++) {
                    codeLine = codeLine.substring(0, i) + '\n'
                            + codeLine.substring(i + 1);
                }

                for (int i = codeLine.length() - 1; i >= 0; i--) {
                    char symbol = codeLine.charAt(i);
                    switch (symbol) {
                    case '{':
                        reversedCodeLine.append('}');
                        break;
                    case '}':
                        reversedCodeLine.append('{');
                        break;
                    case '(':
                        reversedCodeLine.append(')');
                        break;
                    case ')':
                        reversedCodeLine.append('(');
                        break;
                    case '\n':
                        reversedCodeLine.insert(0, ' ');
                        break;
                    case '\t':
                        reversedCodeLine.insert(0, '\t');
                        break;
                    default:
                        reversedCodeLine.append(symbol);
                    }
                }
            }

            reversedSourceCode += reversedCodeLine + "\n";
        }

        return reversedSourceCode;
    }

}
