package com.container.sdk.service.impl;

import java.io.Serializable;
import java.util.List;

import com.container.sdk.dto.JwtTokenUser;
import com.container.sdk.endpoint.LoginEndpoint;
import com.container.sdk.service.RestService;
import com.container.sdk.utils.ResponseEntityBuilder;
import com.container.sdk.utils.UrlUtils;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Service
public class RestServiceImpl implements RestService {
		
	public <T> List<T> getResultList(Class<T> targetClass, String endPoint, Object... uriVariables) throws Exception {

		UrlUtils.UriVariablesFormatter(uriVariables);

		ResponseEntityBuilder<List<T>> responseBuilder = buildCommonResponseBuilderList(HttpMethod.GET, targetClass, endPoint, uriVariables);
		responseBuilder.setHeaders(getAuthorizationHeaderResponseBuilder());
		ResponseEntity<List<T>> response = responseBuilder.build();
		
		return getBody(response);
	}

	public <T> T getResultOne(Class<T> targetClass, String endPoint, Object... uriVariables) throws Exception {

		UrlUtils.UriVariablesFormatter(uriVariables);

		ResponseEntityBuilder<T> responseBuilder = buildCommonResponseBuilderOne(HttpMethod.GET, targetClass, endPoint, uriVariables);
		responseBuilder.setHeaders(getAuthorizationHeaderResponseBuilder());
		ResponseEntity<T> response = responseBuilder.build();
		
		return getBodyForResultOne(response);
	}

	public <T> List<T> postResultList(Class<T> targetClass, String endPoint,
			Serializable request, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<List<T>> responseBuilder = buildCommonResponseBuilderList(HttpMethod.POST, targetClass, endPoint, uriVariables);
		responseBuilder.setRequest(request);
		responseBuilder.setHeaders(getAuthorizationHeaderResponseBuilder());
		ResponseEntity<List<T>> response = responseBuilder.build();
		
		return getBody(response);		
	}
	
	public <T> T postResultOne(Class<T> targetClass, String endPoint, Serializable request, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<T> responseBuilder = buildCommonResponseBuilderOne(HttpMethod.POST, targetClass, endPoint, uriVariables);
		responseBuilder.setRequest(request);
		responseBuilder.setHeaders(getAuthorizationHeaderResponseBuilder());
		ResponseEntity<T> response = responseBuilder.build();
		
		return getBodyForResultOne(response);
	}
	
	public <T> List<T> putResultList(Class<T> targetClass, String endPoint,
			Serializable request, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<List<T>> responseBuilder = buildCommonResponseBuilderList(HttpMethod.PUT, targetClass, endPoint, uriVariables);
		responseBuilder.setRequest(request);
		responseBuilder.setHeaders(getAuthorizationHeaderResponseBuilder());
		ResponseEntity<List<T>> response = responseBuilder.build();
		
		return getBody(response);		
	}
	
	public <T> T putResultOne(Class<T> targetClass, String endPoint, Serializable request, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<T> responseBuilder = buildCommonResponseBuilderOne(HttpMethod.PUT, targetClass, endPoint, uriVariables);
		responseBuilder.setRequest(request);
		responseBuilder.setHeaders(getAuthorizationHeaderResponseBuilder());
		ResponseEntity<T> response = responseBuilder.build();
		
		return getBodyForResultOne(response);
	}
	
	private <T> ResponseEntityBuilder<T> buildCommonResponseBuilderOne(HttpMethod method, Class<T> targetClass, String endPoint, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<T> responseBuilder = new ResponseEntityBuilder<T>()
				.setBodyCollection(false)
				.setMethod(method)
				.setTargetClass(targetClass)
				.setUrl(UrlUtils.getEndpoint(getServlet(), endPoint));
		if (uriVariables != null && uriVariables.length > 0) {
			responseBuilder = responseBuilder.setUriVariables(uriVariables);
		}
		
		return responseBuilder;
	}
	
