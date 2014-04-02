package db_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
		String var = "username=";
		return single_where(var, "", id);
	}
	
	public ArrayList<Customer> get_all_customers()
	{
		return multiple_where("", "", -1);
	}
	
	public int insert_customer(Customer cust)
	{
		System.out.println("User to be inserted: " + cust.getName());
		int rc = -1;
		String name = cust.getName();
		String email = cust.getEmail();
		String zipcode = cust.getZipcode();
		String city = cust.getCity();
		String  phone_nr = cust.getPhone_nr();
		String preferences = cust.getPreferences();
		String address = cust.getAddress();
		int t_id = cust.getCust_type().getId();
		try
		{
			PreparedStatement stmt;
			if (!zip_code_exists(zipcode))
			{
				stmt = make_insert_statement("ZipCity", "zipcode, city, country");
				stmt.setString(1, zipcode);
				stmt.setString(2, city);
				stmt.setString(3, "Denmark");
				stmt.close();
			}
			int id = GetMax.get_max_id("SELECT id FROM Entity");
			stmt = make_insert_statement("Entity", "id, name, phone_nr, email, address, zipcode, type");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setString(3, phone_nr);
			stmt.setString(4, email);
			stmt.setString(5, address);
			stmt.setString(6, zipcode);
			stmt.setString(7, "customer");
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
			
			stmt = make_insert_statement("Customer", "id, t_id, preferences");
			stmt.setInt(1, id);
			stmt.setInt(2, t_id);
			stmt.setString(3, preferences);
			stmt.setQueryTimeout(5);
			stmt.close();
		}
		catch (SQLException ex)
		{
			System.out.println("Error while inserting: " + ex);
		}
		return rc;
	}
	
	private boolean zip_code_exists(String zipcode)
	{
		try
		{
			Statement st = con.createStatement();
			st.closeOnCompletion();
			return st.executeQuery("SELECT * FROM ZipCity WHERE zipcode=" + zipcode).next();
		}
		catch(SQLException se)
		{
			System.out.println("Error occured while searching for a zipcode " + se);
			return false;
		}
	}
	
	private PreparedStatement make_insert_statement(String table, String var_names)
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
			return con.prepareStatement(String.format("INSERT INTO %s(%s) VALUES(%s)", table, var_names, quest_marks));
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating insert statement: " + se);
			return null;
		}
	}
	
	private Customer single_where(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
		Customer cust = null;
		String query = make_complete_query(var);
		try
		{
			PreparedStatement stmt = prepare_statement(query, where_clause, int_where_clause);
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
			System.out.println("Query exception: " + e);
		}
		return cust;
	}
	
	private ArrayList<Customer> multiple_where(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
	    ArrayList<Customer> customers = new ArrayList<Customer>();	
	    String query = make_complete_query(var);
	    try
		{
			PreparedStatement stmt = prepare_statement(query, where_clause, int_where_clause);//con.createStatement();
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			while(results.next())
			{
				customers.add(create_customer(results));
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Query exception: " + e);
		}
	 return customers;   
	}
	
	
	private String make_complete_query(String var)
	{
		String query = "SELECT e.id, e.name, e.phone_nr, e.email, c.preferences, "
				+ "ct.pr_qual_disc, ct.dic_perc, ct.pr_qual_free_ship, ct.id, z.zipcode, z.city "
				+ "FROM Entity AS e, Customer AS c, ZipCity AS z, CustomerType AS ct "
				+ "WHERE type='Customer', e.id = c.id, z.zipcode = e.zipcode, c.t_id = ct.id";
		if (var.length() > 0)
		{
			query += ", " + var + "?";
		}
		return query;
	}
	
	
	private PreparedStatement prepare_statement(String query, String where_clause, int int_where_clause)
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
	
	private Customer create_customer(ResultSet results)
	{
		Customer cust = new Customer();
		CustomerType ct = new CustomerType();
		try
		{
			ct.setDisc_perc(results.getFloat("ct.disc_perc"));
			ct.setPrice_qual_for_disc(results.getFloat("ct.pr_qual_disc"));
			ct.setPrice_qual_for_free_shipment(results.getFloat("ct.pr_qual_free_ship"));
			ct.setId(results.getInt("ct.id"));
			
			cust.setName(results.getString("e.name"));
			cust.setAddress(results.getString("e.address"));
			cust.setCity(results.getString("z.city"));
			cust.setEmail(results.getString("e.email"));
			cust.setId(results.getInt("e.id"));
			cust.setPhone_nr(results.getString("e.phone_nr"));
			cust.setPreferences(results.getString("c.preferences"));
			cust.setZipcode(results.getString("z.zipcode"));
			
			cust.setCust_type(ct);
		}
		catch(SQLException ex)
		{
			System.out.println("There was an error while creating customer!");
			ex.printStackTrace();
			return null;
		}
		return cust;
	}

}
