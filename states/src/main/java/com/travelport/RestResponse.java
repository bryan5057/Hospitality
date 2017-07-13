package com.travelport;

public class RestResponse {

	private String[] messages;
	private Result[] result;
	
	/**
	 * @return the messages in the response
	 */
	public String[] getMessages() {
		return messages;
	}
	/**
	 * @param messages the messages to set
	 */
	public void setMessages(String[] messages) {
		this.messages = messages;
	}
	/**
	 * @return the result
	 */
	public Result[] getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(Result[] result) {
		this.result = result;
	}
}
