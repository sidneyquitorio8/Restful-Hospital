package edu.cs157b.restful;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;




import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;


import org.codehaus.jettison.json.JSONObject;

import edu.cs157b.util.DatabaseConnection;
import edu.cs157b.util.ToJSON;

@Path("/json")
public class FindDataReturnJSON {
	
	@Path("/stringOutput")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDababaseContentsInJSON() throws Exception
	{
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info");
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			
			query.close();
			
			returnString = json.toString();
		}
		finally {
			if (conn!=null) conn.close();
		}
		return  returnString;
	}
	
	
	@Path("/responseOutput")
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response getDababaseContentsResponse() throws Exception
		{
			PreparedStatement query = null;
			String returnString = null;
			Connection conn = null;
			Response rb = null; 
			
			try{
				conn = DatabaseConnection.getDataSource().getConnection();
				query = conn.prepareStatement("select * from doctor_info");
				ResultSet rs = query.executeQuery();
				
				ToJSON converter = new ToJSON();
				JSONArray json = new JSONArray();
				json = converter.toJSONArray(rs);
				
				query.close();
				
				returnString = json.toString();
				rb = Response.ok(returnString).build();
			}
			finally {
				if (conn!=null) conn.close();
			}
			return  rb;
		}

	@Path("/takingParameter")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDababaseContentsusingParameter(@QueryParam("name") String title) throws Exception
	{
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response rb = null; 
		
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info where name = ?");
			query.setString(1, title);
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			query.close();
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
		}
		finally {
			if (conn!=null) conn.close();
		}
		return  rb;
	}
	
	
	@Path("/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDababaseContentsusingParameter2(@PathParam("name") String title) throws Exception
	{
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response rb = null; 
		
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info where name = ?");
			query.setString(1, title);
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			query.close();
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
		}
		finally {
			if (conn!=null) conn.close();
		}
		return  rb;
	}
	@Path("/{name}/{sID}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDababaseContentsusingParameter3(
			@PathParam("name") String title, @PathParam("sID") int uID) throws Exception
	{
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response rb = null; 
		
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from loan where name = ? and sID = ?");
			query.setString(1, title);
			query.setInt(2, uID);
			ResultSet rs = query.executeQuery();
			
			ToJSON converter = new ToJSON();
			JSONArray json = new JSONArray();
			json = converter.toJSONArray(rs);
			query.close();
			
			returnString = json.toString();
			rb = Response.ok(returnString).build();
		}
		finally {
			if (conn!=null) conn.close();
		}
		return  rb;
	}
	
	@Path("/postExample1")
	@POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooks(String incomingData) throws Exception {
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response rb = null; 
            
           
            try {
                    System.out.println("incomingData: " + incomingData);
                    
                    /*
                     * ObjectMapper is from Jackson Processor framework
                     * http://jackson.codehaus.org/
                     *
                     * Using the readValue method, you can parse the JSON from the http request
                     * and data bind it to a Java Class.
                     */
                    ObjectMapper mapper = new ObjectMapper();
                    ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
                    
                    conn = DatabaseConnection.getDataSource().getConnection();
                    query = conn.prepareStatement("insert into BOOK VALUES (?,?,?)");
                	
                	query.setString(1, itemEntry.BOOK_TITLE);
                	query.setString(2, itemEntry.BOOK_AUTHOR);
                	query.setInt(3, itemEntry.BOOK_COPIES);

                    query.executeUpdate(); //note the new command for insert statement
            }
            catch (Exception e) {
            		e.printStackTrace();
                    return Response.status(500).entity("Server was not able to process your request").build();
            		
	        }
            finally { if (conn!=null) conn.close(); }
            
            returnString = "Item inserted";
            return Response.ok(returnString).build();
            		      
    }
	@Path("/postExample2")
	@POST
   // @Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooks2(String incomingData) throws Exception {
		PreparedStatement query = null;
		String returnString = null;
		Connection conn = null;
		Response rb = null; 
		
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		
            
           
            try {
                    System.out.println("incomingData: " + incomingData);
                    JSONObject partsData = new JSONObject(incomingData);
                    System.out.println( "jsonData: " + partsData.toString() );
                    
                    /*
                     * In order to access the data, you will need to use one of the method in JSONArray
                     * or JSONObject. I recommend using the optXXXX methods instead of the get method.
                     *
                     * Example:
                     * partsData.get("PC_PARTS_TITLE");
                     * The example above will get you the data, the problem is, if PC_PARTS_TITLE does
                     * not exist, it will generate a java error. If you are using get, you need to use
                     * the has method first partsData.has("PC_PARTS_TITLE");.
                     *
                     * Example:
                     * partsData.optString("PC_PARTS_TITLE");
                     * The optString example above will also return data but if PC_PARTS_TITLE does not
                     * exist, it will return a BLANK string.
                     *
                     * partsData.optString("PC_PARTS_TITLE", "NULL");
                     * You can add a second parameter, it will return NULL if PC_PARTS_TITLE does not
                     * exist.
                     */
                    
                    
                    conn = DatabaseConnection.getDataSource().getConnection();
                    query = conn.prepareStatement("insert into BOOK VALUES (?,?,?)");
                	
                	query.setString(1, partsData.optString("BOOK_TITLE"));
                	query.setString(2, partsData.optString("BOOK_AUTHOR"));
                	query.setInt(3,partsData.optInt("BOOK_COPIES") );

                    query.executeUpdate(); //note the new command for insert statement
                    
                    /*
                     * The put method allows you to add data to a JSONObject.
                     * The first parameter is the KEY (no spaces)
                     * The second parameter is the Value
                     */
                    jsonObject.put("HTTP_CODE", "200");
                    jsonObject.put("MSG", "Item has been entered successfully, Version 3");
                    /*
                     * When you are dealing with JSONArrays, the put method is used to add
                     * JSONObjects into JSONArray.
                     */
                    returnString = jsonArray.put(jsonObject).toString();
            }
            catch (Exception e) {
            		e.printStackTrace();
                    return Response.status(500).entity("Server was not able to process your request").build();
            		
	        }
            finally { if (conn!=null) conn.close(); }
            
            
            return Response.ok(returnString).build();
            		      
    }
} // the end of class


class ItemEntry
{ public String BOOK_TITLE;
  public String BOOK_AUTHOR;
  public int BOOK_COPIES;
}


