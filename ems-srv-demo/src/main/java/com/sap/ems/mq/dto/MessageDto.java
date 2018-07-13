
/**
 * Project Name:ems-srv-integration 
 * File Name:OutboundMessageDto.java <br/><br/>  
 * Description: Outbound response mesage dto.
 * Copyright: Copyright (c) 2017 
 * Company:SAP
 * 
 * @author SAP
 * @date Jan 5, 2018 1:46:42 PM
 * @version 
 * @see
 * @since 
 */
package com.sap.ems.mq.dto;

/**
 * ClassName: OutboundMessageDto <br/>
 * <br/>
 * Description: This is used for load out bound response message dto while
 * provisioning and so on.
 * 
 * @author SAP
 * @version
 * @see
 * @since
 */
public class MessageDto {
	private String guid;
	private String id;
	private String name;
	private String address;

	public String getGuid() {
		return guid;
	}


	public void setGuid(String guid) {
		this.guid = guid;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String toString() {
		return String.format(
				"\"OutboundMessageDto\" [\"tenantId\":\"%s\", \"jwtToken\":\"%s\", \"transactionGuid\":\"%s\"]",
				id, name,address);
	}

}
