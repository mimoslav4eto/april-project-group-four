

package db_layer;
import java.sql.*;

/**
 * GetMax.java
 * @author kbh
 * @version 26. oktober 2006
 */
public class GetMax 
{
    public GetMax() 
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
	}
	catch(Exception e)
	{
	    System.out.println("Query exception: Error while reading max_id" + e);
	}
	return id;
}
}
