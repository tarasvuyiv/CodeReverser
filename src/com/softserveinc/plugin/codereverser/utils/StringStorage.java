package com.softserveinc.plugin.codereverser.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;

public class StringStorage implements IStorage {
    private String string;
    private String resourceName;
   
    public StringStorage(String input, String name) {
      this.string = input;
      this.resourceName = name;
    }
   
    public InputStream getContents() throws CoreException {
      return new ByteArrayInputStream(string.getBytes());
    }
   
    public IPath getFullPath() {
      return null;
    }
   
    public Object getAdapter(Class adapter) {
      return null;
    }
   
    public String getName() {
      return resourceName + " Reversed";
    }
   
    public boolean isReadOnly() {
      return true;
    }
  }
