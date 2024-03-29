package com.macsemantics.ussd;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@Path("/USSDService")
public class USSDManagerService 
	{
	private static final Log logger = LogFactory.getLog(USSDManagerService.class);
	private String packageName = "USSDManager";
	private String serviceName = "USSDService";
	
	private Map<String,USSDWorkspace> workspaces;
    /**
     * Default constructor. 
     */
    public USSDManagerService() 
    	{
        loadWorkspaces();
        loadAuthorizedUsers();
    	}
    
    protected void loadAuthorizedUsers()
    	{
    	
    	}
    protected void loadWorkspaces()
    	{
    	workspaces = new HashMap<String,USSDWorkspace>();
    	}
    
    @POST
    @Path("/requestToken/")
    @Consumes("application/json")
    @Produces("application/json")
    public USSDResponse requestToken(USSDTokenRequest request)
    	{
    	return(new USSDResponse(USSDCredential.timeToken()));
    	}
    
    @POST
    @Path("/deployWorkspace")
    @Consumes("application/json")
    @Produces("application/json")
    public USSDResponse deployWorkspace(USSDWorkspace workspace)
    	{
    	if (workspace.startMenuUUID.equals(""))
    		{
    		return(new USSDResponse(1001,"A workspace must have a start menu defined before it can be successfully deployed"));
    		}
    	workspace.internalize();
    	USSDWorkspace.setWorkspaceForUUID(workspace,workspace.uuid);
		return(new USSDResponse());
    	}
    
    @GET
    @Path("/runWorkspace/{workspaceName}")
    @Produces("application/text")
    public String runWorkspace(@PathParam("workspaceName") String workspaceName,@Context HttpServletRequest request)
    	{
    	HttpSession session;
    	USSDEngine engine;
    	String url;
    	
    	url = "http://"+request.getServerName() + ":"+request.getServerPort() + "/"+packageName +request.getServletPath() + "/"+serviceName+"/selectMenuItem";
    	logger.debug("GENERATED URL = "+url);
    	session = request.getSession(true);
    	engine = new USSDEngine(workspaceName,url);
    	session.setAttribute(USSDEngine.SessionKey,engine);
    	return(engine.renderedContent(session));
    	}
    
    @GET
    @Path("/selectMenuItem")
    @Produces("application/text")
    public String execute(@Context HttpServletRequest request)
    	{
    	USSDEngine engine;
    	String content;
    	
    	engine = (USSDEngine)request.getSession().getAttribute(USSDEngine.SessionKey);
    	content = engine.selectMenuItem(request);
    	return(content);
    	}
    
    @GET
    @Path("/workspaceNames")
    @Produces("application/json")
    public USSDResponse workspaceNames()
    	{
    	String names[] = {"Campaign1","AfricanBankRewards","Test"};

		return(new USSDResponse(names));
    	}
    
    @GET
    @Path("/workspace/{identifier}")
    @Produces("application/json")
    public USSDResponse workspaceAtIdentifier(@PathParam("identifier") String workspaceIdentifier)
    	{
    	USSDWorkspace workspace;
    	
    	logger.error("/workspace");
    	logger.error("identifier = "+ workspaceIdentifier);
    	workspace = new USSDWorkspace();
    	return(new USSDResponse(workspace));
    	}
	}
