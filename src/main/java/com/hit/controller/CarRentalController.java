package com.hit.controller;

import java.beans.PropertyChangeEvent;

import com.hit.model.Model;
import com.hit.view.View;

public class CarRentalController implements Controller{

	private Model model;
	private View view;
	
	public CarRentalController(Model model, View view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		
	}
}
