package edu.cs157b.restful;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hospital") 
public class HospitalRESTWS 
{ @Path("/htmlinfo") 
 @GET 
 @Produces(MediaType.TEXT_HTML) 
 public String getHTMLHospitalInfo() 
 { return "<html> <h4>SJSU Hospital</h4> </html>";} 
 
 @Path("/textinfo") 
 @GET 
 @Produces(MediaType.TEXT_PLAIN) 
 public String getTEXTHospitalInfo() 
 { return "SJSU Hospital";} 
} 
