
/**
 * Project Name:com.sap.summit.onboarding.callback 
 * File Name:DeployDBClient.java <br/><br/>  
 * Description: 
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 23, 2017 2:08:51 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.onboarding.multitenancy.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.sap.summit.conf.DepolyDBClientFeignConfig;

/**
 * ClassName: DeployDBClient <br/>
 * <br/>
 * Description: DeployDBClient
 * 
 * @author SAP
 * @version
 * @see
 * @since https://srv-ems-dynamic-db-demo.cfapps.sap.hana.ondemand.com
 *        https://ems-dynamic-db-perf.cfapps.sap.hana.ondemand.com
 */
@FeignClient(name = "dbClient", url = "${hdi_dynamic_deploy_url}", configuration = DepolyDBClientFeignConfig.class)
public interface DeployDBClient
{

    /**
     * Title: deployDB <br/>
     * <br/>
     * Description: deploy DB
     * 
     * @param json
     * @return deploy DB message
     * @see
     * @since
     */
    @RequestMapping(method = RequestMethod.POST, value = "/v1/deploy", consumes = "application/json")
    public ResponseEntity<String> deployDB(@RequestBody JSONObject json);
}
