package com.hit.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.hit.dm.Car;
import com.hit.server.Request;
import com.hit.server.Request.Body;
import com.hit.server.Request.Header;
import com.hit.server.Response;

public class AppModel implements Model {
	private PropertyChangeSupport support;
	private CarRentalClient client;
	private List<Car> carList = new ArrayList<>();
	private Header header = new Header();
	private Body body = new Body(carList, "");
	private Request request = new Request(header, body);
	private String requestString;
	private Response response = new Response(header, body);
	
	public AppModel() {
		support = new PropertyChangeSupport(this);
		client = new CarRentalClient(12346);
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		support.addPropertyChangeListener(propertyChangeListener);
	}
	
	//Create type of messeges to the server
	public List<Car> getAll() {
		request.getHeader().setAction("Get-all");
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public void updateCar(Car car, Car updatedCar) {
		request.getHeader().setAction("Update-car");
		carList.add(car);
		carList.add(updatedCar);
		request.getBody().setCarList(carList);
		requestString = writeRequest(request);
		client.sendRequest(request);
	}
	
	public void saveCar(Car car) {
		request.getHeader().setAction("Save-car");
		carList.add(car);
		request.getBody().setCarList(carList);
		requestString = writeRequest(request);
		client.sendRequest(request);
	}
	
	public void deleteCar(Car car) {
		request.getHeader().setAction("Delete-car");
		carList.add(car);
		request.getBody().setCarList(carList);
		requestString = writeRequest(request);
		client.sendRequest(request);
	}
	
	public List<Car> searchCarByCompany(String pat) {
		request.getHeader().setAction("Search-carsByCompany");
		request.getBody().setPat(pat);
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> searchCarByModel(String pat) {
		request.getHeader().setAction("Search-carsByModel");
		request.getBody().setPat(pat);
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> searchCarByYear(String pat) {
		request.getHeader().setAction("Search-carsByYear");
		request.getBody().setPat(pat);
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> searchCarByPrice(String pat) {
		request.getHeader().setAction("Search-carsByPrice");
		request.getBody().setPat(pat);
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> sortCarByCompany() {
		request.getHeader().setAction("Sort-carByCompany");
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> sortCarByModel() {
		request.getHeader().setAction("Sort-carByModel");
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> sortCarByYear() {
		request.getHeader().setAction("Sort-carByYear");
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public List<Car> sortCarByPrice() {
		request.getHeader().setAction("Sort-carByPrice");
		requestString = writeRequest(request);
		client.sendRequest(request);
		response = readResponse(client.getResponse());
		carList = response.getBody().getCarList();
		return carList;
	}
	
	public void sortCurrentCarByCompany() {
		request.getHeader().setAction("SortCurrent-carByCompany");
		requestString = writeRequest(request);
	}
	
	public void sortCurrentCarByModel() {
		request.getHeader().setAction("SortCurrent-carByModel");
		requestString = writeRequest(request);
	}
	
	public void sortCurrentCarByYear() {
		request.getHeader().setAction("SortCurrent-carByYear");
		requestString = writeRequest(request);
	}
	
	public void sortCurrentCarByPrice() {
		request.getHeader().setAction("SortCurrent-carByPrice");
		requestString = writeRequest(request);
	}
	
	public String writeRequest(Request request) {
		String reqStr = null;
		Gson gson = new Gson();
		reqStr = gson.toJson(request);	
		return reqStr;
	}
	
	public Request readRequest(String stringRequest) {
		Gson gson = new Gson();
		request = gson.fromJson(stringRequest, Request.class);
		return request;
	}
	
	public Response readResponse(String stringResponse) {
		Gson gson = new Gson();
		response = gson.fromJson(stringResponse, Response.class);
		return response;
	}
}
