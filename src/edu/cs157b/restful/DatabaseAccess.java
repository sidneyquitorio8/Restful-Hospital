package edu.cs157b.restful;


import java.sql.PreparedStatement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import edu.cs157b.util.*;



import java.sql.*;

@Path("/database")
public class DatabaseAccess {
	private static final String api_version= "00.01.00";
	
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnTitle()
	{ return "<p> Java Web Service</p>" ; }
	
	@Path("/version")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnVersion()
	{ return "<p> Version</p>" + api_version ; }
	
	@Path("/dbaccess")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String returnDatabaseStatus() throws Exception
	{
		PreparedStatement query = null;
		String myString = null;
		String returnString = " ";
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info");
			ResultSet rs = query.executeQuery();
			while(rs.next()) 
			{String title = rs.getString("name");
			 
			returnString += title +"\n"; 
			}
			
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		return "<p> Test </p> " + returnString;
	}
	
}