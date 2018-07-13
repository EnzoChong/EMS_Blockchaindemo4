
/**
 * Project Name:com.sap.summit.onboarding.callback 
 * File Name:DeployDBJSONUtil.java <br/><br/>  
 * Description: generate a Json for Deploy DB
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 23, 2017 2:08:51 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.summit.onboarding.multitenancy.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * ClassName: DeployDBJSONUtil <br/>
 * <br/>
 * Description: generate a Json for Deploy DB
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class DeployDBJSONUtil
{
    
     /**
      * Title: generateDeployDBBody <br/><br/>  
      * Description: generate a Json for Deploy DB
      * @param credentials
      * @return generate a Json for Deploy DB
      * @see
      * @since
      */
    public static JSONObject generateDeployDBBody(Map<String, Object> credentials)
    {
        JSONObject json = new JSONObject();
        Map<String, Object> hana = new HashMap<>();
        List<Map<String, Object>> inHanaList = new ArrayList<>();
        Map<String, Object> inHanaListMap = new HashMap<>();
        List<String> inTagsList = new ArrayList<>();
        inTagsList.add("hana");
        inTagsList.add("database");
        inTagsList.add("relational");
        inHanaListMap.put("name", "hdi_container_service_name");
        inHanaListMap.put("label", "hana");
        inHanaListMap.put("tags", inTagsList);
        inHanaListMap.put("plan", "hdi-shared");
        inHanaListMap.put("credentials", credentials);
        inHanaList.add(inHanaListMap);
        hana.put("hana", inHanaList);
        json.put("ADDITIONAL_VCAP_SERVICES", hana);
        json.put("TARGET_CONTAINER", "hdi_container_service_name");
        return json;
    }
}
