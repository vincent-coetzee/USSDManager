package com.macsemantics.ussd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@XmlRootElement
public class USSDWorkspace extends USSDElement
	{
	private static Map<String,USSDWorkspace> WorkspacesByName = new HashMap<String,USSDWorkspace>();
	private static Map<String,USSDWorkspace> WorkspacesByUUID = new HashMap<String,USSDWorkspace>();
	
	@XmlElement
	public String name = "";
	@XmlElement
	public List<USSDMenu> menus;
	@XmlElement
	public String startMenuUUID;
	
	private Map<String,USSDMenu> menusByUUID;
	
	public static USSDWorkspace workspaceForName(String name)
		{
		return(WorkspacesByName.get(name));
		}
	
	public static void setWorkspaceForName(USSDWorkspace workspace,String key)
		{
		WorkspacesByName.put(key,workspace);
		WorkspacesByUUID.put(workspace.uuid,workspace);
		}
	
	public static USSDWorkspace workspaceForUUID(String name)
		{
		return(WorkspacesByUUID.get(name));
		}
	
	public static void setWorkspaceForUUID(USSDWorkspace workspace,String key)
		{
		WorkspacesByUUID.put(key,workspace);
		WorkspacesByName.put(workspace.name,workspace);
		}
	
	public void internalize()
		{
		menusByUUID = new HashMap<String,USSDMenu>();
		for (USSDMenu menu : menus)
			{
			menu.internalize(uuid);
			menusByUUID.put(menu.uuid,menu);
			}
		}
	
	public USSDMenu startMenu()
		{
		return(menuForUUID(startMenuUUID));
		}
	
	public USSDMenu menuForUUID(String aUUID)
		{
		return(menusByUUID.get(aUUID));
		}
	}
