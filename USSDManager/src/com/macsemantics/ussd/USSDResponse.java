package com.macsemantics.ussd;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class USSDResponse 
	{
	@XmlElement()
	public boolean successful;
	@XmlElement()
	public int errorCode;
	@XmlElement()
	public Object result;
	@XmlElement
	public String errorMessage;
	
	public USSDResponse()
		{
		successful = true;
		errorCode = 0;
		errorMessage = "OK";
		}
	
	public USSDResponse(Object anObject)
		{
		this();
		result = anObject;
		}
	
	public USSDResponse(int code,String message)
		{
		this();
		result = "";
		successful = false;
		errorCode = code;
		errorMessage = message;
		}
	}
