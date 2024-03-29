package com.macsemantics.ussd;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;

public class USSDElement
	{
	@XmlElement()
	public String uuid;
	
	public void logXML()
		{
		try
			{
			JAXBContext context = JAXBContext.newInstance();
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.marshal(this, System.out);
			}
		catch(Exception exception)
			{
			exception.printStackTrace();
			}
		}
	
	public void internalize()
		{
		}
	
	public boolean mustDisplay()
		{
		return(true);
		}
	}
