
/**
 * Project Name:com.sap.ems.common.infrastructure 
 * File Name:JWTTokenModel1.java
 * Package Name:com.sap.csc.ems.model
 * Description:
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Apr 12, 2017 10:12:42 AM
 * @version V1.0
 */

package com.sap.ems.multitenancy.interceptor;

/**
 * ClassName: JWTTokenModel1 
 * Description: JWTTokenModel1
 * 
 * @author SAP
 * @date Apr 12, 2017 10:12:42 AM
 */

public class JWTTokenModel
{
    private String userName;
    private String email;
    private long exp;
    private String zid;

    public String getZid()
    {
    
        return zid;
    }

    public void setZid(String zid)
    {
        this.zid = zid;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public long getExp()
    {
        return exp;
    }

    public void setExp(long exp)
    {
        this.exp = exp;
    }
}
