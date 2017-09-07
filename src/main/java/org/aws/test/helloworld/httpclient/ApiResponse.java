package org.aws.test.helloworld.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;


public class ApiResponse implements Closeable {
    private Response.StatusType statusInfo;
    private MultivaluedMap<String, String> stringHeaders;
    private int statusCode = -1;
    private String responseText;
    private Response response;

    private HttpResponse httpResponse;

    public ApiResponse(Response response) {
        this.response = response;
    }

    public ApiResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    protected void fetchAndClose() {
        if (response != null) {
            this.statusCode = response.getStatus();
            statusInfo = response.getStatusInfo();
            stringHeaders = response.getStringHeaders();
            responseText = response.readEntity(String.class);
            close();
        } else if (httpResponse != null) {
            setParamsForHttpResponse();
        } else {
            return;
        }

    }

    public MultivaluedMap getHeaders(){
        fetchAndClose();
        return stringHeaders;
    }

    private void setParamsForHttpResponse() {
        try {
            if (httpResponse.getStatusLine() != null) {
                this.statusCode = httpResponse.getStatusLine().getStatusCode();
                Response.ResponseBuilder responseBuilder = Response.status(this.statusCode);
                responseBuilder.status(httpResponse.getStatusLine().getStatusCode());
                Response response = responseBuilder.build();
                statusInfo = response.getStatusInfo();
            }
            Arrays.stream(httpResponse.getAllHeaders()).forEach(header -> {
                stringHeaders.add(header.getName(), header.getValue());
            });
            responseText = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception ignore) {
            //todo:Sonali remove after testing
        }
        close();
    }

    public int getStatusCode() {
        fetchAndClose();
        return statusCode;
    }

    public Response.StatusType getStatusInfo() {
        fetchAndClose();
        return statusInfo;
    }

    public String getResponseText() {
        fetchAndClose();
        return responseText;
    }

    public String getHeaderString(String key) {
        fetchAndClose();
        return stringHeaders.getFirst(key);
    }

    /**
     * If User Calls this method, it is users responsibility to close once complete.
     * If User want to do something special none of the other get methods should be called as
     * we close the response as during first get.
     * Try to modify ApiResponse in favour of not using this method by exposing very minimal methods from Jersey Response Object
     */
//    @Deprecated
//    public Response getResponse() {
//        return response;
//    }
    @Override
    public void close() {
        if (response != null) {
            response.close();
            response = null;
        } else if (httpResponse != null && httpResponse.getEntity() != null) {
            try {
                EntityUtils.consume(httpResponse.getEntity());
            } catch (IOException ignore) {
                //todo:Sonali remove after testing
                ignore.printStackTrace();
            }
            httpResponse = null;
        } else {
            return;
        }

    }
}
