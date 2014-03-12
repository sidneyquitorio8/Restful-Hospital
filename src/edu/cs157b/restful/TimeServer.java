package edu.cs157b.restful;

import java.util.Date; 
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
 
@Path("/showtime") 
public class TimeServer 
{ 
 @GET 
 @Produces(MediaType.TEXT_HTML) 
 public String getTimeAsStr() 
 { return String.format("The current server time is" + new Date().toString() 
+"\n"+"Elapse time is"+new Date().getTime()); } 
} 
//Note: There cannot be multiple @GET methods in the same path. 

