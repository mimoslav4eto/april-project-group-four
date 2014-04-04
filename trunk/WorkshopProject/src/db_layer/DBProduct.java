package db_layer;
import java.sql.Connection;
import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model_layer.Product;
import db_layer.DBConnection;
import db_layer.UtilityFunctions;


public class DBProduct
{
	private Connection con;
	public DBProduct()
	{
		con = DBConnection.get_instance().get_connection();
	}
	
	public Product find_product(int id, boolean make_association)
	{
		String var = "id=";
		return single_where(var, "", id, make_association);
	}
	
	public ArrayList<Product> get_all_products(boolean make_association)
	{
		return multiple_where("", "", -1, make_association);
	}
	
	
	public int insert_product(Product prod)
	{
		
		int rc = -1;
		String name = prod.getName();
		int min_amount = prod.getMin_amount();
		Float price = prod.getPrice();
		float retail_price = prod.getRetail_price();
		Float rent_price = prod.getRent_price();
		int amount = prod.getAmount();
		

		System.out.println("Product to be inserted: " + name);
		try
		{
			int id = UtilityFunctions.get_max_id("SELECT max(id) FROM Product") + 1;
			PreparedStatement stmt = UtilityFunctions.make_insert_statement(con, "Product", "id, name, retail_price, price, rent_price, min_amount, amount");
			stmt.setInt(1, id);
			stmt.setString(2, name);
			stmt.setFloat(3, retail_price);
			if(price != null) { stmt.setFloat(4, price); }
			else { stmt.setNull(4, Types.FLOAT); }
			
			if(rent_price != null) { stmt.setFloat(5, rent_price); }
			else { stmt.setNull(5, Types.FLOAT); }
			
			stmt.setInt(6, min_amount);
			stmt.setInt(7, amount);
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();
			try
			{
				prod.setId(id);
				DBSuppliesWith db = new DBSuppliesWith();
				db.insert_relation(prod, prod.getSupplied_by().get(0));
			}
			catch(NullPointerException npe) {}
		}
		catch (SQLException se)
		{
			System.out.println("Error while inserting product: " + se);
		}
		return rc;
	}
	
	public int update_product(Product prod)
	{
		int rc = -1;
		int id = prod.getId();
		String name = prod.getName();
		float retail_price = prod.getRetail_price();
		Float price = prod.getPrice();
		Float rent_price = prod.getRent_price();
		int min_amount = prod.getMin_amount();
		int amount = prod.getAmount();
		try
		{
			PreparedStatement stmt = UtilityFunctions.make_update_statement(con, "Product", "name, retail_price, price, rent_price, min_amount, amount", "id");
			stmt.setString(1, name);
			stmt.setFloat(2, retail_price);
			
			if (price != null) { stmt.setFloat(3, price); }
			else { stmt.setNull(3, Types.FLOAT); }
			
			if(rent_price != null) { stmt.setFloat(4, rent_price); }
			else { stmt.setNull(5, Types.FLOAT); }
			
			stmt.setInt(5, min_amount);
			stmt.setInt(6, amount);
			stmt.setInt(7, id);
			
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
		}
		catch(SQLException se)
		{
			System.out.println("Error while updating product: " + se);
		}
		return rc;
	}
	
	
	private Product single_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		Product prod = null;
		String query = make_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				prod = create_product(results);
				if (make_association)
				{
					DBSuppliesWith db_s = new DBSuppliesWith();
					prod.setSupplied_by(db_s.find_suppliers_of(prod.getId()));
				}
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Product query exception: " + e);
		}
		return prod;
	}
	
	private ArrayList<Product> multiple_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		ArrayList<Product> products = new ArrayList<Product>();
		Product prod;
		String query = make_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			while(results.next())
			{
				prod = create_product(results);
				if (make_association)
				{
					DBSuppliesWith db_s = new DBSuppliesWith();
					prod.setSupplied_by(db_s.find_suppliers_of(prod.getId()));
				}
				products.add(prod);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Product query exception: " + e);
		}
		return products;
	}
	
	private String make_query(String var)
	{
		String query = "SELECT id, name, retail_price, price, rent_price, min_amount, amount FROM Product";
		if (var.length() > 0)
		{
			query += " WHERE " + var + "?";
		}
		
		return query;
	}
	
	protected Product create_product(ResultSet results)
	{
		Product prod = new Product();
		try
		{
			prod.setId(results.getInt("id"));
			prod.setName(results.getString("name"));
			prod.setRetail_price(results.getFloat("retail_price"));
			prod.setPrice(results.getFloat("price"));
			prod.setRent_price(results.getFloat("rent_price"));
			prod.setMin_amount(results.getInt("min_amount"));
			prod.setAmount(results.getInt("amount"));
			return prod;
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating a product: " + se);
			return null;
		}
	}
	
	
	
}
