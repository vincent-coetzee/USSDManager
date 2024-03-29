package com.macsemantics.ussd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

@XmlRootElement()
public class USSDMenu extends USSDElement
	{
	private static Logger logger = Logger.getLogger(USSDMenu.class);
	@XmlElement()
	public String menuName;
	@XmlElement()
	public List<USSDMenuItem> menuItems;
	@XmlElement
	public List<USSDTransferElement> transferElements;
	@XmlElement
	public String text;
	
	private Map<Integer,USSDMenuItem> menuItemsByIndex;
	private String workspaceUUID;
	
	public void internalize(String aUUID)
		{
		USSDMenuItem menuItem;
	
		workspaceUUID = aUUID;
		menuItems = new ArrayList<USSDMenuItem>();
		menuItemsByIndex = new HashMap<Integer,USSDMenuItem>();
		for (USSDTransferElement element : transferElements)
			{
			menuItem = element.asMenuItem();
			if (menuItem != null)
				{
				menuItem.menu = this;
				menuItems.add(menuItem);
				menuItemsByIndex.put(element.itemIndex,menuItem);
				}
			}
		}
	
	public USSDWorkspace workspace()
		{
		return(USSDWorkspace.workspaceForUUID(workspaceUUID));
		}
	
	public String asXMLForSession(HttpSession session,String urlString)
		{
		StringBuilder builder;
		String url;
		
		url = urlString + ";jsessionid="+session.getId();
		builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"utf-8\"?><request>");
		builder.append("<headertext>"+text+"</headertext>");
		builder.append("<options>");
		for (USSDMenuItem item: menuItems)
			{
			builder.append("<option command=\""+item.menuIndex+"\" order=\""+item.menuIndex+"\" callback=\""+url+"?menuItemIndex="+item.menuIndex+"\"");
			builder.append(item.mustDisplay() ? " display=\"true\">"  + item.menuText: " display=\"false\">");
			builder.append("</option>");
			}
		builder.append("</options>");
		builder.append("</request>");
		return(builder.toString());
		}
	
	public USSDMenuItem itemAtIndex(int index)
		{
		return(menuItemsByIndex.get(index));
		}
	}
