package com.loyaltypartner.vendocustomers.soap;

import java.util.List;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class VendoCustomerServiceConfig extends WsConfigurerAdapter {
	
	@Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
        validatingInterceptor.setValidateRequest(false);
        validatingInterceptor.setValidateResponse(false);
        validatingInterceptor.setXsdSchema(vendoCustomersSchema());
        interceptors.add(validatingInterceptor);
    }
	
	@Bean
	public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<MessageDispatcherServlet>(servlet, "/ws/*");
	}
	
	@Bean(name = "vendo-customers")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema vendoCustomersSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("VendoCustomerPort");
		definition.setLocationUri("/ws");
		definition.setTargetNamespace("http://loyaltypartner.com/soap/lps-vendo-customers");
		definition.setSchema(vendoCustomersSchema);
		return definition;
	}
	
	@Bean
	public XsdSchema vendoCustomersSchema() {
		return new SimpleXsdSchema(new ClassPathResource("vendo-customers.xsd"));
	}
	
}
