package com.loyaltypartner.vendocustomers.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.loyaltypartner.vendocustomers.model.SystemAuthentication;
import com.loyaltypartner.vendocustomers.model.VendoCustomersRequest;
import com.loyaltypartner.vendocustomers.model.VendoCustomersResponse;
import com.loyaltypartner.vendocustomers.soap.common.ResponseBuilderHelper;
import com.loyaltypartner.vendocustomers.soap.common.ResponseStatusCode;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@ActiveProfiles("test")
public class VendoCustomersServiceTest {

	@Autowired
	private VendoCustomersService vendoCustomerService;

	@Test
	public void whenMissingElement_ThenStatus9999() {
		VendoCustomersRequest request = getValidVendoCustomersRequestWithAllAttributes();
		request.setSystemAuthentication(null);

		VendoCustomersResponse response = vendoCustomerService.handleVendoCustomerRequest(request);

		assertEquals(ResponseBuilderHelper.getResponseStatus(ResponseStatusCode.INTERNAL_SERVER_ERROR).getErrorCode(), response.getResponseStatus().getErrorCode());
	}

	@Test
	public void whenInvalidAuthentication_ThenStatus0001() {
		VendoCustomersRequest request = getValidVendoCustomersRequestWithAllAttributes();
		request.getSystemAuthentication().setPassword("");

		VendoCustomersResponse response = vendoCustomerService.handleVendoCustomerRequest(request);

		assertNotNull(response.getResponseStatus());
		assertEquals(ResponseBuilderHelper.getResponseStatus(ResponseStatusCode.SYSTEM_AUTHENTICATION_FAILED).getErrorCode(),
				response.getResponseStatus().getErrorCode());
	}

	@Test
	public void whenValidAuthentication_ThenStatus0000() {
		VendoCustomersRequest request = getValidVendoCustomersRequestWithAllAttributes();

		VendoCustomersResponse response = vendoCustomerService.handleVendoCustomerRequest(request);

		assertNotNull(response.getResponseStatus());
		assertEquals(ResponseBuilderHelper.getResponseStatus(ResponseStatusCode.NO_ERROR).getErrorCode(), response.getResponseStatus().getErrorCode());
	}

	private VendoCustomersRequest getValidVendoCustomersRequestWithAllAttributes() {
		VendoCustomersRequest request = new VendoCustomersRequest();

		SystemAuthentication auth = new SystemAuthentication();
		auth.setSource("elok");
		auth.setUsername("elok");
		auth.setPassword("1234");
		request.setSystemAuthentication(auth);
		try {
			request.setLastUpdatedAt(DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar.from(ZonedDateTime.now())));
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		return request;
	}

}
