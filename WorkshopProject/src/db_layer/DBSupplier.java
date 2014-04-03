package db_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model_layer.Supplier;

public class DBSupplier
{
	private Connection con;
	public DBSupplier()
	{
		con = DBConnection.get_instance().get_connection();
	}
	
	public Supplier find_supplier(int id, boolean make_association)
	{
		String var = "s.id=";
		return single_where(var, "", id, make_association);
	}
	
	
	public ArrayList<Supplier> get_all_suppliers(boolean make_association)
	{
		return multiple_where("", "", -1, make_association);
	}

	
	public int insert_supplier(Supplier sup)
	{
		
		int rc = -1;
		String name = sup.getName();
		String email = sup.getEmail();
		String zipcode = sup.getZipcode();
		String city = sup.getCity();
		String phone_nr = sup.getPhone_nr();
		String description = sup.getDescription();
		String address = sup.getAddress();
		String CVR = sup.getCVR();
		String country = sup.getCountry();
		System.out.println("User to be inserted: " + name);
		try
		{
			PreparedStatement stmt;
			if (!zip_code_exists(zipcode))
			{
				stmt = UtilityFunctions.make_insert_statement(con, "ZipCity", "zipcode, city, country");
				stmt.setString(1, zipcode);
				stmt.setString(2, city);
				stmt.setString(3, country);
				stmt.executeUpdate();
				stmt.close();
			}
			int id = UtilityFunctions.get_max_id("SELECT max(id) FROM Entity") + 1;
			stmt = UtilityFunctions.make_insert_statement(con, "Entity", "id, name, phone_nr, email, address, zipcode, type");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, phone_nr);
			stmt.setString(4, email);
			stmt.setString(5, address);
			stmt.setString(6, zipcode);
			stmt.setString(7, "supplier");
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
			
			stmt = UtilityFunctions.make_insert_statement(con, "Supplier", "id, CVR, description");
			stmt.setInt(1, id);
			stmt.setString(2, CVR);
			stmt.setString(3, description);
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
		}
		catch (SQLException se)
		{
			System.out.println("Error while inserting: " + se);
		}
		return rc;
	}
	
	
	private boolean zip_code_exists(String zipcode)
	{
		try
		{
			PreparedStatement st = con.prepareStatement("SELECT * FROM ZipCity WHERE zipcode=?");
			st.setString(1, zipcode);
			boolean exists = st.executeQuery().next();
			st.close();
			return exists;
		}
		catch(SQLException se)
		{
			System.out.println("Error occured while searching for a zipcode " + se);
			return false;
		}
	}
	
	
	private Supplier single_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		Supplier sup = null;
		String query = make_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				sup = create_supplier(results);
				
				if(make_association)
				{
					DBSuppliesWith db_s = new DBSuppliesWith();
					sup.setSupplies_with(db_s.find_supplied_by(sup.getId()));
				}
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Supplier query exception: " + e);
		}
		return sup;
	}
	
	
	private ArrayList<Supplier> multiple_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
	    ArrayList<Supplier> suppliers = new ArrayList<Supplier>();	
	    Supplier sup;
	    String query = make_query(var);
	    try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);//con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			while(results.next())
			{
				sup = create_supplier(results);
				if(make_association)
				{
					DBSuppliesWith db_s = new DBSuppliesWith();
					sup.setSupplies_with(db_s.find_supplied_by(sup.getId()));
				}
				suppliers.add(sup);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Query exception: " + e);
		}
	 return suppliers;   
	}
	
	
	private String make_query(String var)
	{
		String query = "SELECT e.id, e.name, e.address, e.zipcode, e.phone_nr, e.email, "
				+ "z.city, z.country, s.CVR, s.description "
				+ "FROM Entity AS e, Supplier AS s, ZipCity AS z "
				+ "WHERE type='supplier' AND e.id = s.id AND z.zipcode = e.zipcode";
		if (var.length() > 0)
		{
			query += " AND " + var + "?";
		}
		System.out.println("QUERY: " + query);
		return query;
	}
	
	
	protected Supplier create_supplier(ResultSet results)
	{
		Supplier sup = new Supplier();
		try
		{

			
			sup.setName(results.getString("name"));
			sup.setAddress(results.getString("address"));
			sup.setCity(results.getString("city"));
			sup.setEmail(results.getString("email"));
			sup.setId(results.getInt("id"));
			sup.setPhone_nr(results.getString("phone_nr"));
			sup.setZipcode(results.getString("zipcode"));
			sup.setCountry(results.getString("country"));
			sup.setDescription(results.getString("description"));
			sup.setCVR(results.getString("CVR"));

		}
		catch(SQLException se)
		{
			System.out.println("There was an error while creating supomer!");
			se.printStackTrace();
			return null;
		}
		return sup;
	}
	

}
