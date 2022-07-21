package com.container.sdk.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface RestService {

	default String getServlet() {		
		return null;
	} 
	
	default <T> List<T> getBody(ResponseEntity<List<T>> response) throws Exception {
		if (HttpStatus.OK.equals(response.getStatusCode())) {
			return response.getBody();
		} else {
			if (HttpStatus.NO_CONTENT.equals(response.getStatusCode())) {
				return null;
			} else {
				throw new Exception(response.getStatusCode().toString());
			}
		}
	}
	
	default <T> T getBodyForResultOne(ResponseEntity<T> response) throws Exception {

		if (HttpStatus.OK.equals(response.getStatusCode())) {
			return response.getBody();
		} else {
			if (HttpStatus.NO_CONTENT.equals(response.getStatusCode())) {
				return null;
			} else {
				throw new Exception(response.getStatusCode().toString());
			}
		}
	}
	
	public <T> List<T> getResultList(Class<T> clazz, String endPoint, Object... uriVariables) throws Exception;
	public <T> T getResultOne(Class<T> clazz, String endPoint, Object... uriVariables) throws Exception;
	public <T> List<T> postResultList(Class<T> clazz, String endPoint, Serializable request, Object... uriVariables) throws Exception;
	public <T> T postResultOne(Class<T> clazz, String endPoint, Serializable request, Object... uriVariables) throws Exception;
	public <T> List<T> putResultList(Class<T> clazz, String endPoint, Serializable request, Object... uriVariables) throws Exception;
	public <T> T putResultOne(Class<T> clazz, String endPoint, Serializable request, Object... uriVariables) throws Exception;
	public <T> List<T> getResultListWithHeaders(Class<T> clazz, String endPoint, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception;
	public <T> T getResultOneWithHeaders(Class<T> clazz, String endPoint, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception;
	public <T> List<T> postResultListWithHeaders(Class<T> clazz, String endPoint, Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception;
	public <T> T postResultOneWithHeaders(Class<T> clazz, String endPoint, Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception;
	public <T> List<T> putResultListWithHeaders(Class<T> clazz, String endPoint, Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception;
	public <T> T putResultOneWithHeaders(Class<T> clazz, String endPoint, Serializable request, MultiValueMap<String, String> headers, Object... uriVariables) throws Exception;
}
