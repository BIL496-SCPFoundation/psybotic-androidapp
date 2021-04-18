package com.scpfoundation.psybotic.app.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

public class RequestManager {
    private static final String TAG = "RequestManager";
    private static RequestManager instance = null;

    public RequestQueue requestQueue;

    public RequestManager(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized RequestManager getInstance(Context context) {
        if (null == instance)
            instance = new RequestManager(context);
        return instance;
    }

    public static synchronized RequestManager getInstance() {
        if (null == instance) {
            throw new IllegalStateException(RequestManager.class.getSimpleName() +
                    " is not initialized, call getInstance(...) first");
        }
        return instance;
    }

    /**
     *
     * This method sends a POST request to the specified url
     *
     * @param url: the url to send the post request
     * @param body: post request body (must be a JSONObject)
     * @param responseListener: success response callback
     * @param errorListener: error callback
     */
    public void postRequest(String url, JSONObject body,
                            Response.Listener<JSONObject> responseListener,
                            Response.ErrorListener errorListener) {
        prepareRequestWithBody(url, Request.Method.POST, body, responseListener, errorListener);
    }

    /**
     *  This method sends a GET request to the specified url
     *
     * @param url: the url to send the get request
     * @param params: GET request params (Map<String, String>)
     * @param responseListener: success response callback
     * @param errorListener: error callback
     */
    public void getRequest(String url, Map<String, String> params,
                           Response.Listener<JSONObject> responseListener,
                           Response.ErrorListener errorListener) {

        url = prepareUrlWithParams(url, params);
        prepareRequest(url, Request.Method.GET, responseListener, errorListener);
    }

    /**
     *  This method send a GET request to the specified url and waits for a json array response
     * @param url: the url to send the get request
     * @param params: GET request params (Map<String
     * @param responseListener: success response cal
     * @param errorListener: error callback
     */
    public void getArrayRequest(String url, Map<String, String> params,
                                Response.Listener<JSONArray> responseListener,
                                Response.ErrorListener errorListener) {
        url = prepareUrlWithParams(url, params);
        prepareArrayRequest(url, Request.Method.GET, responseListener, errorListener);

    }

    /**
     *  This method sends a DELETE request to the specified url
     *
     * @param url: the url to send the post request
     * @param body: delete request body (must be a JSONObject)
     * @param responseListener: success response callback
     * @param errorListener: error callback
     */
    public void deleteRequest(String url, JSONObject body,
                           Response.Listener<JSONObject> responseListener,
                           Response.ErrorListener errorListener) {
        prepareRequestWithBody(url, Request.Method.DELETE, body, responseListener, errorListener);
    }

    /**
     *  This method sends DELETE request with the provided parameters
     * @param url: the url to send the get request
     * @param params: DELETE request params (Map<String, String>)
     * @param responseListener: success response callback
     * @param errorListener: error callback
     */
    public void deleteRequest(String url, Map<String, String> params,
                              Response.Listener<JSONObject> responseListener,
                              Response.ErrorListener errorListener) {
        url = prepareUrlWithParams(url, params);
        prepareRequest(url, Request.Method.DELETE, responseListener, errorListener);
    }

    private String prepareUrlWithParams(String url, Map<String, String> params) {
        if (params != null && !params.isEmpty()) {
            url += "?";
        }
        StringBuilder urlBuilder = new StringBuilder(url);
        if (params != null) {
            for (String key :
                    params.keySet()) {
                urlBuilder.append(key).append("=").append(params.get(key));
            }
        }
        return urlBuilder.toString();
    }

    private void prepareRequest(String url, int method,
                                Response.Listener<JSONObject> responseListener,
                                Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(
                method, url, null, responseListener, errorListener);
        requestQueue.add(request);
    }

    private void prepareArrayRequest(String url, int method, Response.Listener<JSONArray> responseListener,
                                     Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(
                method, url, null, responseListener, errorListener
        );
        requestQueue.add(request);
    }

    private void prepareRequestWithBody(String url, int method, JSONObject body,
                                        Response.Listener<JSONObject> responseListener,
                                        Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(
                method, url, body, responseListener, errorListener);
        requestQueue.add(request);

    }

}
