package org.aws.test.helloworld.httpclient;

import org.apache.http.HttpResponse;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

/**
 * Created by hhira1 on 11/17/16.
 */
public abstract class HttpUtility {


    public static final String SPLUNK_RESPONSE_TIME_FIELDNAME = "response_time";
    public static final String SPLUNK_RESPONSE_PAYLOAD_SIZE_FIELDNAME = "response_payload_size";

    //  public abstract  ApiResponse  get(String url, Map<String, String> headers, Map<String, String> queryParams, String proxyUrl, Integer timeOut, boolean printLog) ;

    public ApiResponse createApiResponse(Response response) {
        return new ApiResponse(response);
    }


    public ApiResponse createApiResponse(HttpResponse httpResponse){
        return new ApiResponse(httpResponse);
    }

    public abstract ApiResponse get(String url, Map<String, String> headers, Map<String, String> queryParams, String proxyUrl, Integer timeOut, boolean printLog);

    public abstract ApiResponse post(String url, Map<String, String> headers, String jsonPayload, String proxyUrl, Integer timeOut);

    public abstract ApiResponse postRAW(String url, Map<String, String> headers, String stringPayloadForDebug, String proxyUrl, Integer timeOut, Entity<?> entity);

    public abstract ApiResponse putRAW(String url, Map<String, String> headers, String stringPayloadForDebug, String proxyUrl, Integer timeOut, Entity<?> entity);
    
    public abstract ApiResponse put(String url, Map<String, String> headers, String jsonPayload, String proxyUrl, Integer timeOut);

    public abstract ApiResponse delete(String url, Map<String, String> headers, String proxyUrl, Integer timeOut);

    public abstract ApiResponse postRAW(String url, Map<String, String> headers, String stringPayloadForDebug, String proxyUrl, Integer timeOut, Entity<?> entity, Map<String, String> queryParameter) ;
    
    public abstract void close() ;
}

