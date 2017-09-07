package org.aws.test.helloworld;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.aws.test.helloworld.httpclient.ApiResponse;
import org.aws.test.helloworld.httpclient.HttpUtility;
import org.aws.test.helloworld.httpclient.JerseyHttpUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Component
@Path(value = "/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HttpTestResource {
    private static final Logger logger = LoggerFactory.getLogger(HttpTestResource.class);

    @GET
    @Path("/health/full")
    @Produces({MediaType.TEXT_PLAIN})
    public Response healthCheck() {
        return Response.ok().entity("Health Check Ok").build();
    }

    @POST
    @Path("/jersey")
    @Produces({MediaType.APPLICATION_JSON})
    public Response jerseyTest(
            @QueryParam("method") String method,
            @QueryParam("outboundUrl") String outboundUrl,
            @HeaderParam("tid") String tid,
            @Context HttpHeaders httpHeaders,
            String body) {

        logger.info("Jersey Test for outboundUrl={}", outboundUrl);
        HttpUtility httpUtility = JerseyHttpUtility.getInstance();
        return makeHttpCall(method, outboundUrl, tid, httpHeaders, body, httpUtility);
    }

    private Response makeHttpCall(String method, String outboundUrl, String tid, HttpHeaders httpHeaders, String body, HttpUtility httpUtility) {
        if (outboundUrl == null) {
            throw new IllegalArgumentException("url is null or invalid");
        }
        if (StringUtils.isBlank(tid)) {
            tid = UUID.randomUUID().toString();
        }
        final String uuid = tid;
        Map<String, String> aResponse = new HashMap<>();
        aResponse.put("tid", uuid);

        logger.debug("GET account response: {}", toJson(aResponse));
        ApiResponse apiResponse = httpCalls(method, outboundUrl, uuid, httpHeaders, body, httpUtility);
        return Response.status(apiResponse.getStatusCode()).entity(apiResponse.getResponseText()).build();
    }

    private static Map generateRequestHeader(HttpHeaders httpHeaders) {
        Map headerMap = new HashMap<String, String>();
        for (Map.Entry entry : httpHeaders.getRequestHeaders().entrySet()) {
            String key = entry.getKey().toString();
            headerMap.put(key, ((List) entry.getValue()).get(0));
            logger.info("headerKey={} value={}", entry.getKey(), entry.getValue());
        }
        return headerMap;
    }

    private static ApiResponse httpCalls(String method, String outboundUrl, String uuid, HttpHeaders httpHeaders, String body, HttpUtility httpUtility) {
        String s = "Apache_" + uuid + "_ConnectionTest";
        Map<String, String> queryParams = null;
        String proxyUrl = null;

        long lastMillis = Calendar.getInstance().getTimeInMillis();
        ApiResponse response = null;
        logger.info("Start {} Url {}", s, proxyUrl);
        try {
            Map headers = generateRequestHeader(httpHeaders);
            if (method == null || method.length() == 0 || method.equalsIgnoreCase("GET")) {
                response = httpUtility.get(outboundUrl, headers, queryParams, proxyUrl, 2000, true);
            } else if (method.equalsIgnoreCase("POST")) {
                response = httpUtility.post(outboundUrl, headers, body, proxyUrl, 2000);
            } else if (method.equalsIgnoreCase("PUT")) {
                response = httpUtility.put(outboundUrl, headers, body, proxyUrl, 2000);
            }
        } catch (Exception e) {
            logger.info("End {} {} HttpException {}", s, Calendar.getInstance().getTimeInMillis() - lastMillis, e.getMessage());
        }
        logger.info("End {} {} HttpSuccess {}", s, Calendar.getInstance().getTimeInMillis() - lastMillis, response.getStatusCode());
        return response;
    }


    private static String toJson(Object _message) {
        try {
            return new ObjectMapper().writeValueAsString(_message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "{}";
    }
}
