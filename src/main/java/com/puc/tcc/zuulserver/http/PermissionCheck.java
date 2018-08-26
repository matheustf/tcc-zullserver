package com.puc.tcc.zuulserver.http;

import java.util.Arrays;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.exception.ZuulException;

@Component
public class PermissionCheck {

	public static boolean verifyToken(String path, String token) throws ZuulException {
		try {
			final String uri = "http://localhost:9090/oauth".concat(path);

			 CloseableHttpClient client = HttpClients.custom().build();

		        HttpUriRequest request = RequestBuilder.get()
		                .setUri(uri)
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
					throw new ZuulException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), 500, "Problema no servidor. Tente conectar mais tarde");
				}

			return false;
	}

}
