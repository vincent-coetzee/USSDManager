package com.macsemantics.ussd;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class USSDTransferElement extends USSDElement
	{
	@XmlElement()
	public String type;
	@XmlElement()
	public int itemIndex;
	@XmlElement()
	public String text;
	@XmlElement()
	public String actionType;
	@XmlElement()
	public String actionValue;
	@XmlElement()
	public String nextMenuUUID;
	
	public void log()
		{
		
		}
	
	public USSDMenuItem asMenuItem()
		{
		if (type.equals("USSDSpace.USSDMenuItem"))
			{
			return(asUSSDMenuItem());
			}
		return(null);
		}
	
	protected USSDMenuItem asUSSDMenuItem()
		{
		USSDMenuItem item;
		
		item = new USSDMenuItem();
		item.menuIndex = itemIndex;
		item.menuText = text;
		item.uuid = uuid;
		if (actionType != null && actionType.equals("link"))
			{
			item.actionType = "link";
			item.nextMenuUUID = nextMenuUUID;
			}
		return(item);
		}
	}	