	private <T> ResponseEntityBuilder<List<T>> buildCommonResponseBuilderList(HttpMethod method, Class<T> targetClass, String endPoint, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<List<T>> responseBuilder = new ResponseEntityBuilder<List<T>>()
				.setBodyCollection(true)
				.setMethod(method)
				.setTargetClass(targetClass)
				.setUrl(UrlUtils.getEndpoint(getServlet(), endPoint));
		if (uriVariables != null && uriVariables.length > 0) {
			responseBuilder = responseBuilder.setUriVariables(uriVariables);
		}
		
		return responseBuilder;
	}
	
	public <T> List<T> getResultListWithHeaders(Class<T> targetClass, String endPoint, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception {

		UrlUtils.UriVariablesFormatter(uriVariables);

		ResponseEntityBuilder<List<T>> responseBuilder = buildCommonResponseBuilderList(HttpMethod.GET, targetClass, endPoint, uriVariables);
		headers.addAll(getAuthorizationHeaderResponseBuilder());
		responseBuilder.setHeaders(headers);
		ResponseEntity<List<T>> response = responseBuilder.build();
		
		return getBody(response);
	}
	
	public <T> T getResultOneWithHeaders(Class<T> targetClass, String endPoint, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception {

		UrlUtils.UriVariablesFormatter(uriVariables);

		ResponseEntityBuilder<T> responseBuilder = buildCommonResponseBuilderOne(HttpMethod.GET, targetClass, endPoint, uriVariables);
		headers.addAll(getAuthorizationHeaderResponseBuilder());
		responseBuilder.setHeaders(headers);
		ResponseEntity<T> response = responseBuilder.build();
		
		return getBodyForResultOne(response);
	}

	public <T> List<T> postResultListWithHeaders(Class<T> targetClass, String endPoint,
			Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<List<T>> responseBuilder = buildCommonResponseBuilderList(HttpMethod.POST, targetClass, endPoint, uriVariables);
		headers.addAll(getAuthorizationHeaderResponseBuilder());
		responseBuilder.setHeaders(headers);
		responseBuilder.setRequest(request);
		ResponseEntity<List<T>> response = responseBuilder.build();
		
		return getBody(response);		
	}
	
	public <T> T postResultOneWithHeaders(Class<T> targetClass, String endPoint, Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<T> responseBuilder = buildCommonResponseBuilderOne(HttpMethod.POST, targetClass, endPoint, uriVariables);
		headers.addAll(getAuthorizationHeaderResponseBuilder());
		responseBuilder.setHeaders(headers);
		responseBuilder.setRequest(request);
		ResponseEntity<T> response = responseBuilder.build();
		
		return getBodyForResultOne(response);
	}
	
	public <T> List<T> putResultListWithHeaders(Class<T> targetClass, String endPoint,
			Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<List<T>> responseBuilder = buildCommonResponseBuilderList(HttpMethod.PUT, targetClass, endPoint, uriVariables);
		headers.addAll(getAuthorizationHeaderResponseBuilder());
		responseBuilder.setHeaders(headers);
		responseBuilder.setRequest(request);
		ResponseEntity<List<T>> response = responseBuilder.build();
		
		return getBody(response);		
	}
	
	public <T> T putResultOneWithHeaders(Class<T> targetClass, String endPoint, Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception {
		
		ResponseEntityBuilder<T> responseBuilder = buildCommonResponseBuilderOne(HttpMethod.PUT, targetClass, endPoint, uriVariables);
		headers.addAll(getAuthorizationHeaderResponseBuilder());
		responseBuilder.setHeaders(headers);
		responseBuilder.setRequest(request);
		ResponseEntity<T> response = responseBuilder.build();
		
		return getBodyForResultOne(response);
	}

	private MultiValueMap<String, String> getAuthorizationHeaderResponseBuilder() {
		SecurityContext sc = SecurityContextHolder.getContext();
		String token = null;
		try {
			token = ((JwtTokenUser)sc.getAuthentication().getPrincipal()).getAuthorizationToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MultiValueMap<String, String> headers = new org.springframework.http.HttpHeaders();
		headers.add(LoginEndpoint.AUTHORIZATION_TOKEN, token);
		return headers;
	}

}
