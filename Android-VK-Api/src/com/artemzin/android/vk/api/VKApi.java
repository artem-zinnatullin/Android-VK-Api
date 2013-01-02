package com.artemzin.android.vk.api;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

/**
 * Main Vk.com VKApi class
 * Use it for actions with api
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php">VK Api Documentation</a>
 */
public class VKApi {
	
	private final static String TAG;
	
	/**
	 * Main vk.com api url
	 */
	private final static String BASIC_API_URL;
	
	/**
	 * Default value for gzip compression is disabled
	 */
	public final static boolean GZIP_COMPRESSION_DEFAULT;
	
	/**
	 * Default query retry limit
	 */
	public final static int QUERY_RETRY_LIMIT_DEFAULT;
	
	/**
	 * Default connection timeout in millis
	 */
	public final static int CONNECTION_TIMEOUT_DEFAULT;
	
	static {
		TAG = "VKApi";
		BASIC_API_URL = "https://api.vk.com/method/";
		// By default, gzip compression is disabled
		// Because of on slow devices compression time is too long
		GZIP_COMPRESSION_DEFAULT = false;
		// By default, query reply limit is 3
		QUERY_RETRY_LIMIT_DEFAULT = 3;
		// By default, connection timeout will be 30 seconds
		CONNECTION_TIMEOUT_DEFAULT = 30000;
	}
	
	/**
	 * VKUser authorization token
	 */
	private final String accessToken;
	
	/**
	 * vk.com application id
	 */
	@SuppressWarnings("unused")
	private final long appId;
	
	/**
	 * Gzip compression enable/disable
	 */
	private boolean isGzipEnable;
	
	/**
	 * Limit of query retries
	 */
	private int queryRetryLimit;
	
	/**
	 * Connection timeout in millis
	 */
	private int connectionTimeout;
	
	{
		isGzipEnable = GZIP_COMPRESSION_DEFAULT;
		queryRetryLimit = QUERY_RETRY_LIMIT_DEFAULT;
		connectionTimeout = CONNECTION_TIMEOUT_DEFAULT;
	}
	
	/**
	 * Creating vk.com api instance
	 * @param accessToken user access token, needed for queries
	 * @param appId of application in vk.com
	 */
	public VKApi(String accessToken, long appId) {
		this.accessToken = accessToken;
		this.appId = appId;
	}
	
	/**
	 * Enabling/Disabling gzip compression for queries
	 * @param status true - gzip enabled, false - gzip disabled
	 */
	public void setGzipCompression(boolean status) {
		this.isGzipEnable = status;
	}
	
	/**
	 * @return gzip compression enable/disable
	 */
	public boolean getGzipCompressionStatus() {
		return this.isGzipEnable;
	}
	
	/**
	 * Setting query retry limit
	 * @param limit number of query retries
	 */
	public void setQueryRetryLimit(int limit) {
		this.queryRetryLimit = limit;
	}
	
	/**
	 * @return current query retry limit
	 */
	public int getQueryRetryLimit() {
		return this.queryRetryLimit;
	}
	
	/**
	 * @return current connection timeout in millis
	 */
	public int getConnectionTimeout() {
		return this.connectionTimeout;
	}
	
	/**
	 * Setting new connection timeout for network operations
	 * @param connectionTimeout in millis
	 * @throws Exception if connection timeout negative or zero
	 */
	public void setConnectionTimeout(int connectionTimeout) throws Exception {
		if (connectionTimeout < 0) {
			throw new Exception("Connection timeout could not be negative");
		} else if (connectionTimeout == 0) {
			throw new Exception("Connection timeout could not be zero");
		} else {
			this.connectionTimeout = connectionTimeout;
		}
	}
	
	/**
	 * Forms requestUrl from VKRequestParams and accessToken
	 * @param requestParams object with requesting params
	 * @return url with needed get params
	 */
	private String createRequestUrl(VKRequestParams requestParams) {
		StringBuilder requestUrl = new StringBuilder(BASIC_API_URL);
		requestUrl.append(requestParams.apiMethodName).append("?");
		requestUrl.append(requestParams.getParamsAsRequestString());
		if (requestUrl.length() != 0) {
			requestUrl.append("&");
		}
		requestUrl.append("access_token=").append(accessToken);
		return requestUrl.toString();
	}
	
