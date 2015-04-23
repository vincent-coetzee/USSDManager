package com.macsemantics.ussd;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class USSDTokenRequest
	{
	@XmlElement
	public String personName;
	@XmlElement
	public String requestToken;
	@XmlElement
	public long timestamp;
	}
