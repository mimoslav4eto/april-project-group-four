package db_layer;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection
{
	
	private static Connection con;
	private DatabaseMetaData dmd;
	private static DBConnection instance = null;
	
	private static final String driver = "jdbc:sqlserver://balder.ucn.dk:1433";
	private static final String db_name = ";databaseName=dmaj0913_4";
	private static final String u_name = ";user=dmaj0913_4";
	private static final String pass = ";password=IsAllowed";

	private DBConnection()
	{
		String url = driver + db_name + u_name + pass;
		try
		{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			System.out.println("Load of class was successfull");
		}
		catch(ClassNotFoundException cnfe)
		{
			System.out.println("Load of the class was unsuccessfull.");
			System.out.println(cnfe.getMessage());
		}
		
		try
		{
			con = DriverManager.getConnection(url);
			con.setAutoCommit(true);
			dmd = con.getMetaData();
			System.out.println("Successfully connected with: " + dmd.getURL());
			System.out.println("Driver used: " + dmd.getDriverName());
			System.out.println("Database: " + dmd.getDatabaseProductName());
		}
		catch(SQLException se)
		{
			System.out.println("Error while making a connection to database");
			System.out.println(se.getMessage());
			System.out.println("URL: " + url);
		}
	}
	
	public static void close_connection()
	{
		try
		{
			con.close();
			System.out.println("Connection closed");
		}
		catch(SQLException se)
		{
			System.out.println("Error while trying to close the connection.");
			System.out.println(se.getMessage());
		}
	}
	
	public static DBConnection get_instance()
	{
		if (instance == null)
		{
			instance = new DBConnection();
		}
		return instance;
	}
	
	public Connection get_connection()
	{
		return con;
	}

}
