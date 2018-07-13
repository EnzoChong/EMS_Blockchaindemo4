
/**
 * Project Name:com.sap.ems.onboarding.callback 
 * File Name:InstanceDto.java <br/><br/>  
 * Description: TODO
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jun 23, 2017 2:08:51 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.onboarding.multitenancy.dto;

import java.io.Serializable;
import java.util.Map;

import com.sap.xsa.core.instancemanager.client.ManagedServiceInstance;
import com.sap.xsa.core.instancemanager.model.OperationStatus;

/**
 * ClassName: InstanceDto <br/>
 * <br/>
 * Description: InstanceDto
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class InstanceDto implements Serializable
{

    /**
     * serialVersionUID：
     * 
     * @see
     * @since
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private long updatedOn;

    private OperationStatus status;

    private Map<String, Object> credentials;

    private String failedMessage;

    private String deployFailedMessage;

    /**
     * create an instance: InstanceDto. Title: InstanceDto <br/>
     * <br/>
     */
    public InstanceDto()
    {
        super();
    }

    public InstanceDto(ManagedServiceInstance managedServiceInstance)
    {
        super();
        this.id = managedServiceInstance.getId();
        this.credentials = managedServiceInstance.getCredentials();
        this.status = managedServiceInstance.getStatus();
        this.updatedOn = managedServiceInstance.getUpdatedOnTimestamp();
        this.failedMessage = managedServiceInstance.getFailedMessage();
    }

    /**
     * create an instance: InstanceDto. Title: InstanceDto <br/>
     * <br/>
     * 
     * @param id
     * @param updatedOn
     * @param status
     * @param credentials
     * @param failedMessage
     */
    public InstanceDto(String id, long updatedOn, OperationStatus status, Map<String, Object> credentials, String failedMessage)
    {
        super();
        this.id = id;
        this.updatedOn = updatedOn;
        this.status = status;
        this.credentials = credentials;
        this.failedMessage = failedMessage;
    }

    public String getId()
    {

        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public long getUpdatedOn()
    {

        return updatedOn;
    }

    public void setUpdatedOn(long updatedOn)
    {
        this.updatedOn = updatedOn;
    }

    public OperationStatus getStatus()
    {

        return status;
    }

    public void setStatus(OperationStatus status)
    {
        this.status = status;
    }

    public Map<String, Object> getCredentials()
    {

        return credentials;
    }

    public void setCredentials(Map<String, Object> credentials)
    {
        this.credentials = credentials;
    }

    public String getFailedMessage()
    {

        return failedMessage;
    }

    public void setFailedMessage(String failedMessage)
    {
        this.failedMessage = failedMessage;
    }

    public String getDeployFailedMessage()
    {
    
        return deployFailedMessage;
    }

    public void setDeployFailedMessage(String deployFailedMessage)
    {
        this.deployFailedMessage = deployFailedMessage;
    }

}
