

package db_layer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GetMax.java
 * @author kbh
 * @version 26. oktober 2006
 */
public class UtilityFunctions 
{
    public UtilityFunctions() 
    {
    }		
    public static int get_max_id(String query)
    {
		ResultSet results;
		int id = -1;
		try
		{ 
		   Statement stmt = DBConnection.get_instance().get_connection().createStatement();
		   results = stmt.executeQuery(query);
		   if( results.next() )
		   {
			 id = results.getInt(1);
		   }
		   else
		   {
			   id = 1;
		   }
		}
		catch(Exception e)
		{
		    System.out.println("Query exception: Error while reading max_id " + e);
		}
		return id;
	}
    
    public static PreparedStatement make_insert_statement(Connection con, String table, String var_names)
	{
		return make_multiple_insert_statement(con, table, var_names, 1);
	}
    
    public static PreparedStatement make_multiple_insert_statement(Connection con, String table, String var_names, int times)
    {
    	String quest_marks = "";
		int var_num = var_names.split(",").length;
		for(int i = 0; i < var_num; i++)
		{
			if(i==0)
			{
				quest_marks += "?";
			}
			else
			{
				quest_marks += ", ?";
			}
		}
		try
		{
			String query = String.format("INSERT INTO %s(%s) VALUES(%s)", table, var_names, quest_marks);
			for (int i = 0; i < times-1; i++)
			{
				query += String.format(", (%s)", quest_marks);
			}
			 return con.prepareStatement(query);
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating insert statement: " + se);
			return null;
		}
    }
    
    public static PreparedStatement prepare_statement(Connection con, String query, String where_clause, int int_where_clause)
	{
		PreparedStatement ps = null;
		try
		{
			ps = con.prepareStatement(query);
			if (where_clause.length() > 0)
			{
				ps.setString(1, where_clause);
			}
			else if (int_where_clause != -1 )
			{
				ps.setInt(1, int_where_clause);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error while creating a statement: " + e);
		}
		return ps;
	}
    
}
