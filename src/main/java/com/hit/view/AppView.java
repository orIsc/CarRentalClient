package com.hit.view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class AppView implements View {

	private PropertyChangeSupport support;
	private DisplayApp app;

	public AppView() {
		support = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		support.addPropertyChangeListener(propertyChangeListener);
	}
	
	public void start() {//Starts the view
		app = new DisplayApp(support);

	}

}
