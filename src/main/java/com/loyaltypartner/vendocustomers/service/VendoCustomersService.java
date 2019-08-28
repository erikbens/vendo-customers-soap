package com.loyaltypartner.vendocustomers.service;

import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.loyaltypartner.vendocustomers.model.SoapUser;
import com.loyaltypartner.vendocustomers.model.SystemAuthentication;
import com.loyaltypartner.vendocustomers.model.VendoCustomer;
import com.loyaltypartner.vendocustomers.model.VendoCustomersRequest;
import com.loyaltypartner.vendocustomers.model.VendoCustomersResponse;
import com.loyaltypartner.vendocustomers.soap.common.ResponseBuilderHelper;
import com.loyaltypartner.vendocustomers.soap.common.ResponseStatusCode;

@Service
@PropertySource("classpath:soap-security.properties")
public class VendoCustomersService {
	
	private static final Logger LOG = LoggerFactory.getLogger(VendoCustomersService.class);
	
	@Autowired
	private Environment env;
	
	@Value("#{'${vendo-customers-sources}'.split(',')}")
	private List<String> sources;
	
	public VendoCustomersResponse handleVendoCustomerRequest(VendoCustomersRequest request) {
		VendoCustomersResponse response = new VendoCustomersResponse();
		
		try {
			SystemAuthentication authentication = request.getSystemAuthentication();
			SoapUser user = this.isSystemAccessAllowed(authentication.getSource(), authentication.getUsername(), authentication.getPassword());
			if (user == null) {
				response.setResponseStatus(ResponseBuilderHelper.getResponseStatus(ResponseStatusCode.SYSTEM_AUTHENTICATION_FAILED));
				return response;
			}
			
			ZonedDateTime lastUpdatedAt = request.getLastUpdatedAt().toGregorianCalendar().toZonedDateTime();
			VendoCustomer elokCustomer = request.getRequestDetails();
			
			VendoCustomer newCustomer = this.updateVendoCustomerInKto(elokCustomer, lastUpdatedAt);
			
			response.setResponseDetails(newCustomer);
			response.setResponseStatus(ResponseBuilderHelper.getResponseStatus(ResponseStatusCode.NO_ERROR));
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getLocalizedMessage());
			response = new VendoCustomersResponse();
			response.setResponseStatus(ResponseBuilderHelper.getResponseStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR));
		}
		
		return response;
	}
	
	private SoapUser isSystemAccessAllowed(String source, String username, String password) {
		LOG.info("System " + source + " is requesting access with user=" + username + " and password.");
		if (sources.contains(source)) {
			try {
				String localUserName = env.getProperty(getUsername(source));
				String localPassword = env.getProperty(getPasswordName(source));
				if (localUserName.equals(username) && localPassword.equals(password)) {
					LOG.info("System " + source + " authenticated.");
					return new SoapUser(username, source);
				}
			} catch (Exception e) {
				return null;
			}
		}
		LOG.info("Authentication failed.");
		return null;
	}
	
	private VendoCustomer updateVendoCustomerInKto(VendoCustomer newCustomer, ZonedDateTime lastUpdatedAt) {
		return null;
	}
	
	private String getPasswordName(String source) {
		return "vendo-customers-" + source + "-pw";
	}
	
	private String getUsername(String source) {
		return "vendo-customers-" + source + "-user";
	}

}
