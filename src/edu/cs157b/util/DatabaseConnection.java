package edu.cs157b.util;

import javax.naming.*;
import javax.sql.*;

public class DatabaseConnection {
	private static DataSource dataSource = null;
	private static Context context = null;
	
	public static DataSource getDataSource() throws Exception
	  {
		if (dataSource != null)
			return dataSource;
		if (context == null)
			context = new InitialContext();
		dataSource = (DataSource) context.lookup("mysqldb");
		
		return dataSource; 
	  }

}