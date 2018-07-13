
  
 /**
  * Project Name:ems-srv-onboard 
  * File Name:PayloadDataDto.java <br/><br/>  
  * Description: Payload data dto from LPS.
  * Copyright: Copyright (c) 2017 
  * Company:SAP
  * 
  * @author SAP
  * @date Dec 29, 2017 9:27:56 AM
  * @version 
  * @see
  * @since 
  */
  package com.sap.ems.onboarding.dto;

import java.util.Arrays;
import java.util.Map;

/**
  * ClassName: PayloadDataDto <br/><br/> 
  * Description: Payload Data DTO from LPS.
  * @author SAP
  * @version 
  * @see
  * @since 
  */
public class PayloadDataDto
{
    private String subscriptionAppName;
    private String subscriptionAppId;
    private String subscribedTenantId;
    private String subscribedSubdomain;
    private String subscriptionAppPlan;
    private long subscriptionAppAmount;
    private String[] dependantServiceInstanceAppIds = null;

    private Map<String, String> additionalInformation;

    public PayloadDataDto() {}

    public PayloadDataDto(String subscriptionAppName, String subscriptionAppId,
            String subscribedTenantId, String subscribedSubdomain, String subscriptionAppPlan,
            Map<String, String> additionalInformation) {
        this.subscriptionAppName = subscriptionAppName;
        this.subscriptionAppId = subscriptionAppId;
        this.subscribedTenantId = subscribedTenantId;
        this.subscribedSubdomain = subscribedSubdomain;
        this.subscriptionAppPlan = subscriptionAppPlan;
        this.additionalInformation = additionalInformation;

    }

    public String getSubscriptionAppName() {
        return subscriptionAppName;
    }

    public void setSubscriptionAppName(String subscriptionAppName) {
        this.subscriptionAppName = subscriptionAppName;
    }

    public String getSubscriptionAppId() {
        return subscriptionAppId;
    }

    public void setSubscriptionAppId(String subscriptionAppId) {
        this.subscriptionAppId = subscriptionAppId;
    }

    public String getSubscribedTenantId() {
        return subscribedTenantId;
    }

    public void setSubscribedTenantId(String subscribedTenantId) {
        this.subscribedTenantId = subscribedTenantId;
    }

    public String getSubscribedSubdomain() {
        return subscribedSubdomain;
    }

    public void setSubscribedSubdomain(String subscribedSubdomain) {
        this.subscribedSubdomain = subscribedSubdomain;
    }

    public String getSubscriptionAppPlan() {
        return subscriptionAppPlan;
    }

    public void setSubscriptionAppPlan(String subscriptionAppPlan) {
        this.subscriptionAppPlan = subscriptionAppPlan;
    }

    public Map<String, String> getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(Map<String, String> additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public long getSubscriptionAppAmount() {
        return subscriptionAppAmount;
    }

    public void setSubscriptionAppAmount(long subscriptionAppAmount) {
        this.subscriptionAppAmount = subscriptionAppAmount;
    }

    public String[] getDependantServiceInstanceAppIds() {
        return dependantServiceInstanceAppIds;
    }

    public void setDependantServiceInstanceAppIds(
            String[] dependantServiceInstanceAppIds) {
        this.dependantServiceInstanceAppIds = dependantServiceInstanceAppIds;
    }

    @Override
    public String toString()
    {
        return String.format(
            "\"PayloadDataDto\" [\"subscriptionAppName\":\"%s\", \"subscriptionAppId\":\"%s\", \"subscribedTenantId\":\"%s\", \"subscribedSubdomain\":\"%s\", \"subscriptionAppPlan\":\"%s\", \"subscriptionAppAmount\":\"%s\", \"dependantServiceInstanceAppIds\":\"%s\", \"additionalInformation\":\"%s\"]",
            subscriptionAppName, subscriptionAppId, subscribedTenantId, subscribedSubdomain, subscriptionAppPlan, subscriptionAppAmount,
            Arrays.toString(dependantServiceInstanceAppIds), additionalInformation);
    }
    
}
 