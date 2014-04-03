package db_layer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model_layer.Customer;
import model_layer.Delivery;
import model_layer.Rent;
import model_layer.RentLineItem;

public class DBRent
{
	Connection con;
	DBDelivery db_d;
	public DBRent()
	{
		con = DBConnection.get_instance().get_connection();
		db_d = new DBDelivery();
	}
	
	public Rent find_rent(int id, boolean make_association)
	{
		String var = "rent_id=";
		return single_where(var, "", id, make_association);
	}
	
	public ArrayList<Rent> get_all_rents(boolean make_association)
	{
		return multiple_where("", "", -1, make_association);
	}
	
	public int insert_rent(Rent rent)
	{
		
		int rc = -2;
		float rent_price = rent.getRent_price();
		Date date = new Date(rent.getDate().getTime());
		Date return_date = new Date(rent.getReturn_date().getTime());
		Customer cust = rent.getCustomer();
		Delivery del = rent.getDelivery();
		ArrayList<RentLineItem> items = rent.getItems();
		int size = items.size();
		try
		{
			if(del != null)
			{
				
				del.setId(db_d.insert_delivery(del));
			}
			int id = UtilityFunctions.get_max_id("SELECT max(rent_id) FROM Rent") + 1;
			System.out.println("Rent to be inserted: " + id);
			PreparedStatement stmt = UtilityFunctions.make_insert_statement(con, "Rent", "rent_id, rent_price, date, return_date, customer_id, delivery_id");
			stmt.setInt(1, id);
			stmt.setFloat(2, rent_price);
			stmt.setDate(3, date);
			stmt.setDate(4, return_date);
			stmt.setInt(5, cust.getId());
			
			if(del != null) { stmt.setInt(6, del.getId()); }
			else { stmt.setNull(6, java.sql.Types.INTEGER); }
			
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
			
			stmt = UtilityFunctions.make_multiple_insert_statement(con, "RentLineItem", "id, daily_price, r_id", size);
			PreparedStatement lstmt = UtilityFunctions.make_multiple_insert_statement(con, "LineItem", "id, product_id, amount", size);
			int i = 0;
			int item_id = UtilityFunctions.get_max_id("SELECT max(id) FROM LineItem") + 1;
			for (RentLineItem item : items)
			{
				
				stmt.setInt(3*i + 1, item_id);
				stmt.setFloat(3*i + 2, item.getDaily_price());
				stmt.setInt(3*i + 3, id);
				
				lstmt.setInt(3*i + 1, item_id);
				lstmt.setInt(3*i + 2, item.getProduct().getId());
				lstmt.setFloat(3*i + 3, item.getAmount());
				item_id++;
				i++;
			}
			lstmt.setQueryTimeout(5);
			rc += lstmt.executeUpdate()/size;
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate()/size;
			lstmt.close();
			stmt.close();
		}
		catch (SQLException se)
		{
			System.out.println("Error while inserting rent: " + se);
		}
		return rc;
	}
	
	public Rent single_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		Rent rent = null;
		String query = make_rent_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				rent = create_rent(results);
				if (make_association)
				{
					query = make_item_query();
					stmt = UtilityFunctions.prepare_statement(con, query, "", rent.getRent_id());
					stmt.setQueryTimeout(5);
					ResultSet items_result = stmt.executeQuery();
					rent.setItems(create_items(items_result));
				}
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Rent query exception: " + e);
		}
		return rent;
	}
	
	public ArrayList<Rent> multiple_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		ArrayList<Rent> rents = new ArrayList<Rent>();
		Rent rent;
		String query = make_rent_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			while(results.next())
			{
				rent = create_rent(results);
				if (make_association)
				{
					query = make_item_query();
					stmt = UtilityFunctions.prepare_statement(con, query, "", rent.getRent_id());
					stmt.setQueryTimeout(5);
					ResultSet items_result = stmt.executeQuery();
					rent.setItems(create_items(items_result));
				}
				rents.add(rent);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Rent query exception: " + e);
		}
		return rents;
	}
	
	private String make_rent_query(String var)
	{
		String query = "SELECT rent_id, rent_price, date, return_date, customer_id, delivery_id "
				+ "FROM Rent";
		if(var.length() > 0)
		{
			query += " WHERE " + var + "?";
		}
		System.out.println("QUERY: " + query);
		return query;
	}
	
	private String make_item_query()
	{
		String query = "SELECT r.id, r.daily_price, l.amount, l.product_id "
				+ "FROM RentLineItem AS r, LineItem AS l "
				+ "WHERE r.id = l.id AND r.r_id = ?";
		return query;		
	}
	
	private Rent create_rent(ResultSet results)
	{
		Rent rent = null;
		DBCustomer db_c = new DBCustomer();
		DBDelivery db_d = new DBDelivery();
		try
		{
			rent = new Rent();
			rent.setCustomer(db_c.find_customer(results.getInt("customer_id")));
			rent.setDelivery(db_d.find_delivery(results.getInt("delivery_id")));
			
			rent.setRent_id(results.getInt("rent_id"));
			rent.setRent_price(results.getFloat("rent_price"));
			
			java.util.Date date = new java.util.Date();
			date.setTime(results.getDate("date").getTime());
			rent.setDate(date);
			date = new java.util.Date();
			date.setTime(results.getDate("return_date").getTime());
			rent.setReturn_date(date);
			
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating rent: " + se);
		}
		return rent;
	}
	
	private ArrayList<RentLineItem> create_items(ResultSet results)
	{
		DBProduct db_p = new DBProduct();
		ArrayList<RentLineItem> items = new ArrayList<RentLineItem>();
		RentLineItem item;
		try
		{
			while(results.next())
			{
				item = new RentLineItem();
				item.setId(results.getInt("id"));
				item.setAmount(results.getInt("amount"));
				item.setDaily_price(results.getFloat("daily_price"));
				item.setProduct(db_p.find_product(results.getInt("product_id"), false));
				items.add(item);
			}
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating items: " + se);
			return null;
		}
		return items;
	}

}
