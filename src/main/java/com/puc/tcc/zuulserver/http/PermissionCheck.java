package com.puc.tcc.zuulserver.http;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class PermissionCheck {

	public static boolean verifyToken(String path, String token) {
		try {
			final String uri = "http://localhost:9090/oauth".concat(path);

			 CloseableHttpClient client = HttpClients.custom().build();

		        HttpUriRequest request = RequestBuilder.get()
		                .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
		                .setHeader("x-access-token", token)
		                .build();

		        CloseableHttpResponse response;
				
					response = client.execute(request);
					
			        List<Header> httpHeaders = Arrays.asList(response.getAllHeaders());
			        httpHeaders.stream().forEach(System.out::println);
			        
			        if(response.getStatusLine().getStatusCode() == 200) {
			        	return true;
			        }
				
				} catch (Exception e) {
					//TODO Criar excpetion
					return false;
				}
			

			return false; //result.getStatusCode().equals(HttpStatus.OK);
	}

}
