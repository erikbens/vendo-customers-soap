package com.loyaltypartner.vendocustomers.soap.common;

import com.loyaltypartner.vendocustomers.model.ResponseStatus;

public class ResponseBuilderHelper {
	
	public static ResponseStatus getResponseStatus(ResponseStatusCode statusCode) {
		ResponseStatus status = new ResponseStatus();

		switch (statusCode) {
		case SYSTEM_AUTHENTICATION_FAILED:
			status.setErrorCode("0001");
			status.setErrorMessage("Authentication for requesting system failed.");
			break;
		case INTERNAL_SERVER_ERROR:
			status.setErrorCode("9999");
			status.setErrorMessage("Internal server error.");
			break;
		case NO_ERROR:
			status.setErrorCode("0000");
			break;
		default:
			break;
		}
		
		return status;
	}

}
