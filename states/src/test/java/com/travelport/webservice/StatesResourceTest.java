package com.travelport.webservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.travelport.common.Constants;
import com.travelport.json.Response;
import com.travelport.json.RestResponse;
import com.travelport.json.Result;

public class StatesResourceTest {

	private StatesResource resource;
	
	@Before
	public void setUp() throws Exception {
		resource = new StatesResource();
	}

	@Test
	public void testGetPeachStates() {
		StatesResource resource = new StatesResource();
		Response response = resource.getPeachStates();
		assertNotNull(response);
		RestResponse restResponse = response.getRestResponse();
		assertNotNull(restResponse);
		Result[] results = restResponse.getResult();
		assertNotNull(results);
		assertEquals("Unexpected number of states returned", 2, results.length);
		String firstStateName = results[0].getName();
		String secondStateName = results[1].getName();
		assertNotNull(firstStateName);
		assertNotNull(secondStateName);
		assertTrue("Unexpected state name found", 
				Constants.ALABAMA.equalsIgnoreCase(firstStateName) || Constants.ALABAMA.equalsIgnoreCase(secondStateName));
		assertTrue("Unexpected state name found", 
				Constants.GEORGIA.equalsIgnoreCase(firstStateName) || Constants.GEORGIA.equalsIgnoreCase(secondStateName));
	}
	
	@Test
	public void testGetAllStatesExcept() {
		StatesResource resource = new StatesResource();
		Response response = resource.getAllStatesExcept(Constants.GEORGIA);
		assertNotNull(response);
		RestResponse restResponse = response.getRestResponse();
		assertNotNull(restResponse);
		Result[] results = restResponse.getResult();
		assertNotNull(results);
		assertEquals("Unexpected number of states returned", 55, results.length);
		
		for (Result result : results) {
			assertFalse("Unexpected state name found", Constants.GEORGIA.equalsIgnoreCase(result.getName()));
		}
	}
	
    @After
    public void tearDown() throws Exception {
    	resource = null;
    }
}