	/**
	 * Sending request to vk.com api with needed params
	 * @param requestParams container with params to send
	 * @return JSONObject with server reply
	 * @throws Exception 
	 * @throws IOException
	 * @throws VKException if vk.com response contains error code
	 */
	private JSONObject sendRequest(VKRequestParams requestParams) throws Exception {
		final String requestUrl = createRequestUrl(requestParams);
		String response = null;
		Log.d(TAG, "Request url: " + requestUrl);
		for (int i = 1; i <= queryRetryLimit; i++) {
			if (i != 1) {
				Log.w(TAG, "Request was failed, trying again (" + i + ")");
			}
			try {
				response = sendRequestInternal(requestUrl);
				Log.d(TAG, "Server response: " + response);
				break;
			} catch (javax.net.ssl.SSLException e) {
				if (i == this.queryRetryLimit) {
					throw e;
				}
			} catch (java.net.SocketException e) {
				if (i == this.queryRetryLimit) {
					throw e;
				}
			}
		}
		JSONObject json = new JSONObject(response);
		VKUtils.checkJSONForVKError(json);
		return json;
	}
	
	/**
	 * Sending http request
	 * @param requestUrl is url with get params
	 * @return String with server response
	 * @throws IOException if problems with input stream from server response
	 * @throws Exception if problem with server response code, may be keep-alive problem
	 */
	private String sendRequestInternal(final String requestUrl) throws Exception {
		HttpURLConnection connection = null;
		// Server response code
		int responseCode;
		try {
			connection = (HttpURLConnection) new URL(requestUrl).openConnection();
			connection.setConnectTimeout(this.connectionTimeout);
			connection.setReadTimeout(this.connectionTimeout);
			connection.setUseCaches(false);
			connection.setDoOutput(false);
			connection.setDoInput(true);
			if (this.isGzipEnable) {
				connection.setRequestProperty("Accept-Encoding", "gzip");
			}
			responseCode = connection.getResponseCode();
			Log.d(TAG, "Server responce code: " + responseCode);
			// Because of using keep-alive, we could get responseCode == -1
			if (responseCode == -1) {
				throw new Exception("Got response code -1, may be http keep-alive problem");
			}
			InputStream is = new BufferedInputStream(connection.getInputStream(), 8192);
			final String contentEncoding = connection.getContentEncoding();
			if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
				is = new GZIPInputStream(is);
			}
			return VKUtils.convertStreamToString(is);
		}
		finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	/**
	 * Gets user`s friends
	 * @see <a href="http://vk.com/developers.php?o=-1&p=friends.get">VK Frieds api</a>
	 * @param userId null if want to get current authorized user`s friends, or user id of user, whose friends you want to get.
	 * @param fields to request.
	 *        If null, will get first_name, last_name, photo_medium, online
	 * @param lId id of friends list of current user
	 * @throws Exception 
	 * @throws VKException 
	 * @throws IOException 
	 */
	public ArrayList<VKUser> getFriends(Long userId, String[] fields, Long lId) throws Exception {
		VKRequestParams params = new VKRequestParams("friends.get");
		if (fields == null) {
			fields = new String[] {"first_name", "last_name", "photo_medium_rec", "online", "sex", "activity", "last_seen"};
		}
		StringBuilder fieldsAsString = new StringBuilder();
		for (int i = 0; i < fields.length; i++) {
			fieldsAsString.append(fields[i]);
			if (i != (fields.length - 1)) {
				fieldsAsString.append(",");
			}
		}
		params.putParam("fields", fieldsAsString.toString());
		if (userId == null || userId == 0) {
			params.putParam("uid", "null");
		} else {
			params.putParam("uid", userId.toString());
		}
		if (lId == null || lId == 0) {
			params.putParam("order","hints");
		} else {
			params.putParam("lid", lId.toString());
		}
		JSONObject json = sendRequest(params);
		JSONArray jsonArray = json.optJSONArray("response");
		if (jsonArray == null) {
			return null;
		}
		ArrayList<VKUser> friends = new ArrayList<VKUser>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonFriend = (JSONObject)jsonArray.get(i);
			VKUser friend = VKUser.parseFromJSON(jsonFriend);
			friends.add(friend);
		}
		return friends;
	}
	
	/**
	 * Gets user by uid
	 * @throws Exception 
	 * @throws VKException 
	 * @throws IOException 
	 * @see <a href="http://vk.com/pages?oid=-1&p=users.get">VK Users api</a>
	 */
	public ArrayList<VKUser> getUsers(ArrayList<Long> usersIds, String[] fields, String nameCase) throws Exception {
		VKRequestParams params = new VKRequestParams("users.get");
		if (fields == null) {
			fields = new String[] {"first_name", "last_name", "photo_medium_rec", "photo_rec", "online", "sex"};
		}
		StringBuilder fieldsAsString = new StringBuilder();
		for (int i = 0; i < fields.length; i++) {
			fieldsAsString.append(fields[i]);
			if (i != (fields.length - 1)) {
				fieldsAsString.append(",");
			}
		}
		params.putParam("fields", fieldsAsString.toString());
		if (usersIds == null) {
			throw new IllegalArgumentException("Users ids cannot be empty");
		}
		if (usersIds.size() == 0) {
			throw new IllegalArgumentException("Users ids cannot be empty");
		}
		StringBuilder usersIdsString = new StringBuilder();
		for (int i = 0; i < usersIds.size(); i++) {
			usersIdsString.append(usersIds.get(i).toString());
			if (i != (usersIds.size() - 1)) {
				usersIdsString.append(",");
			}
		}
		params.putParam("uids", usersIdsString.toString());
		if (nameCase != null) {
			params.putParam("name_case", nameCase);
		}
		JSONObject json = sendRequest(params);
		JSONArray jsonArray = json.optJSONArray("response");
		if (jsonArray == null) {
			return null;
		}
		ArrayList<VKUser> users = new ArrayList<VKUser>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonUser = (JSONObject)jsonArray.get(i);
			VKUser user = VKUser.parseFromJSON(jsonUser);
			users.add(user);
		}
		return users;
	}
	
	/**
	 * Gets all messages with needed params
	 * @see <a href="http://vk.com/pages?oid=-1&p=messages.get">VK Messages api</a>
	 * @param isOut true if you want sended messages
	 * @param count of messages to get, 100 is maximum
	 * @param timeOffset max seconds after message date to current time
	 *        0 if you want to get all messages by time
	 * @return ArrayList of needed messages 
	 * @throws Exception 
	 * @throws VKException 
	 * @throws IOException 
	 */
	public ArrayList<VKMessage> getMessages(boolean isOut, Integer count, Long timeOffset) throws Exception {
		if (count > 100 || count < 0) {
			throw new IllegalArgumentException("count param should be positive and <= 100");
		}
		if (timeOffset < 0) {
			throw new IllegalArgumentException("timeOffset param should be positive");
		}
		VKRequestParams requestParams = new VKRequestParams("messages.get");
		if (isOut) {
			requestParams.putParam("is_out", "1");
		}
		if (count != null) {
			requestParams.putParam("count", count.toString());
		}
		if (timeOffset != null) {
			requestParams.putParam("time_offset", "" + timeOffset);
		}
		JSONObject json = sendRequest(requestParams);
		JSONArray  jsonMessagesArray = json.getJSONArray("response");
		ArrayList<VKMessage> messages = new ArrayList<VKMessage>();
		// First object is count of messages, skiping it
		for (int i = 1; i < jsonMessagesArray.length(); i++) {
			messages.add(VKMessage.parseFromJSON((JSONObject)jsonMessagesArray.get(i)));
		}
		return messages;
	}
	
	/**
	 * Gets messages history
	 * @see <a href="http://vk.com/pages?oid=-1&p=messages.getHistory">VK Messages getHistory</a>
	 * @param userIdOrChatId which messages histoy you want to get
	 * @param offset default is null, or offset to choose needed messages
	 * @param count default is null, or count of messages to get, 200 max
	 * @param startMessageId after that message we will get history
	 * @param reverse default is null, true to get in chronologius order, false to get in reversed order (DEFAULT)
	 * @return ArrayList of history messages
	 * @throws Exception 
	 * @throws VKException 
	 * @throws IOException 
	 * @throws IllegalArgumentException
	 */
	public ArrayList<VKMessage> getMessagesHistory(long userIdOrChatId, Long offset, Long count, Long startMessageId, Boolean reverse) throws Exception {
		VKUtils.checkToBePositiveValue("userId", userIdOrChatId);
		VKUtils.checkToBePositiveValue("offset", offset);
		VKUtils.checkToBePositiveValue("count", count);
		VKUtils.checkToBePositiveValue("startMessageId", startMessageId);
		VKRequestParams requestParams = new VKRequestParams("messages.getHistory");
		requestParams.putParam("uid", "" + userIdOrChatId);
		if (offset != null) {
			requestParams.putParam("offset", offset.toString());
		}
		if (count != null) {
			if (count > 200) {
				throw new IllegalArgumentException("count param max value is 200");
			}
			requestParams.putParam("count", count.toString());
		}
		if (startMessageId != null) {
			requestParams.putParam("start_mid", startMessageId.toString());
		}
		if (reverse != null) {
			if (reverse) {
				requestParams.putParam("rev", "" + 1);
			} else {
				requestParams.putParam("rev", "" + 0);
			}
		}
		JSONObject json = sendRequest(requestParams);
		ArrayList<VKMessage> messages = new ArrayList<VKMessage>();
		JSONArray jsonMessagesArray = json.getJSONArray("response");
		for (int i = 1; i < jsonMessagesArray.length(); i++) {
			messages.add(VKMessage.parseFromJSON((JSONObject)jsonMessagesArray.get(i)));
		}
		return messages;
	}
	
	/**
	 * Gets last messages in dialogs
	 * @see <a href="http://vk.com/pages?oid=-1&p=messages.getDialogs">VK Messages getDialogs</a>
	 * @param userId null by default or userId whose last message you want to get
	 * @param chatId null by default or chatId which last message you want to get
	 * @param offset null by default or offset to get messages
	 * @param count null by default or count of last messages you want to get
	 * @param previewLength null by default or previewLength of words in messages to get
	 * @return ArrayList with last messages in needed or all dialogs
	 * @throws Exception 
	 * @throws VKException 
	 * @throws IOException 
	 */
	public ArrayList<VKMessage> getDialogs(Long userId, Long chatId, Long offset, Long count, Long previewLength) throws Exception {
		VKUtils.checkToBePositiveValue("userId", userId);
		VKUtils.checkToBePositiveValue("chatId", chatId);
		VKUtils.checkToBePositiveValue("offset", offset);
		VKUtils.checkToBePositiveValue("count", count);
		VKUtils.checkToBePositiveValue("previewLength", previewLength);
		VKRequestParams requestParams = new VKRequestParams("messages.getDialogs");
		if (userId != null) {
			requestParams.putParam("user_id", userId.toString());
		}
		if (chatId != null) {
			requestParams.putParam("chat_id", chatId.toString());
		}
		if (offset != null) {
			requestParams.putParam("offset", offset.toString());
		}
		if (previewLength != null) {
			requestParams.putParam("preview_length", previewLength.toString());
		}
		JSONObject json = sendRequest(requestParams);
		JSONArray  jsonDialogsArray = json.getJSONArray("response");
		ArrayList<VKMessage> dialogs = new ArrayList<VKMessage>();
		// First object is count of dialogs, skiping it
		for (int i = 1; i < jsonDialogsArray.length(); i++) {
			dialogs.add(VKMessage.parseFromJSON((JSONObject)jsonDialogsArray.get(i)));
		}
		return dialogs;
	}
	
	/**
	 * Gets news feed
	 * @param filters look to VKNewsFeed
	 * @param startTime DO NOT FORGET millis/1000, put null to use default
	 * @param endTime DO NOT FORGET millis/1000, put null to use default
	 * @param count MAX VALUE == 100, put null to use default
	 * @return VKNewsFeed object
	 * @throws IOException
	 * @throws VKException
	 * @throws Exception
	 */
	public VKNewsFeed getNewsFeed(String[] filters, Long startTime, Long endTime, Integer count) throws Exception {
		VKRequestParams params = new VKRequestParams("newsfeed.get");
		if (filters != null) {
			params.putParam("filters", VKUtils.arrayToParams(filters));
		}
		if (startTime != null) {
			params.putParam("start_time", startTime.toString());
		}
		if (endTime != null) {
			params.putParam("end_time", endTime.toString());
		}
		if (count != null) {
			params.putParam("count", count.toString());
		}
		JSONObject json = sendRequest(params);
		return VKNewsFeed.parseFromJSON(json);
	}
	
	/**
	 * Gets group by id
	 * @param gIds of groups to get, use long[] or String[], cannot be null
	 * @param fields put null to get all fields, or check out: http://vk.com/pages?oid=-1&p=%D0%9F%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80_fields_%D0%B4%D0%BB%D1%8F_%D0%B3%D1%80%D1%83%D0%BF%D0%BF
	 * @throws Exception 
	 * @throws VKException 
	 * @throws IOException 
	 */
	public <T> ArrayList<VKGroup> getGroupsById(T[] gIds, String[] fields) throws Exception {
		VKRequestParams params = new VKRequestParams("groups.getById");
		if (gIds == null) return null;
		params.putParam("gids", VKUtils.arrayToParams(gIds));
		if (fields != null) {
			params.putParam("fields", VKUtils.arrayToParams(fields));
		}
		JSONObject json = sendRequest(params);
		JSONArray jGroups = json.optJSONArray("response");
		return VKGroup.parseFromJSON(jGroups);
	}
}
