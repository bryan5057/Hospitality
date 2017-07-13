package com.travelport;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

	@JsonProperty
	private RestResponse RestResponse;

	public RestResponse getRestResponse() {
		return RestResponse;
	}

	public void setRestResponse(RestResponse restResponse) {
		RestResponse = restResponse;
	}

	public Response(){

	}
}


