
/**
 * Project Name:summit-srv-demo 
 * File Name:TokenInterceptor.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 27, 2018 12:52:17 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.demo.config.interceptor;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.alibaba.fastjson.JSONObject;
import com.sap.summit.multitenancy.interceptor.JWTTokenUtil;

/**
 * ClassName: TokenInterceptor <br/>
 * <br/>
 * Description: TODO
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
@Component
public class TokenInterceptor implements ClientHttpRequestInterceptor
{

    String serviceKey = "{\"type\":\"hyperledger-fabric\",\"channelId\":\"devf3758a10-4e9d-4d9d-9f34-5c7ddccb742fems-a-channel\",\"serviceUrl\":\"https://hyperledger-fabric.cfapps.sap.hana.ondemand.com/api/v1\",\"documentationUrl\":\"https://api.sap.com/shell/discover/contentpackage/SCPBlockchainTechnologies/api/hyperledger_fabric\",\"oAuth\":{\"clientId\":\"sb-4f963475-12a1-4125-883c-2f2f91c41e2d!b50|na-54bc25f3-f937-40b7-8b33-ffe240899cf0!b342\",\"clientSecret\":\"05r8PEaVWCJozWOjRk1AxopU0wc=\",\"url\":\"https://sap-csc-cd.authentication.sap.hana.ondemand.com\",\"identityZone\":\"sap-csc-cd\"}}";

    //    @Value("${blockchain.api.service.key}")
    //    private String serviceKey;

    private final ConcurrentMap<String, String> tokenCache = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.http.client.ClientHttpRequestInterceptor#intercept(org.springframework.http.HttpRequest,
     *      byte[], org.springframework.http.client.ClientHttpRequestExecution)
     */
    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException
    {
        String token;
        if (tokenCache.containsKey("token") && StringUtils.isNotEmpty(tokenCache.get("token"))
            && !JWTTokenUtil.isTokenExpired(tokenCache.get("token")))
        {
            token = tokenCache.get("token");
        }
        else
        {
            token = getToken();
            tokenCache.put("token", token);
        }
        HttpHeaders headers = request.getHeaders();
        headers.add("Authorization", token);
        return execution.execute(request, body);
    }

    private String getToken()
    {
        JSONObject object = JSONObject.parseObject(serviceKey);
        JSONObject oAuth = object.getJSONObject("oAuth");
        String oAuthUrl = oAuth.get("url").toString() + "/oauth/token?grant_type=client_credentials";
        String clientId = oAuth.get("clientId").toString();
        String clientSecret = oAuth.get("clientSecret").toString();
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        String str = clientId + ":" + clientSecret;
        headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(str.getBytes()));
        String body = restTemplate
            .exchange(new RequestEntity<Object>(headers, HttpMethod.GET, UriComponentsBuilder.fromHttpUrl(oAuthUrl).build().toUri()), String.class)
            .getBody();
        JSONObject auth = JSONObject.parseObject(body);
        String token = "Bearer " + auth.get("access_token").toString();
        return token;
    }
}
