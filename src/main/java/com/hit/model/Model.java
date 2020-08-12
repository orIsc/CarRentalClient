package com.hit.model;

import com.hit.server.Request;

public interface Model {
	public String writeRequest(Request request);
	public Request readRequest(String stringRequest);
}
