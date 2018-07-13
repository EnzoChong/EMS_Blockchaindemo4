package com.sap.summit;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.http.HttpHost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.sap.summit.demo.config.interceptor.TokenInterceptor;
import com.sap.summit.multitenancy.DynamicDBInit;

@SpringBootApplication
@SpringBootConfiguration
@Import({DynamicDBInit.class})
@EnableFeignClients
@EnableEurekaClient
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
    private Environment env;
	
	@Autowired
	private TokenInterceptor tokenInterceptor;
	
	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException
	{
        CloseableHttpClient httpClient = httpClient();

        HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory();

        requestFactory.setHttpClient(httpClient);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    restTemplate.getInterceptors().add(tokenInterceptor);
	    return restTemplate;
	}

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
    
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }
    
    public CloseableHttpClient httpClient()
    {
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setDefaultMaxPerRoute(1);
        connectionManager.setMaxTotal(20);
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(connectionManager);
        // Set proxy
        String proxy = System.getenv("https_proxy");
        if (StringUtils.isNotBlank(proxy))
        {
            proxy = proxy.replace("http://", "");
            int portSplitter = proxy.lastIndexOf(':');
            if (NumberUtils.isNumber(proxy.substring(proxy.lastIndexOf(':') + 1)))
            {
                String proxyHost = proxy.substring(0, portSplitter);
                Integer proxyPort = Integer.valueOf(proxy.substring(portSplitter + 1));
                httpClientBuilder.setProxy(new HttpHost(proxyHost, proxyPort, "https"));
            }
        }

        if (null != env.getProperty("spring.config.name") || StringUtils.join(env.getActiveProfiles()).contains("local"))
        {
            httpClientBuilder.setProxy(new HttpHost("proxy.wdf.sap.corp", 8080, "http"));
        }

        // Build
        return httpClientBuilder.build();
    }
}
