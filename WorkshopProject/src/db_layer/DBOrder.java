package db_layer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;



import java.sql.Types;
import java.util.ArrayList;

import model_layer.Customer;
import model_layer.Order;
import model_layer.Delivery;
import model_layer.SaleLineItem;

public class DBOrder
{
	Connection con;
	DBDelivery db_d;
	public DBOrder()
	{
		con = DBConnection.get_instance().get_connection();
		db_d = new DBDelivery();
	}
	
	public Order find_order(int id, boolean make_association)
	{
		String var = "sale_id=";
		return single_where(var, "", id, make_association);
	}
	
	public ArrayList<Order> find_specific_orders(String variable_name, String where_clause, int int_where_clause, boolean make_association)
	{
		String var = variable_name + "=";
		return multiple_where(var, where_clause, int_where_clause, make_association);
	}
	
	public ArrayList<Order> get_all_orders(boolean make_association)
	{
		return multiple_where("", "", -1, make_association);
	}
	
	public int insert_order(Order ord)
	{
		
		int rc = -2;
		float total_price = ord.getTotal_price();
		
		Date payment_date = new Date(ord.getPayment_date().getTime());
		Customer cust = ord.getCustomer();
		Delivery del = ord.getDelivery();
		ArrayList<SaleLineItem> items = ord.getItems();
		int complete = ord.isComplete() ? 1:0;
		int size = items.size();
		try
		{
			if(del != null)
			{
				
				del.setId(db_d.insert_delivery(del));
			}
			int id = UtilityFunctions.get_max_id("SELECT max(sale_id) FROM Sale") + 1;
			int invoice_nr = UtilityFunctions.get_max_id("SELECT max(invoice_nr) FROM Sale") + 1;
			System.out.println("Order to be inserted: " + id);
			PreparedStatement stmt = UtilityFunctions.make_insert_statement(con, "Sale", "sale_id, price, payment_date, invoice_nr, customer_id, delivery_id, complete");
			stmt.setInt(1, id);
			stmt.setFloat(2, total_price);
			stmt.setDate(3, payment_date);
			stmt.setInt(4, invoice_nr);
			stmt.setInt(5, cust.getId());
			
			if(del != null) { stmt.setInt(6, del.getId()); }
			else { stmt.setNull(6, Types.INTEGER); }
			
			stmt.setInt(7, complete);
			
			stmt.setQueryTimeout(5);
			rc += stmt.executeUpdate();
			stmt.close();
			
			stmt = UtilityFunctions.make_multiple_insert_statement(con, "SaleLineItem", "id, total_price, s_id", size);
			PreparedStatement lstmt = UtilityFunctions.make_multiple_insert_statement(con, "LineItem", "id, product_id, amount", size);
			int i = 0;
			int i_id = UtilityFunctions.get_max_id("SELECT max(id) FROM LineItem") + 1;
			for (SaleLineItem item : items)
			{
				
				stmt.setInt(3*i + 1, i_id);
				stmt.setFloat(3*i + 2, item.getTotal_price());
				stmt.setInt(3*i + 3, id);
				
				lstmt.setInt(3*i + 1, i_id);
				lstmt.setInt(3*i + 2, item.getProduct().getId());
				lstmt.setFloat(3*i + 3, item.getAmount());
				i_id++;
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
			System.out.println("Error while inserting order: " + se);
		}
		return rc;
	}
	
	public int update_order(Order ord)
{
		
		int rc = -1;
		int id = ord.getOrder_id();
		float total_price = ord.getTotal_price();
		int invoice_nr = ord.getInvoice_nr();
		Date payment_date = new Date(ord.getPayment_date().getTime());
		Customer cust = ord.getCustomer();
		Delivery del = ord.getDelivery();
		int complete = ord.isComplete() ? 1:0;
		try
		{
			if(del != null)
			{
				
				db_d.update_delivery(del);
			}

			System.out.println("Order to be updated: " + id);
			PreparedStatement stmt = UtilityFunctions.make_update_statement(con, "Sale", "price, payment_date, invoice_nr, customer_id, delivery_id, complete", "sale_id");
			
			stmt.setFloat(1, total_price);
			stmt.setDate(2, payment_date);
			stmt.setInt(3, invoice_nr);
			stmt.setInt(4, cust.getId());
						
			if(del != null) { stmt.setInt(5, del.getId()); }
			else { stmt.setNull(5, Types.INTEGER); }
			
			stmt.setInt(6, complete);
			stmt.setInt(7, id);
			
			stmt.setQueryTimeout(5);
			rc = stmt.executeUpdate();
			stmt.close();

		}
		catch (SQLException se)
		{
			System.out.println("Error while updating order: " + se);
		}
		return rc;
	}
	
	public Order single_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		Order ord = null;
		String query = make_order_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				ord = create_order(results);
				if (make_association)
				{
					query = make_item_query();
					stmt = UtilityFunctions.prepare_statement(con, query, "", ord.getOrder_id());
					stmt.setQueryTimeout(5);
					ResultSet items_result = stmt.executeQuery();
					ord.setItems(create_items(items_result));
				}
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Order query exception: " + e);
		}
		return ord;
	}
	
	public ArrayList<Order> multiple_where(String var, String where_clause, int int_where_clause, boolean make_association)
	{
		ResultSet results;
		ArrayList<Order> orders = new ArrayList<Order>();
		Order ord;
		String query = make_order_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			while(results.next())
			{
				ord = create_order(results);
				if (make_association)
				{
					query = make_item_query();
					stmt = UtilityFunctions.prepare_statement(con, query, "", ord.getOrder_id());
					stmt.setQueryTimeout(5);
					ResultSet items_result = stmt.executeQuery();
					ord.setItems(create_items(items_result));
				}
				orders.add(ord);
				
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Order query exception: " + e);
		}
		return orders;
	}
	
	private String make_order_query(String var)
	{
		String query = "SELECT sale_id, price, payment_date, invoice_nr, customer_id, delivery_id, complete "
				+ "FROM Sale";
		if(var.length() > 0)
		{
			query += " WHERE " + var + "?";
		}
		System.out.println("QUERY: " + query);
		return query;
	}
	
	private String make_item_query()
	{
		String query = "SELECT s.id, s.total_price, l.amount, l.product_id "
				+ "FROM SaleLineItem AS s, LineItem AS l "
				+ "WHERE s.id = l.id AND s.s_id = ?";
		return query;		
	}
	
	private Order create_order(ResultSet results)
	{
		Order ord = null;
		DBCustomer db_c = new DBCustomer();
		DBDelivery db_d = new DBDelivery();
		try
		{
			ord = new Order();
			ord.setCustomer(db_c.find_customer(results.getInt("customer_id")));
			ord.setDelivery(db_d.find_delivery(results.getInt("delivery_id")));
			ord.setInvoice_nr(results.getInt("invoice_nr"));
			ord.setOrder_id(results.getInt("sale_id"));
			ord.setTotal_price(results.getFloat("price"));
			
			java.util.Date date = new java.util.Date();
			date.setTime(results.getDate("payment_date").getTime());
			ord.setPayment_date(date);
			ord.setComplete(results.getInt("complete") == 1);
			
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating order: " + se);
		}
		return ord;
	}
	
	private ArrayList<SaleLineItem> create_items(ResultSet results)
	{
		DBProduct db_p = new DBProduct();
		ArrayList<SaleLineItem> items = new ArrayList<SaleLineItem>();
		SaleLineItem item;
		try
		{
			while(results.next())
			{
				item = new SaleLineItem();
				item.setId(results.getInt("id"));
				item.setAmount(results.getInt("amount"));
				item.setTotal_price(results.getFloat("total_price"));
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
