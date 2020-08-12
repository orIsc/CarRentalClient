package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import com.hit.server.Request;

public class CarRentalClient {
    
	private ObjectInputStream reader;
	private ObjectOutputStream writer;
	private String response;
	private int port;
	private Socket socket;
	
	public CarRentalClient(int port) {
		this.port = port;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void	closeConnection()
	{
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void	connectToServer() {
		try {
			socket = new Socket("127.0.0.1", this.port);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public String sendRequest(Request request) {
    	String reqString = null;
    	connectToServer();
    	try {
			writer =new ObjectOutputStream(socket.getOutputStream());
			reader = new ObjectInputStream(socket.getInputStream());   
			AppModel appModel = new AppModel();
			String message = appModel.writeRequest(request);
			writer.writeObject(message);
			writer.flush();
			

			response = reader.readObject().toString();
			System.out.println(response);
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return reqString;
    }
}
