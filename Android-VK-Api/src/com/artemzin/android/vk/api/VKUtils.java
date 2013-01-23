package com.artemzin.android.vk.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

import org.json.JSONObject;

/**
 * Class with utils methods
 * @author Artem Zinnatullin
 *
 */
public class VKUtils {
	
	/**
	 * Closing constructor, not needed
	 */
	private VKUtils() {
		
	}
	
	/**
	 * Converting input stream content to string
	 * @param is input stream to convert
	 * @return String with stream content
	 * @throws IOException if problems with reading input stream
	 */
	public static String convertStreamToString(InputStream is) throws IOException {
        InputStreamReader r = new InputStreamReader(is);
        StringWriter sw = new StringWriter();
        char[] buffer = new char[1024];
        try {
            for (int n; (n = r.read(buffer)) != -1;)
                sw.write(buffer, 0, n);
        }
        finally{
            try {
                is.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return sw.toString();
    }
	
	/**
	 * Checking JSON server response for errors
	 * @param json with server responce
	 * @throws VKException if it is vk error
	 */
	public static void checkJSONForVKError(JSONObject json) throws VKException {
        if(!json.isNull("error")){
            JSONObject errorJson = json.optJSONObject("error");
            int errorCode = errorJson.optInt("error_code");
            String message = errorJson.optString("error_msg");
            VKException e = new VKException(errorCode, message);
            // Needed when captcha problem
            if (errorCode == 14) {
            	VKCaptcha captcha = new VKCaptcha(errorJson.optString("captcha_sid"), errorJson.optString("captcha_img"));
            	e.attachCaptcha(captcha);
            }
            throw e;
        }
    }
	
	/**
	 * Unescaping some chars and symbols
	 * @param text to unescape
	 * @return unescaped string
	 */
	public static String unescape(String text){
        return text.replace("&amp;", "&").replace("&quot;", "\"").replace("<br>", "\n").replace("&gt;", ">").replace("&lt;", "<")
        .replace("&#39;", "'").replace("<br/>", "\n").replace("&ndash;","-").replace("&#33;", "!").trim();
    }
	
	/**
	 * Checking param for positive value
	 * @param paramName to display error
	 * @param param only Long or Integer are possible
	 * @throws IllegalArgumentException if value of param is not positive
	 */
	public static void checkToBePositiveValue(String paramName, Object param) throws IllegalArgumentException {
		if (param == null) {
			return;
		}
		if (param instanceof Long) {
			Long paramLong = (Long) param;
			if (paramLong < 0) {
				throw new IllegalArgumentException(paramName + " param should be positive");
			}
		}
		if (param instanceof Integer) {
			Integer paramInteger = (Integer) param;
			if (paramInteger < 0) {
				throw new IllegalArgumentException(paramName + " param should be positive");
			}
		}
		
	}
	
	/**
	 * Converting array to string with commas between array elements
	 * @param array to convert
	 * @return array converted to string
	 */
	public static <T> String arrayToParams(T[] array) {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			if (i != array.length - 1) {
				buffer.append(array[i]).append(",");
			} else {
				buffer.append(array[i]);
			}
		}
		return buffer.toString();
	}

    /**
     * Converting List to string with commas between list elements
     * @param list to convert
     * @return converted string
     */
    public static <E> String listToParams(List<E> list) {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() -1)
                buffer.append(list.get(i).toString()).append(",");
            else
                buffer.append(list.get(i).toString());
        }
        return buffer.toString();
    }
}
