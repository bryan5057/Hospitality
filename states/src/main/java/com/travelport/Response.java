package com.travelport;

public class Response {

	  private RestResponse RestResponse;
	  
	  public RestResponse getRestResponse() {
	    return RestResponse;
	  }

	  public void setRestResponse(RestResponse restResponse) {
	    RestResponse = restResponse;
	  }

	  public Response(){
	    
	  }

	  @Override
	  public String toString() {
	    return "Response [RestResponse=" + RestResponse + "]";
	  }

	}


