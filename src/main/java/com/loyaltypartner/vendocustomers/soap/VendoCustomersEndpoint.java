package com.loyaltypartner.vendocustomers.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.loyaltypartner.vendocustomers.model.VendoCustomersRequest;
import com.loyaltypartner.vendocustomers.model.VendoCustomersResponse;
import com.loyaltypartner.vendocustomers.service.VendoCustomersService;

@Endpoint
public class VendoCustomersEndpoint {

	private static final String NAMESPACE_URI = "http://loyaltypartner.com/soap/lps-vendo-customers";
	
	@Autowired
	private VendoCustomersService vendoCustomerService;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "VendoCustomersRequest")
	@ResponsePayload
	public VendoCustomersResponse createCustomer(@RequestPayload VendoCustomersRequest request) {
		return vendoCustomerService.handleVendoCustomerRequest(request);
	}
	
}
