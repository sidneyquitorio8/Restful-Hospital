package edu.cs157b.restful;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.cs157b.util.*;

public class RestfulDAO {
	
	public String getPatientsDoctors(int patient_id) throws Exception {
		String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select patient_info.name as patient_name, doctor_info.name as doctor_name from patient_info left join appointment_request on patient_info.id = appointment_request.patient_id left join doctor_info on appointment_request.doctor_id = doctor_info.id where patient_info.id = ?;");
			query.setInt(1, patient_id);
			ResultSet rs = query.executeQuery();
			while(rs.next()) 
			{
				String patient = rs.getString("patient_name");
				if(result.equalsIgnoreCase("")) {
					result = "<h1>" + patient + "'s Doctors:" + "</h1>";
				}
				String doctor = rs.getString("doctor_name");
				result += "<p>" +doctor +"</p>"; 
			}
			
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		
		return result;
	}
	
	public String getDoctorsBySpecialty(int specialty_id) throws Exception {
		String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select doctor_info.name as doctor_name, specialty_info.name as specialty_name from specialty_info left join doctor_info on specialty_info.id = doctor_info.specialty_id where specialty_info.id = ?;");
			query.setInt(1, specialty_id);
			ResultSet rs = query.executeQuery();
			while(rs.next()) 
			{
				String specialty = rs.getString("specialty_name");
				if(result.equalsIgnoreCase("")) {
					result = "<h1>" + specialty + " Doctors:" + "</h1>";
				}
				String doctor = rs.getString("doctor_name");
				result += "<p>" +doctor +"</p>"; 
			}
			
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		
		return result;
	}
	
	public String getAllPatientInfo() throws Exception {
		String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from patient_info;");
			ResultSet rs = query.executeQuery();
			while(rs.next()) 
			{
				String patient = rs.getString("name");
				String record = rs.getString("medical_record");
				result += "<p>" +patient + "'s medical record: " + record +"</p>"; 
			}
			
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		
		return result;
	}
	
	public String getAllDoctorInfo() throws Exception {
		String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select doctor_info.name as doctor_name, specialty_info.name as specialty_name from doctor_info left join specialty_info on doctor_info.specialty_id = specialty_info.id;");
			ResultSet rs = query.executeQuery();
			while(rs.next()) 
			{
				String doctor = rs.getString("doctor_name");
				String specialty = rs.getString("specialty_name");
				if(specialty == null) {
					specialty = "nothing";
				}
				result += "<p>" + doctor + " specializes in: " + specialty +"</p>"; 
			}
			
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		
		return result;
	}

}
