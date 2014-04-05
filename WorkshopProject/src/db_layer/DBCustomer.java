package db_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model_layer.Customer;
import model_layer.CustomerType;

public class DBCustomer
{
	private Connection con;
	public DBCustomer()
	{
		con = DBConnection.get_instance().get_connection();
	}
	
	public Customer find_customer(int id)
	{
		String var = "c.id=";
		return single_where_customer(var, "", id);
	}
	
	public CustomerType find_customer_type(int id)
	{
		String var = "id=";
		return single_where_type(var, "", id);
	}
	
	public HashMap<Integer, Customer> get_all_customers()
	{
		return multiple_where_customer("", "", -1);
	}
	
	public HashMap<Integer, Customer> get_some_customers(String variable_name, String where_clause, int int_where_clause)
	{
		String var = "e." + variable_name + "=";
		return multiple_where_customer(var, where_clause, int_where_clause);
	}
	
	public ArrayList<CustomerType> get_all_types()
	{
		return multiple_where_type("", "", -1);
	}
	
	public int insert_customer(Customer cust)
	{
		
		int rc = -1;
		int id = -1;
		String name = cust.getName();
		String email = cust.getEmail();
		String zipcode = cust.getZipcode();
		String city = cust.getCity();
		String  phone_nr = cust.getPhone_nr();
		String preferences = cust.getPreferences();
		String address = cust.getAddress();
		int t_id = cust.getCust_type().getId();
		System.out.println("Customer to be inserted: " + name);
		try
		{
			PreparedStatement stmt;
			if (!zip_code_exists(zipcode))
			{
				stmt = UtilityFunctions.make_insert_statement(con, "ZipCity", "zipcode, city, country");
				stmt.setString(1, zipcode);
				stmt.setString(2, city);
				stmt.setString(3, "Denmark");
				stmt.executeUpdate();
				stmt.close();
			}
			id = UtilityFunctions.get_max_id("SELECT max(id) FROM Entity") + 1;
			stmt = UtilityFunctions.make_insert_statement(con, "Entity", "id, name, phone_nr, email, address, zipcode, type");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, phone_nr);
			stmt.setString(4, email);
			stmt.setString(5, address);
			stmt.setString(6, zipcode);
			stmt.setString(7, "customer");
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
			
			stmt = UtilityFunctions.make_insert_statement(con, "Customer", "id, t_id, preferences");
			stmt.setInt(1, id);
			stmt.setInt(2, t_id);
			stmt.setString(3, preferences);
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
		}
		catch (SQLException se)
		{
			System.out.println("Error while inserting customer: " + se);
		}
		if(rc != 1)
		{
			id = -1;
		}
		return id;
	}
	
	public int update_customer(Customer cust)
	{
		int rc = -1;
		int id = cust.getId();
		String name = cust.getName();
		String email = cust.getEmail();
		String zipcode = cust.getZipcode();
		String city = cust.getCity();
		String phone_nr = cust.getPhone_nr();
		String preferences = cust.getPreferences();
		String address = cust.getAddress();
		int t_id = cust.getCust_type().getId();
		System.out.println("Customer to be updated: " + name);
		try
		{
			PreparedStatement stmt;
			if(name != null)
			{
				if (!zip_code_exists(zipcode))
				{
					stmt = UtilityFunctions.make_insert_statement(con, "ZipCity", "zipcode, city, country");
					stmt.setString(1, zipcode);
					stmt.setString(2, city);
					stmt.setString(3, "Denmark");
				}
				else
				{
					stmt = UtilityFunctions.make_update_statement(con, "ZipCity", "city", "zipcode");
					stmt.setString(1, city);
					stmt.setString(2, zipcode);
				}
				stmt.executeUpdate();
				stmt.close();
			}
			
			stmt = UtilityFunctions.make_update_statement(con, "Entity", "name, phone_nr, email, address, zipcode, type", "id");
			
			stmt.setString(1, name);
			stmt.setString(2, phone_nr);
			stmt.setString(3, email);
			stmt.setString(4, address);
			stmt.setString(5, zipcode);
			stmt.setString(6, "customer");
			stmt.setInt(7, id);
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
			
			stmt = UtilityFunctions.make_update_statement(con, "Customer", "t_id, preferences", "id");
			
			stmt.setInt(1, t_id);
			stmt.setString(2, preferences);
			stmt.setInt(3, id);
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException se)
		{
			System.out.println("Error while updating customer: " + se);
		}
		return rc;
	}
	
	public int insert_customer_type(CustomerType ct)
	{
		int rc = -1;
		int id = UtilityFunctions.get_max_id("SELECT max(id) FROM CustomerType") + 1;
		float disc_perc = ct.getDisc_perc();
		float pr_qual_free_ship = ct.getPrice_qual_for_free_shipment();
		float pr_qual_disc = ct.getPrice_qual_for_disc();
		System.out.println("Inserting customer type: " + id);
		
		try
		{
			PreparedStatement stmt = UtilityFunctions.make_insert_statement(con, "CustomerType", "id, disc_perc, pr_qual_disc, pr_qual_free_ship");
			stmt.setInt(1, id);
			stmt.setFloat(2, disc_perc);
			stmt.setFloat(3, pr_qual_disc);
			stmt.setFloat(4, pr_qual_free_ship);
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException se)
		{
			System.out.println("Error while inserting customer type: " + se);
		}
		return rc;
	}
	
	public int update_customer_type(CustomerType ct)
	{
		int rc = -1;
		int id = ct.getId();
		float disc_perc = ct.getDisc_perc();
		float pr_qual_free_ship = ct.getPrice_qual_for_free_shipment();
		float pr_qual_disc = ct.getPrice_qual_for_disc();
		
		System.out.println("Updating customer type: " + id);
		
		try
		{
			PreparedStatement stmt = UtilityFunctions.make_update_statement(con, "CustomerType", "disc_perc, pr_qual_disc, pr_qual_free_ship", "id");
			
			stmt.setFloat(1, disc_perc);
			stmt.setFloat(2, pr_qual_disc);
			stmt.setFloat(3, pr_qual_free_ship);
			stmt.setInt(4, id);
			
			rc = stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException se)
		{
			System.out.println("Error while updating customer type: " + se);
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
	
	
	private Customer single_where_customer(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
		Customer cust = null;
		String query = make_complete_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				cust = create_customer(results);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Customer query exception: " + e);
		}
		return cust;
	}
	
	private CustomerType single_where_type(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
		CustomerType ct = null;
		String query = make_customer_type_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				ct = create_customer_type(results);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Type query exception: " + e);
		}
		return ct;
	}
	
	private ArrayList<CustomerType> multiple_where_type(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
	    ArrayList<CustomerType> types= new ArrayList<CustomerType>();	
	    String query = make_customer_type_query(var);
	    try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			while(results.next())
			{
				types.add(create_customer_type(results));
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Customer query exception: " + e);
		}
	 return types;   
	}
	
	private HashMap<Integer, Customer> multiple_where_customer(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
		HashMap<Integer, Customer> customers = new HashMap<Integer, Customer>();	
	    String query = make_complete_query(var);
	    try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			Customer cust;
			while(results.next())
			{
				cust = create_customer(results);
				customers.put(cust.getId(), cust);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Customer query exception: " + e);
		}
	 return customers;   
	}
	
	
	private String make_complete_query(String var)
	{
		String query = "SELECT e.id AS e_id, e.name, e.address, e.phone_nr, e.email, c.preferences, "
				+ "ct.pr_qual_disc, ct.disc_perc, ct.pr_qual_free_ship, ct.id, z.zipcode, z.city "
				+ "FROM Entity AS e, Customer AS c, ZipCity AS z, CustomerType AS ct "
				+ "WHERE type='customer' AND e.id = c.id AND z.zipcode = e.zipcode AND c.t_id = ct.id";
		if (var.length() > 0)
		{
			query += " AND " + var + "?";
		}
		System.out.println("QUERY: " + query);
		return query;
	}
	
	private String make_customer_type_query(String var)
	{
		String query = "SELECT id, disc_perc, pr_qual_disc, pr_qual_free_ship FROM CustomerType";
		if (var.length() > 0)
		{
			query += " WHERE " + var + "?";
		}
		System.out.println("Query: " + query);
		return query;
	}
	
	
	private Customer create_customer(ResultSet results)
	{
		Customer cust = new Customer();
		CustomerType ct = new CustomerType();
		try
		{
			ct.setDisc_perc(results.getFloat("disc_perc"));
			ct.setPrice_qual_for_disc(results.getFloat("pr_qual_disc"));
			ct.setPrice_qual_for_free_shipment(results.getFloat("pr_qual_free_ship"));
			ct.setId(results.getInt("id"));
			
			cust.setName(results.getString("name"));
			cust.setAddress(results.getString("address"));
			cust.setCity(results.getString("city"));
			cust.setEmail(results.getString("email"));
			cust.setId(results.getInt("e_id"));
			cust.setPhone_nr(results.getString("phone_nr"));
			cust.setPreferences(results.getString("preferences"));
			cust.setZipcode(results.getString("zipcode"));
		
			cust.setCust_type(ct);
		}
		catch(SQLException se)
		{
			System.out.println("There was an error while creating customer!");
			se.printStackTrace();
			return null;
		}
		return cust;
	}
	
	private CustomerType create_customer_type(ResultSet results)
	{
		CustomerType ct = new CustomerType();
		try
		{
			ct.setDisc_perc(results.getFloat("disc_perc"));
			ct.setPrice_qual_for_disc(results.getFloat("pr_qual_disc"));
			ct.setPrice_qual_for_free_shipment(results.getFloat("pr_qual_free_ship"));
			ct.setId(results.getInt("id"));
			System.out.println("ID: " + ct.getId());
		}
		catch(SQLException se)
		{
			System.out.println("There was an error while creating customer type!");
			se.printStackTrace();
			return null;
		}
		return ct;
	}
	

}
