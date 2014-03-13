package edu.cs157b.restful;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import edu.cs157b.util.*;

public class RestfulDAO {
	
	public String getPatientsDoctors(int patient_id) throws Exception {
		String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select patient_info.name as patient_name, doctor_info.name as doctor_name from patient_info left join appointment_request on patient_info.id = appointment_request.patient_id left join doctor_info on appointment_request.doctor_id = doctor_info.id where doctor_info.id = ?;");
			query.setInt(1, patient_id);
			ResultSet rs = query.executeQuery();
			while(rs.next()) 
			{
				String doctor = rs.getString("doctor_name");
				if(result.equalsIgnoreCase("")) {
					result = "<h1>" + doctor + "'s Patients:" + "</h1>";
				}
				String patient = rs.getString("patient_name");
				result += "<p>" +patient +"</p>"; 
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

    public String addDoctor(String name, int specialty_id) throws Exception {
    	String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		String specialty = "";
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from specialty_info where id = ?");
			query.setInt(1, specialty_id);
			ResultSet rs = query.executeQuery();
			if(rs.next()) {
				specialty = rs.getString("name");
			}
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		if(!specialty.equalsIgnoreCase("")) {
	        try {
	            conn = DatabaseConnection.getDataSource().getConnection();
	            query = conn.prepareStatement("insert into doctor_info (name, specialty_id) values (?, ?);");
	        	
	        	query.setString(1, name);
	        	query.setInt(2, specialty_id);
	
	            query.executeUpdate(); //note the new command for insert statement
	            result += "<h1> Doctor added </h1>";
	        }
	        finally { if (conn!=null) conn.close(); }
		}
		else {
			result += "<h1> No specialty with that id </h1>";
		}
        return result;
    }
    
    public String addPatient(String name, String medical_record) throws Exception {
    	String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		
        try {
            conn = DatabaseConnection.getDataSource().getConnection();
            query = conn.prepareStatement("insert into patient_info (name, medical_record) values (?, ?);");
        	
        	query.setString(1, name);
        	query.setString(2, medical_record);

            query.executeUpdate(); //note the new command for insert statement
            result += "<h1> Patient added </h1>";
        }
        finally { if (conn!=null) conn.close(); }
        
        return result;
    }
    
    public String deleteDoctor(int id) throws Exception {
    	String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		String doctor = "";
		
		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info where id = ?");
			query.setInt(1, id);
			ResultSet rs = query.executeQuery();
			if(rs.next()) {
				doctor = rs.getString("name");
			}
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		if(!doctor.equalsIgnoreCase("")) {
	        try {
	            conn = DatabaseConnection.getDataSource().getConnection();
	            query = conn.prepareStatement("delete from doctor_info where id = ?;");
	        	
	        	query.setInt(1, id);
	
	            query.executeUpdate(); //note the new command for insert statement
	            result += "<h1> Doctor deleted </h1>";
	        }
	        finally { if (conn!=null) conn.close(); }
		}
		else {
			result += "<h1> No doctor found with that id </h1>";
		}
        return result;
    }
    
    public String editDoctor(int doctor_id, String name, int specialty_id) throws Exception {
    	String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		String specialty = "";
		String doctor = "";

		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info where id = ?");
			query.setInt(1, doctor_id);
			ResultSet rs = query.executeQuery();
			if(rs.next()) {
				doctor = rs.getString("name");
			}
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		if(!doctor.equalsIgnoreCase("")) {
			if(specialty_id != 0) {
				try{
					conn = DatabaseConnection.getDataSource().getConnection();
					query = conn.prepareStatement("select * from specialty_info where id = ?");
					query.setInt(1, specialty_id);
					ResultSet rs = query.executeQuery();
					if(rs.next()) {
						specialty = rs.getString("name");
					}
					query.close();
				}
				finally {
					if (conn!=null) conn.close();
				}
			}
			else{
				specialty = "don't change";
			}
    	}
		else {
			result += "<h1> No doctor with that id </h1>";
		}
		if(!specialty.equalsIgnoreCase("")) {
	        try {
	            conn = DatabaseConnection.getDataSource().getConnection();
	            String sql = "";
	            
	            if(name != null && specialty_id != 0 ) {
	            	sql = "update doctor_info set name=?, specialty_id=? where id=?";
	            }
	            else if(name != null) {
	            	sql = "update doctor_info set name=? where id=?";
	            }
	            else if(specialty_id != 0) {
	            	sql = "update doctor_info set specialty_id=? where id=?";
	            }
	            else {
	            	return "<p>No information changed</p>";
	            }
	            
	            query = conn.prepareStatement(sql);
	            
	            if(name != null && specialty_id != 0 ) {
		        	query.setString(1, name);
		        	query.setInt(2, specialty_id);
		        	query.setInt(3, doctor_id);
	            }
	            else if(name != null) {
		        	query.setString(1, name);
		        	query.setInt(2, doctor_id);
	            }
	            else if(specialty_id != 0) {
		        	query.setInt(1, specialty_id);
		        	query.setInt(2, doctor_id);
	            }
	            else {
	            	return "No information changed";
	            }
	
	            query.executeUpdate(); //note the new command for insert statement
	            result += "<h1> Doctor edited </h1>";
	        }
	        finally { if (conn!=null) conn.close(); }
		}
		else {
			result += "<h1> No specialty with that id </h1>";
		}
        return result;
    }
    
    public String editPatient(int patient_id, String name, String record) throws Exception {
    	String result = "";
		PreparedStatement query = null;
		Connection conn = null;
		String specialty = "";
		String patient = "";

		try{
			conn = DatabaseConnection.getDataSource().getConnection();
			query = conn.prepareStatement("select * from doctor_info where id = ?");
			query.setInt(1, patient_id);
			ResultSet rs = query.executeQuery();
			if(rs.next()) {
				patient = rs.getString("name");
			}
			query.close();
		}
		finally {
			if (conn!=null) conn.close();
		}
		if(!patient.equalsIgnoreCase("")) {
	        try {
	            conn = DatabaseConnection.getDataSource().getConnection();
	            String sql = "";
	            if(name != null && record != null) {
	            	sql = "update patient_info set name=?, medical_record=? where id=?";
	            }
	            else if(record != null) {
	            	sql = "update patient_info set medical_record=? where id=?";
	            }
	            else if(name != null) {
	            	sql = "update patient_info set name=? where id=?";
	            }
	            else {
	            	return "<p> No information changed </p>";
	            }
	            
	            query = conn.prepareStatement(sql);
	        	
	            if(name != null & record != null) {
	            	query.setString(1, name);
		        	query.setString(2, record);
		        	query.setInt(3, patient_id);
	            }
	            else if(record != null) {
		        	query.setString(1, record);
		        	query.setInt(2, patient_id);
	            }
	            else if(name != null) {
	            	query.setString(1, name);
		        	query.setInt(2, patient_id);
	            }
	
	            query.executeUpdate(); //note the new command for insert statement
	            result += "<h1> Patient edited </h1>";
	        }
	        finally { if (conn!=null) conn.close(); }
    	}
		else {
			result += "<h1> No patient with that id </h1>";
		}
        return result;
    }
}
