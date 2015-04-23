package com.macsemantics.ussd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
public class USSDMenuItem extends USSDMenuEntry
	{
	@XmlElement()
	public int menuIndex;
	@XmlElement()
	public String menuText;
	
	public String actionType;
	public String nextMenuUUID;
	public USSDMenu menu;
	
	public void executeInContext(USSDEngine engine,HttpServletRequest request)
		{
		HttpSession session;
		USSDWorkspace workspace;
		
		session = request.getSession();
		if (actionType.equals("link") && nextMenuUUID != null)
			{
			workspace = engine.workspace();
			engine.setCurrentMenu(workspace.menuForUUID(nextMenuUUID));
			}
		}
	}
