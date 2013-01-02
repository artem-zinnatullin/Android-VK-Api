package com.artemzin.android.vk.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Container for request params
 * @author Artem Zinnatullin
 *
 */
public class VKRequestParams {
	
	/**
	 * Map with params
	 */
	private final HashMap<String, String> params;
	
	/**
	 * vk.com api method name
	 */
	public final String apiMethodName;
	
	{
		params = new HashMap<String, String>();
	}
	
	/**
	 * Creating new container with request params
	 * @param apiMethodName is name of vk.com api method needed
	 */
	public VKRequestParams(String apiMethodName) {
		this.apiMethodName = apiMethodName;
	}
	
	/**
	 * Puts new param to container
	 * @param paramName NOT NULL name of parameter
	 * @param paramValue NOT NULL parameter value
	 * @return true if param was added, else false
	 */
	public boolean putParam(String paramName, String paramValue) {
		if (paramName != null && paramName.length() != 0
				&& paramValue != null && paramValue.length() != 0) {
			params.put(paramName, paramValue);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Returns params as request string
	 * @return request optimized string with all params
	 */
	public String getParamsAsRequestString() {
		StringBuilder resultString = new StringBuilder();
		for (Entry<String, String> param: params.entrySet()) {
			if (resultString.length() != 0) {
				resultString.append("&");
			}
			try {
				resultString.append(param.getKey()).append("=").append(URLEncoder.encode(param.getValue(), "UTF-8"));
			} catch (UnsupportedEncodingException ignored) {
			}
		}
		return resultString.toString();
	}
}
