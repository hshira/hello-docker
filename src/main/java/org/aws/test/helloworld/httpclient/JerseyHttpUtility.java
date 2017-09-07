package org.aws.test.helloworld.httpclient;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hhira1 on 11/17/16.
 */
public class JerseyHttpUtility extends HttpUtility {


    private static final Logger logger = LoggerFactory.getLogger(JerseyHttpUtility.class);
    private static JerseyHttpUtility itself = null;

    private static Map<String, Client> clientCache = new HashMap<>();

    public static HttpUtility getInstance() {
        if(itself == null){
            itself = new JerseyHttpUtility();
        }
        return itself;
    }

    @Override
    public ApiResponse get(String url, Map<String, String> headers, Map<String, String> queryParams, String proxyUrl, Integer timeOut, boolean printLog) {
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            client = createClient(proxyUrl, timeOut);
            Invocation.Builder requestWithURL = setupBuilder(url, headers, queryParams, client);
            Response response = requestWithURL.get(Response.class);
            ApiResponse apiResponse = createApiResponse(response);
            if (printLog) {
                logResponse("GET", url, proxyUrl, headers, null, timeOut, startTime, apiResponse, HttpStatus.OK.value());
            }
            return apiResponse;
        } catch (Exception ex) {
            if (printLog) {
                logException("GET", url, proxyUrl, headers, null, timeOut, startTime, ex);
            }
            throw new WebApplicationException(ex);
        }
    }

    @Override
        public ApiResponse postRAW(String url, Map<String, String> headers, String stringPayloadForDebug, String proxyUrl, Integer timeOut, Entity<?> entity,Map<String,String> queryParameter) {
            long startTime = System.currentTimeMillis();
            Client client = null;
            try {
                client = createClient(proxyUrl, timeOut);
                Invocation.Builder request = setupBuilder(url, headers, queryParameter, client);
                Response response = request.post(entity, Response.class);
                ApiResponse apiResponse = createApiResponse(response);
                logResponse("POST", url, proxyUrl, headers, stringPayloadForDebug, timeOut, startTime, apiResponse, HttpStatus.OK.value(), HttpStatus.CREATED.value(), HttpStatus.ACCEPTED.value(), HttpStatus.NO_CONTENT.value());
                return apiResponse;
            } catch (Exception ex) {
                logException("POST", url, proxyUrl, headers, stringPayloadForDebug, timeOut, startTime, ex);
                throw new WebApplicationException(ex);
            }
        }

    @Override
    public ApiResponse post(String url, Map<String, String> headers, String jsonPayload, String proxyUrl, Integer timeOut) {
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            client = createClient(proxyUrl, timeOut);
            Invocation.Builder request = setupBuilder(url, headers, null, client);
            Response response = request.post(createJSONEntity(jsonPayload), Response.class);
            ApiResponse apiResponse = createApiResponse(response);
            logResponse("POST", url, proxyUrl, headers, jsonPayload, timeOut, startTime, apiResponse, HttpStatus.OK.value(), HttpStatus.CREATED.value(), HttpStatus.ACCEPTED.value(), HttpStatus.NO_CONTENT.value());
            return apiResponse;
        } catch (Exception ex) {
            logException("POST", url, proxyUrl, headers, jsonPayload, timeOut, startTime, ex);
            throw new WebApplicationException(ex);
        }
    }

    @Override
    public ApiResponse postRAW(String url, Map<String, String> headers, String stringPayloadForDebug, String proxyUrl, Integer timeOut, Entity<?> entity) {
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            client = createClient(proxyUrl, timeOut);
            Invocation.Builder request = setupBuilder(url, headers, null, client);
            Response response = request.post(entity, Response.class);
            ApiResponse apiResponse = createApiResponse(response);
            logResponse("POST", url, proxyUrl, headers, stringPayloadForDebug, timeOut, startTime, apiResponse, HttpStatus.OK.value(), HttpStatus.CREATED.value(), HttpStatus.ACCEPTED.value(), HttpStatus.NO_CONTENT.value());
            return apiResponse;
        } catch (Exception ex) {
            logException("POST", url, proxyUrl, headers, stringPayloadForDebug, timeOut, startTime, ex);
            throw new WebApplicationException(ex);
        }
    }
    
    @Override
    public ApiResponse putRAW(String url, Map<String, String> headers, String stringPayloadForDebug, String proxyUrl, Integer timeOut, Entity<?> entity) {
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            client = createClient(proxyUrl, timeOut);
            Invocation.Builder request = setupBuilder(url, headers, null, client);
            Response response = request.put(entity, Response.class);
            ApiResponse apiResponse = createApiResponse(response);
            logResponse("PUT", url, proxyUrl, headers, stringPayloadForDebug, timeOut, startTime, apiResponse, HttpStatus.OK.value(), HttpStatus.CREATED.value(), HttpStatus.ACCEPTED.value(), HttpStatus.NO_CONTENT.value());
            return apiResponse;
        } catch (Exception ex) {
            logException("PUT", url, proxyUrl, headers, stringPayloadForDebug, timeOut, startTime, ex);
            throw new WebApplicationException(ex);
        }
    }

    @Override
    public ApiResponse put(String url, Map<String, String> headers, String jsonPayload, String proxyUrl, Integer timeOut) {
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            client = createClient(proxyUrl, timeOut);
            Invocation.Builder requestWithHeaders = setupBuilder(url, headers, null, client);
            Response response = requestWithHeaders.put(createJSONEntity(jsonPayload), Response.class);
            ApiResponse apiResponse = createApiResponse(response);
            logResponse("PUT", url, proxyUrl, headers, jsonPayload, timeOut, startTime, apiResponse, HttpStatus.OK.value());
            return apiResponse;
        } catch (Exception ex) {
            logException("PUT", url, proxyUrl, headers, jsonPayload, timeOut, startTime, ex);
            throw new WebApplicationException(ex);
        }
    }

    @Override
    public ApiResponse delete(String url, Map<String, String> headers, String proxyUrl, Integer timeOut) {
        long startTime = System.currentTimeMillis();
        Client client = null;
        try {
            client = createClient(proxyUrl, timeOut);
            Invocation.Builder request = setupBuilder(url, headers, null, client);
            Response response = request.delete(Response.class);
            ApiResponse apiResponse = createApiResponse(response);
            logResponse("DELETE", url, proxyUrl, headers, null, timeOut, startTime, apiResponse, HttpStatus.OK.value());
            return apiResponse;
        } catch (Exception ex) {
            logException("DELETE", url, proxyUrl, headers, null, timeOut, startTime, ex);
            throw new WebApplicationException(ex);
        }
    }


    private Invocation.Builder createRequest(WebTarget target) {
        return target.request().accept(MediaType.APPLICATION_JSON);
    }

    private Client createClient(String proxyUrl) {
        ClientConfig clientConfig = new ClientConfig();
//        ApacheConnectorProvider apacheConnectorProvider = new ApacheConnectorProvider();
//        clientConfig.connectorProvider(apacheConnectorProvider);
        Client client = ClientBuilder.newClient(clientConfig);
        if (StringUtils.isNotEmpty(proxyUrl)) {
            client.property(ClientProperties.PROXY_URI, proxyUrl);
        }

        return client;
    }

    private MultivaluedHashMap<String, Object> createMultivaluedHashMap(Map<String, String> headers) {
        MultivaluedHashMap<String, Object> headersHashMap = new MultivaluedHashMap<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            headersHashMap.add(entry.getKey(), entry.getValue());
        }
        return headersHashMap;
    }


    private Client createClient(String proxyUrl, Integer timeOut) {
        String key = proxyUrl + "_" + timeOut;

        Client client = clientCache.get(key);
        if (client == null) {
            client = createClient(proxyUrl);
            if (timeOut != null) {
                client.property(ClientProperties.CONNECT_TIMEOUT, timeOut);
                client.property(ClientProperties.READ_TIMEOUT, timeOut);
            }
            clientCache.put(key, client);
        }
        return client;
    }

    private Invocation.Builder setupBuilder(String url, Map<String, String> headers, Map<String, String> queryParams, Client client) {
        WebTarget target = client.target(url);

        if (queryParams != null) {
            for (Map.Entry<String, String> entry : queryParams.entrySet())
                target = target.queryParam(entry.getKey(), entry.getValue());
        }

        Invocation.Builder request = createRequest(target);

        if (headers != null && headers.size() > 0) {
            return request.headers(createMultivaluedHashMap(headers));
        }
        return request;
    }

    private Entity<String> createJSONEntity(String jsonPayload) {
        return Entity.entity(jsonPayload, MediaType.APPLICATION_JSON);
    }

    private void logException(String method, String url, String proxyUrl, Map<String, String> headers, String jsonPayload, Integer timeOut, long startTime, Exception ex) {
        logger.error(String.format("Method=%s; proxyUrl=%s; url=%s; headers=%s; jsonPayload=%s; timeOut=%d executed in " + SPLUNK_RESPONSE_TIME_FIELDNAME + "=%d", method, proxyUrl, url, headers, jsonPayload, timeOut, System.currentTimeMillis() - startTime), ex);
    }

    private void logResponse(String method, String url, String proxyUrl, Map<String, String> headers, String jsonPayload, Integer timeOut, long startTime, ApiResponse apiResponse, Integer... validResponses) {

        boolean hasResponsePayload = StringUtils.isNotBlank(apiResponse.getResponseText());
        int responsePayloadSize = hasResponsePayload?apiResponse.getResponseText().length():0;
        if (logger.isInfoEnabled()) {
            try {
                url = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException ignore) {
            }
            logger.info(String.format("Method=%s; proxyUrl=%s; url=%s; statusCode=%s; timeOut=%d; executed in " + SPLUNK_RESPONSE_TIME_FIELDNAME + "=%d " + SPLUNK_RESPONSE_PAYLOAD_SIZE_FIELDNAME + "=%d", method, proxyUrl, url, apiResponse.getStatusCode(), timeOut, System.currentTimeMillis() - startTime, responsePayloadSize));
        }
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Header=%s; RequestPayload=%s; ResponsePayload=%s", headers, jsonPayload, apiResponse.getResponseText()));
        } else if (!ArrayUtils.contains(validResponses, apiResponse.getStatusCode())) {
            // if response not ok and debug is not enable print to log as error
            logger.error(String.format("StatusInfo:%s, Response String: %s", apiResponse.getStatusInfo(), apiResponse.getResponseText()));
        }
    }

    @Override
    public void close() {
        try {
            for (Client client : clientCache.values()) {
                client.close();
            }
            clientCache.clear();
        } catch (Exception ex) {
            logger.error("Unable to close client ", ex);
        }
    }
}
