package com.travelport;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Root resource (exposed at "/get" path)
 */
@Path("/get")
public class StatesResource {

    /**
     * Method handling HTTP GET requests. The response will be sent
     * to the client as the "application/JSON" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/southern")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSouuthernStates() {
    	Response returnVal = new Response();
    	
    	// TODO: add JUnit tests
    	// TODO: run Sonar
    	
    	try {
			SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
			Proxy proxy= new Proxy(Type.HTTP, new InetSocketAddress(Constants.PROXY_HOST, Constants.PROXY_PORT));
			requestFactory.setProxy(proxy);
			RestTemplate restTemplate = new RestTemplate(requestFactory);
			
			Response allStatesResponse = restTemplate.getForObject(Constants.GROUPKT_ALL_US_STATES_URI, Response.class);
			
			if (allStatesResponse != null && allStatesResponse.getRestResponse() != null) {
				Result[] allStates = allStatesResponse.getRestResponse().getResult();
				List<Result> southernStates = new ArrayList<Result>();
				for (int i = 0; i < allStates.length; i++) {
					Result state = allStates[i];
					if (state != null) {
						if (Constants.ALABAMA.equalsIgnoreCase(state.getName()) || Constants.GEORGIA.equalsIgnoreCase(state.getName())) {
							southernStates.add(state);
						}
					}
				}
			
				Result[] southernStateArray = new Result[southernStates.size()];
				southernStateArray = southernStates.toArray(southernStateArray);

				RestResponse restResponse = new RestResponse();
				restResponse.setMessages(new String[] {String.format("Total [%d] records found.", southernStates.size())});
				restResponse.setResult(southernStateArray);
				returnVal.setRestResponse(restResponse);
			}
		} catch (Exception e) {
			RestResponse restResponse = new RestResponse();
			restResponse.setMessages(new String[] {Constants.UNKNOWN_ERROR_MSG});
			returnVal.setRestResponse(restResponse);
		}
    	
        return returnVal;
    }
}
