package com.artemzin.android.vk.api;

import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.zip.GZIPInputStream;

/**
 * Main VKApi class
 * Use it to work with vk.com api
 * @author Artem Zinnatullin
 * @see <a href="http://vk.com/developers.php">VK Api Documentation</a>
 */
public class VKApi {

    /**
     * For LogCat messages
     */
    private static  String TAG;

    /**
     * Main vk.com api url
     */
    private static final String BASIC_API_URL;

    /**
     * Default value for gzip compression is disabled
     */
    public static final boolean GZIP_COMPRESSION_DEFAULT;

    /**
     * Default query retry limit
     */
    public static final int QUERY_RETRY_LIMIT_DEFAULT;

    /**
     * Default connection timeout in millis
     */
    public static final int CONNECTION_TIMEOUT_DEFAULT;

    /**
     * Exception message if response from vk.com was incorrect
     */
    public static final String EXCEPTION_MESSAGE_INCORRECT_RESPONSE;

    static {
        TAG = "VKApi";
        BASIC_API_URL = "https://api.vk.com/method/";
        // By default, gzip compression is disabled
        // Because on slow devices compression time is too big
        GZIP_COMPRESSION_DEFAULT = false;
        // By default, query reply limit is 3
        QUERY_RETRY_LIMIT_DEFAULT = 3;
        // By default, connection timeout will be 30 seconds
        CONNECTION_TIMEOUT_DEFAULT = 30000;
        EXCEPTION_MESSAGE_INCORRECT_RESPONSE = "Incorrect response from vk.com";
    }

    /**
     * Status of debug mode
     */
    private boolean debugMode;

    /**
     * VKUser authorization token
     */
    private final String accessToken;

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
     */
    public VKApi(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Enable/Disable debug mode (Will cause LogCat debug messages)
     * @param debugMode true to enable, false to disable
     */
    public void setDebugMode(final boolean debugMode) {
        this.debugMode = debugMode;
    }

    /**
     * By default, debug mode is false
     * @return current debug mode status
     */
    public boolean getDebugMode() {
        return this.debugMode;
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
     * Output LogCat message if debugMode enabled
     * @param logType of message, use android.util.Log static vars
     * @param message to output
     */
    private void log(final int logType, String message) {
        if (!debugMode) return;
        switch (logType) {
            case Log.DEBUG:
                Log.d(TAG, message);
                break;
            case Log.ERROR:
                Log.e(TAG, message);
                break;
            case Log.INFO:
                Log.i(TAG, message);
                break;
            case Log.VERBOSE:
                Log.v(TAG, message);
                break;
            case Log.WARN:
                Log.w(TAG, message);
                break;
            default:
                return;
        }
    }

    /**
     * Setting new connection timeout for network operations
     * @param connectionTimeout in millis
     * @throws java.security.InvalidParameterException if connection timeout negative or zero
     */
    public void setConnectionTimeout(int connectionTimeout) throws InvalidParameterException {
        if (connectionTimeout < 0) {
            throw new InvalidParameterException("Connection timeout could not be negative");
        } else if (connectionTimeout == 0) {
            throw new InvalidParameterException("Connection timeout could not be zero");
        } else {
            this.connectionTimeout = connectionTimeout;
        }
    }

    /**
     * Creating requestUrl with GET params from VKRequestParams and accessToken
     * @param requestParams with request params
     * @return url with needed GET params
     */
    String createRequestUrl(VKRequestParams requestParams) {
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
     * @throws java.io.IOException
     * @throws VKException if vk.com response contains error code
     */
    JSONObject sendRequest(VKRequestParams requestParams) throws Exception {
        final String requestUrl = createRequestUrl(requestParams);
        String response = null;
        log(Log.DEBUG, "Request url: " + requestUrl);
        for (int i = 1; i <= queryRetryLimit; i++) {
            if (i != 1) {
                log(Log.WARN, "Request was failed, trying again (" + i + ")");
            }
            try {
                response = sendRequestInternal(requestUrl);
                log(Log.DEBUG, "Server response: " + response);
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
     * @throws java.io.IOException if problems with input stream from server response
     * @throws Exception if problem with server response code, may be keep-alive problem
     */
    String sendRequestInternal(final String requestUrl) throws Exception {
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
            log(Log.DEBUG, "Server responce code: " + responseCode);
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
     * Use it to work with users api
     */
    public final VKUsersApi users = new VKUsersApi(this);

    /**
     * Use it to work with friends api
     */
    public final VKFriendsApi friends = new VKFriendsApi(this);

}
