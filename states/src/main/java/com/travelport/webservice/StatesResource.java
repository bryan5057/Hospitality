package com.travelport.webservice;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.travelport.common.Constants;
import com.travelport.json.Response;
import com.travelport.json.RestResponse;
import com.travelport.json.Result;

/**
 * Root resource for our web service (exposed at "/get" path)
 */
@Path("/get")
public class StatesResource {

    /**
     * Method handling HTTP GET requests at the "/peachstates" path. The response will be returned
     * to the client as the "application/JSON" media type.
     *
     * @return Response
     */
    @GET
    @Path("/peachstates")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPeachStates() {
    	Response response = new Response();
    	
    	try {
    		// Retrieve all US states
			Response allStates = retrieveAllStates();
			
			if (allStates != null && allStates.getRestResponse() != null && allStates.getRestResponse().getResult() != null) {
				// Build list containing just Alabama and Georgia
				List<Result> peachStates = new ArrayList<Result>();
				for (Result state : allStates.getRestResponse().getResult()) {
					if (state != null) {
						if (Constants.ALABAMA.equalsIgnoreCase(state.getName()) || Constants.GEORGIA.equalsIgnoreCase(state.getName())) {
							peachStates.add(state);
						}
					}
				}
				
				// Build response to be returned
				RestResponse restResponse = buildRestResponse(peachStates);
				response.setRestResponse(restResponse);
			}
			else {
				response = getUnknownErrorResponse();
			}
		} catch (Exception e) {
			response = getUnknownErrorResponse();
		}
    	
        return response;
    }

    /**
     * Method handling HTTP GET requests at the "/allstatesexcept{statetoexclude}" path. The response will be returned
     * to the client as the "application/JSON" media type.
     *
     * @return Response
     */
    @GET
    @Path("/allstatesexcept/{statetoexclude}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStatesExcept(@PathParam("statetoexclude") String stateToExclude) {
    	Response response = new Response();
    	
    	try {
    		// Retrieve all US states
			Response allStates = retrieveAllStates();
			
			if (allStates != null && allStates.getRestResponse() != null && allStates.getRestResponse().getResult() != null) {
				// Build list containing all states except the excluded one
				List<Result> statesToReturn = new ArrayList<Result>();
				for (Result state : allStates.getRestResponse().getResult()) {
					if (state != null) {
						if (!stateToExclude.equalsIgnoreCase(state.getName())) {
							statesToReturn.add(state);
						}
					}
				}
				
				// Build response to be returned
				RestResponse restResponse = buildRestResponse(statesToReturn);
				response.setRestResponse(restResponse);
			}
			else {
				response = getUnknownErrorResponse();
			}
		} catch (Exception e) {
			response = getUnknownErrorResponse();
		}
    	
        return response;
    }
    
	/**
	 * 	Build the RestResponse object to return to user from list of results
	 * 
	 * @return RestResponse
	 */   
    private RestResponse buildRestResponse(List<Result> results) {
    	// Convert list into array to be returned
		Result[] resultsArray = new Result[results.size()];
		resultsArray = results.toArray(resultsArray);
		// Build rest response
		RestResponse restResponse = new RestResponse();
		restResponse.setMessages(new String[] {String.format(Constants.TOTAL_RECORDS_FOUND_MSG, results.size())});
		restResponse.setResult(resultsArray);
		return restResponse;
    }

	/**
	 * 	Build a response containing a polite error message indicating something went wrong
	 * 
	 * @return Response
	 */
    private Response getUnknownErrorResponse() {
    	Response response = new Response();
		RestResponse restResponse = new RestResponse();
		restResponse.setMessages(new String[] {Constants.UNKNOWN_ERROR_MSG});
		response.setRestResponse(restResponse);
		return response;
    }

	/**
	 * 	Retrieve all US states from services.groupkt.com
	 * 
	 * @return Response
	 */
	private Response retrieveAllStates() {
		Response allStatesResponse = new Response();
		try {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress(Constants.PROXY_HOST, Constants.PROXY_PORT));
			requestFactory.setProxy(proxy);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			allStatesResponse = restTemplate.getForObject(Constants.GROUPKT_ALL_US_STATES_URI, Response.class);
		} catch (RestClientException clientException) {
			// Unable to retrieve all states from Groupkt
			clientException.printStackTrace();
			throw clientException;
		}
		return allStatesResponse;
	}
}
