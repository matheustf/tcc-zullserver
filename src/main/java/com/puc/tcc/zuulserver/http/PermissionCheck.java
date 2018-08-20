package com.puc.tcc.zuulserver.http;

import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PermissionCheck {

	public static boolean verifyToken(String token) {

		{
			final String uri = "http://localhost:9090/oauth";

			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.set("x-access-token", token);
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

			RestTemplate restTemplate = new RestTemplate();

			ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);

			System.out.println(result);

			return result.getStatusCode().equals(HttpStatus.OK);
		}
	}

}
