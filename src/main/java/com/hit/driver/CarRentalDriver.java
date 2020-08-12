package com.hit.driver;

import com.hit.controller.CarRentalController;
import com.hit.controller.Controller;
import com.hit.model.AppModel;
import com.hit.model.Model;
import com.hit.view.AppView;
import com.hit.view.View;

public class CarRentalDriver {

	public static void main(String[] args){  
		Model model = new AppModel();  
		View view = new AppView(); 
		Controller controller = new CarRentalController(model, view); 
		((AppModel)model).addPropertyChangeListener(controller); 
		((AppView)view).addPropertyChangeListener(controller); 
		view.start();
	}
}
