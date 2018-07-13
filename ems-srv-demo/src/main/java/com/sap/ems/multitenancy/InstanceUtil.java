package com.sap.ems.multitenancy;

import java.util.List;

import com.sap.ems.multitenancy.exception.InstanceManagerInitException;
import com.sap.xsa.core.instancemanager.client.ImClientException;
import com.sap.xsa.core.instancemanager.client.InstanceCreationOptions;
import com.sap.xsa.core.instancemanager.client.InstanceManagerClient;
import com.sap.xsa.core.instancemanager.client.InstanceManagerClientFactory;
import com.sap.xsa.core.instancemanager.client.ManagedServiceInstance;
import com.sap.xsa.core.instancemanager.client.ServiceInstance;

/**
 * ClassName: InstanceUtil <br/>
 * <br/>
 * Description: get instance from env
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class InstanceUtil
{
    /**
     * Title: getInfoFromVCAP <br/>
     * <br/>
     * Description: get VCAP_SERVICES from env and generate ServiceInstance
     * 
     * @return
     * @throws ImClientException
     * @throws ClassNotFoundException
     * @see
     * @since
     */
    public static List<ServiceInstance> getInfoFromVCAP() throws ImClientException, ClassNotFoundException
    {
        List<ServiceInstance> result = null;
        String vcapServices = System.getenv().get("VCAP_SERVICES");
        if (vcapServices != null && !vcapServices.isEmpty())
        {
            result = InstanceManagerClientFactory.getServicesFromVCAP(vcapServices);
        }
        return result;
    }

    /**
     * Title: getImClient <br/>
     * <br/>
     * Description: generate InstanceManagerClient from serviceInstance
     * 
     * @return
     * @throws ClassNotFoundException
     * @throws ImClientException
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    public static InstanceManagerClient getImClient() throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        List<ServiceInstance> serviceInstances = getInfoFromVCAP();
        if (serviceInstances != null && !serviceInstances.isEmpty())
        {
            ServiceInstance serviceInstance = serviceInstances.get(0);
            return InstanceManagerClientFactory.getInstance(serviceInstance);
        }
        throw new InstanceManagerInitException("serviceInstance can not generate a InstanceManagerClient");
    }

    /**
     * Title: getManagedServiceInstance <br/>
     * <br/>
     * Description:get ManagedServiceInstancef by instance name
     * 
     * @param instenceName
     * @param forceCacheUpdate
     *            if update the cache which is store the instance.
     * @return
     * @throws ClassNotFoundException
     * @throws ImClientException
     * @throws InstanceManagerInitException
     * @see
     * @since
     */
    public static ManagedServiceInstance getManagedServiceInstance(String instenceName, boolean forceCacheUpdate)
        throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        InstanceManagerClient imClient = getImClient();
        if (imClient != null)
        {
            return imClient.getManagedInstance(instenceName, forceCacheUpdate);
        }
        else
        {
            throw new InstanceManagerInitException("InstanceManagerClient is null");
        }
    }

    /**
     * Title: getManagedServiceInstance <br/>
     * <br/>
     * Description:get instence with token
     * 
     * @param instenceName
     * @return instence
     * @throws ClassNotFoundException
     * @throws ImClientException
     * @throws InstanceManagerInitException
     * @see #getManagedServiceInstance(String instenceName, boolean forceCacheUpdate)
     * @since
     */
    public static ManagedServiceInstance getManagedServiceInstance(String instenceName)
        throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        return getManagedServiceInstance(instenceName, false);
    }

    
     /**
      * Title: createManagedInstance <br/><br/>  
      * Description: create a instance
      * @param token
      * @param creationOptions
      * @param i
      * @return the intance created 
     * @throws InstanceManagerInitException 
     * @throws ImClientException 
     * @throws ClassNotFoundException 
      * @see
      * @since
      */
    public static ManagedServiceInstance createManagedInstance(String token, InstanceCreationOptions creationOptions, int timeout) throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        InstanceManagerClient imClient = getImClient();
        ManagedServiceInstance msi = imClient.createManagedInstance(token,creationOptions,timeout);
        return msi;
    }

    
     /**
      * Title: deleteManagedInstance <br/><br/>  
      * Description: delete a instance with token ,the data will delete at the same time 
      * @param token
     * @throws InstanceManagerInitException 
     * @throws ImClientException 
     * @throws ClassNotFoundException 
      * @see
      * @since
      */
    public static void deleteManagedInstance(String token) throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        InstanceManagerClient imClient = getImClient();
        imClient.deleteManagedInstance(token);
    }

    
     /**
      * Title: getManagedInstances <br/><br/>  
      * Description: get all instance list
      * @return
     * @throws InstanceManagerInitException 
     * @throws ImClientException 
     * @throws ClassNotFoundException 
      * @see
      * @since
      */
    public static List<ManagedServiceInstance> getManagedInstances() throws ClassNotFoundException, ImClientException, InstanceManagerInitException
    {
        InstanceManagerClient imClient = getImClient();
        return imClient.getManagedInstances(); 
    }
}
