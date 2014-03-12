package edu.cs157b.restful;


import java.sql.PreparedStatement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.cs157b.util.*;

import java.sql.*;

@Path("/restful")
public class Restful {
	private static final RestfulDAO dao= new RestfulDAO();
	
	@Path("/doctors")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getDoctorsInfo() throws Exception
	{
		String result = "";
		result += "<h1> Doctors Info </h1>";
		result += dao.getAllDoctorInfo();
		return result;
	}
	
	@Path("/patients")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getPatientsInfo() throws Exception
	{
		String result = "";
		result += "<h1> Patients Info </h1>";
		result += dao.getAllPatientInfo();
		return result;
	}
	
	@Path("patients/{pID}/doctors")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getPatientsDoctors(
			@PathParam("pID") int pID) throws Exception
	{
		String result = "";
		result += dao.getPatientsDoctors(pID);
		return result;
	}
	
}