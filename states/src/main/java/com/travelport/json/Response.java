package com.travelport.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {

    @JsonProperty
    private RestResponse RestResponse;

    public RestResponse getRestResponse() {
        return this.RestResponse;
    }

    public void setRestResponse(RestResponse restResponse) {
        this.RestResponse = restResponse;
    }
}
