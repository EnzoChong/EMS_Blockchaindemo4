package com.sap.summit.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeUtil {
	
	/**
	 * 对象序列化
	* @date: 2017年5月19日 
	* @author: chenhao
	* @title: serialize 
	* @param object
	* @return 
	* @exception: 
	* @version: 1.0 
	* @description: 
	* update_version: update_date: update_author: update_note:
	 */
	public static byte[] serialize(Object object){
		ObjectOutputStream objIn = null;
		ByteArrayOutputStream arrayObjOut = null;
		try{
			if(object != null){
				arrayObjOut = new ByteArrayOutputStream();
				objIn = new ObjectOutputStream(arrayObjOut);
				objIn.writeObject(object);
				byte[] result = arrayObjOut.toByteArray();
				return result;	
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *反序列化
	* @date: 2017年5月19日 
	* @author: chenhao
	* @title: deSerialize 
	* @param bytes
	* @return 
	* @exception: 
	* @version: 1.0 
	* @description: 
	* update_version: update_date: update_author: update_note:
	 */
	public static Object deSerialize(byte[] bytes){
		ByteArrayInputStream in = null;
		try {
			if(bytes!=null){
				in = new ByteArrayInputStream(bytes);
				ObjectInputStream objIn = new ObjectInputStream(in);
				Object obj = objIn.readObject();
				return obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
