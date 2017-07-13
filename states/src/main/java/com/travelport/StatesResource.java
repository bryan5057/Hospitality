package com.travelport;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;

/**
 * Root resource (exposed at "/get" path)
 */
@Path("/get")
public class StatesResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/southern")
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
    	
    	ClientConfig config = new ClientConfig();
//    	config.property(ClientProperties.PROXY_URI, "http://atlproxy.tvlport.com:8080");
    	
    	Client client = ClientBuilder.newClient();
    	WebTarget webTarget = client.target("http://services.groupkt.com/state/get/USA/all");
    	Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    	//invocationBuilder.header("some-header", "true");
    	
    	RestResponse response = invocationBuilder.get(RestResponse.class);
    	//System.out.println(response.getStatus());
    	//Object entity = response.getEntity();

    	
        return "Got it!";
    }
}
