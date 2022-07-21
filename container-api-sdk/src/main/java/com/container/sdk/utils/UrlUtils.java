package com.container.sdk.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Configuration
@PropertySources({
		@PropertySource(name="sdk.properties", value = {"classpath:sdk.properties"}, ignoreResourceNotFound=true),
})
public class UrlUtils {
	
	protected static final Logger logger = Logger.getLogger(UrlUtils.class.getName());

	@Autowired
	private Environment env;
	
	private static String url = null;
	private static UrlUtils instance = null;
	
	public UrlUtils() {
		instance = this;
	}
	
	/**
	 * 
	 * @param servlet: must be without /
	 * @param path
	 * @return
	 */
	public static String getEndpoint(String servlet, String path) {

		String endpoint = getUrl();
		String argument = endpoint + ((servlet != null) ? servlet : "");
		argument += treatmentForUrl(path);
		logger.log(Level.INFO, "Call to url: " + argument);
		return argument;
	}

	public static String getEndpoint(String path) {

		String endpoint = getUrl();		
		endpoint +=  treatmentForUrl(path);
		logger.log(Level.INFO, "Call to url: " + endpoint);
		return endpoint;
	}

	private static String treatmentForUrl(String value) {

		if (!value.startsWith("/")) {
			value = "/" + value;
		} 
		return value;
	}
	
	private static String getUrl() {
		
		if(url == null) {
			try {
				url = getEndpointFromProps();				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return url;
	}

	public static MultiValueMap<String, String> getHeaders() {
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Date", getHeaderDate());
		headers.add("User-Agent", RestConfigEnum.USER_AGENT.toString());
		return headers;
	}

	public static void UriVariablesFormatter(Object... uriVariables) {
		if (uriVariables != null) {
			for (int i = 0; i < uriVariables.length; i++) {
				if (uriVariables[i] instanceof List) {
					StringBuilder multipleVariableParam = new StringBuilder();
					for (Object variable : (List) uriVariables[i]) {
						multipleVariableParam.append(variable.toString()).append(",");
					}
					multipleVariableParam.deleteCharAt(multipleVariableParam.length() - 1);
					uriVariables[i] = multipleVariableParam.toString();
				}
			}
		}
	}

	private static String getHeaderDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.US);
		String headerDate;

		Calendar fechaFuturo = Calendar.getInstance();
		fechaFuturo.add(Calendar.MINUTE, 10);
		Date date = fechaFuturo.getTime();
		headerDate = sdf.format(date);
		return headerDate;

	}

	public static String addParametersToUrl(String url, Object dto) {
		if (dto != null) {
			Field[] fields = dto.getClass().getDeclaredFields();
			for (Field field : fields) {
				String attributeName = field.getName();
				String getterName = "get" + toUpperCamelCase(attributeName);
				try {
					Object value = dto.getClass().getDeclaredMethod(getterName).invoke(dto);
					if (value != null) {
						if (value.getClass().isPrimitive() || Number.class.isInstance(value)
								|| String.class.isInstance(value)) {
							url = url.replaceFirst("\\{" + attributeName + "\\}",
									dto.getClass().getDeclaredMethod(getterName).invoke(dto).toString());
						} else {
							url = addParametersToUrl(url, value);
						}
					} else {
						url = url.replaceFirst("\\{" + attributeName + "\\}", "null");
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		return url;

	}

	private static String toUpperCamelCase(String string) {
		return string.substring(0, 1).toUpperCase() + string.substring(1);
	}

	private static String getEndpointFromProps() throws IOException {
		if (instance != null && instance.env.getProperty("container.url") != null) {
			return instance.env.getProperty("container.url");
		} else {
			throw new RuntimeException("Required property not founded in sdk.properties: \"container.url\"");
		}
	}
	
	private enum RestConfigEnum {

		CONTENT_TYPE("application/json;charset=UTF-8"), 
		USER_AGENT("container-sdk");

		private String value;

		RestConfigEnum(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return value;
		}
	}

}