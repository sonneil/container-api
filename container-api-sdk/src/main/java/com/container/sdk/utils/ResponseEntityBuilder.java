package com.container.sdk.utils;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.type.ArrayType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class ResponseEntityBuilder<T> {

	private String url;
	private HttpMethod method;
	private Serializable request;
	private Object[] uriVariables = new Object[0];
	private MultiValueMap<String, String> headers;
	private Class<?> targetClass;
	private Boolean bodyCollection;
	
	public ResponseEntityBuilder<T> setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public ResponseEntityBuilder<T> setMethod(HttpMethod method) {
		this.method = method;
		return this;
	}
	
	public ResponseEntityBuilder<T> setRequest(Serializable request) {
		this.request = request;
		return this;
	}
	
	public ResponseEntityBuilder<T> setHeaders(MultiValueMap<String, String> headers) {
		this.headers = new LinkedMultiValueMap<String, String>(headers);
		return this;
	}
	
	public ResponseEntityBuilder<T> setUriVariables(Object... uriVariables) {
		this.uriVariables = uriVariables;
		return this;
	}
	
	public ResponseEntityBuilder<T> setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
		return this;
	}

	public ResponseEntityBuilder<T> setBodyCollection(Boolean bodyCollection) {
		this.bodyCollection = bodyCollection;
		return this;
	}

	public ResponseEntity<T> build() {
		validateRequiredParams();
		
		RestTemplate rt = new RestTemplate();
		rt.setMessageConverters(createConverters());
		@SuppressWarnings("unchecked")
		HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntity();
				
		return doExchange(rt, requestEntity, bodyCollection); 
	}
	
	public ResponseEntity<T> build(Boolean isBodyCollection) {
		this.bodyCollection = isBodyCollection;
		return build();
	}		
	
	private void validateRequiredParams() {
		if(targetClass == null || (url == null)) {
			throw new IllegalArgumentException("Nor targetClass or uri/url can be null");
		}
	}
	
	private List<HttpMessageConverter<?>> createConverters(){
        final List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new FormHttpMessageConverter());
        converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        converters.add(new MappingJackson2HttpMessageConverter());
        converters.add(new ResourceHttpMessageConverter());
        return converters;
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private HttpEntity createRequestEntity() {
		HttpEntity requestEntity;
		if(headers != null) {
			if(request != null) {
				requestEntity = new HttpEntity(request, headers);
			} else {
				requestEntity = new HttpEntity(headers);
			}
		} else {
			requestEntity = new HttpEntity(request);
		}
		return requestEntity;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ResponseEntity<T> doExchange(RestTemplate rt, HttpEntity requestEntity, Boolean isBodyCollection) {
		
		if(isBodyCollection){
			ResponseEntity<T> aux = getResponseAsVector(rt, requestEntity);
			List<T> body = new ArrayList<T>();
			for(int i = 0; i < ((T[])aux.getBody()).length; i++) {
				body.add(((T[])aux.getBody())[i]);
			}
			return new ResponseEntity<T>((T) body, aux.getStatusCode());
			
		}else{
			return (ResponseEntity<T>) rt.exchange(url, method, requestEntity, targetClass, uriVariables);
		}
	}
	
	@SuppressWarnings({ "unchecked" })
	private ResponseEntity<T> getResponseAsVector(RestTemplate rt, HttpEntity<MultiValueMap<String, String>> requestEntity) {
		ArrayType arrayType = TypeFactory.defaultInstance().constructArrayType(targetClass);
		Class<?> c = arrayType.getRawClass();
		ResponseEntity<T> aux =  (ResponseEntity<T>) rt.exchange(url, method, requestEntity, c, uriVariables);
		return aux;
	}

}
