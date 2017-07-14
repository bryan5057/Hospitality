package com.travelport.webservice;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.travelport.common.Constants;
import com.travelport.json.Response;
import com.travelport.json.RestResponse;
import com.travelport.json.Result;

public class StatesResourceTest {

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
}
