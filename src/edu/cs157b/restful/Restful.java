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
	
	@Path("specialtys/{sID}/doctors")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getDoctorsBySpecialty(
			@PathParam("sID") int sID) throws Exception
	{
		String result = "";
		result += dao.getDoctorsBySpecialty(sID);
		return result;
	}
	
	@PUT
	@Path("/doctors/{dID}")
	@Produces(MediaType.TEXT_HTML)
	public String editDoctor(@PathParam("dID") int dID, @FormParam("name") String name, @FormParam("specialty") int sID) throws Exception {
		String result = "";
		result += dao.editDoctor(dID, name,sID);
		return result;
	}
	
	@PUT
	@Path("/patients/{pID}")
	@Produces(MediaType.TEXT_HTML)
	public String editPatient(@PathParam("pID") int pID, @FormParam("name") String name, @FormParam("record") String record) throws Exception {
		String result = "";
		result += dao.editPatient(pID, name,record);
		return result;
	}
	
	@POST
	@Path("/doctors")
	@Produces(MediaType.TEXT_HTML)
	public String addDoctor(
			@FormParam("name") String name,
			@FormParam("specialty") int sID) throws Exception {
		String result = "";
		result += dao.addDoctor(name,sID);
		return result;
	}
	
	@POST
	@Path("/patients")
	@Produces(MediaType.TEXT_HTML)
	public String addPatient(
			@FormParam("name") String name,
			@FormParam("record") String record) throws Exception {
		String result = "";
		result += dao.addPatient(name,record);
		return result;
	}
	
	@DELETE
	@Path("/doctors/{dID}")
	@Produces(MediaType.TEXT_HTML)
	public String deleteDoctor(@PathParam("dID") int id) throws Exception {
		String result = "";
		result += dao.deleteDoctor(id);
		return result;
	}
	
}