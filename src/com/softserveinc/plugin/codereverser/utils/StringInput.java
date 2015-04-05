package com.softserveinc.plugin.codereverser.utils;

import org.eclipse.core.resources.IStorage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public class StringInput implements IStorageEditorInput {
    private IStorage storage;

    public StringInput(IStorage storage) {
        this.storage = storage;
    }

    public boolean exists() {
        return true;
    }

    public ImageDescriptor getImageDescriptor() {
        return null;
    }

    public String getName() {
        return storage.getName();
    }

    public IPersistableElement getPersistable() {
        return null;
    }

    public IStorage getStorage() {
        return storage;
    }

    public String getToolTipText() {
        String storageName = storage.getName();
        String resourceName = storageName.substring(0, storageName.indexOf(' '));
        return "Reversed file: " + resourceName;
    }

    public Object getAdapter(Class adapter) {
        return null;
    }
}