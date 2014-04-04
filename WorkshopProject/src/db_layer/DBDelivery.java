package db_layer;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model_layer.Delivery;


public class DBDelivery
{
	private Connection con;
	public DBDelivery()
	{
		con = DBConnection.get_instance().get_connection();
	}
	
	public int insert_delivery(Delivery del)
	{
		float cost = del.getCost();
		boolean i_p = del.isIn_progress();
		boolean p_o_d = del.isPay_on_delivery();
		int id = -1;
		int in_progress = i_p ? 1: 0;
		int pay_on_delivery = p_o_d ? 1 : 0;
		
		java.util.Date d = del.getDate();
		Date date = new Date(d.getTime());
		
		try
		{
			id = UtilityFunctions.get_max_id("SELECT max(id) FROM Delivery") + 1; 
			PreparedStatement stmt = UtilityFunctions.make_insert_statement(con, "Delivery", "id, cost, date, in_progress, pay_on_delivery");
			stmt.setInt(1, id);
			stmt.setFloat(2, cost);
			stmt.setDate(3, date);
			stmt.setInt(4, in_progress);
			stmt.setInt(5, pay_on_delivery);
			
			stmt.setQueryTimeout(5);
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException se)
		{
			System.out.println("Error while inserting delivery: " + se);
		}
		return id;
	}
	
	public int update_delivery(Delivery del)
	{
		int id = del.getId();
		float cost = del.getCost();
		boolean i_p = del.isIn_progress();
		boolean p_o_d = del.isPay_on_delivery();
		int in_progress = i_p ? 1: 0;
		int pay_on_delivery = p_o_d ? 1 : 0;
		
		java.util.Date d = del.getDate();
		Date date = new Date(d.getTime());
		
		try
		{
			PreparedStatement stmt = UtilityFunctions.make_update_statement(con, "Delivery", "cost, date, in_progress, pay_on_delivery", "id");
			
			stmt.setFloat(1, cost);
			stmt.setDate(2, date);
			stmt.setInt(3, in_progress);
			stmt.setInt(4, pay_on_delivery);
			stmt.setInt(5, id);
			
			stmt.setQueryTimeout(5);
			stmt.executeUpdate();
			stmt.close();
		}
		catch(SQLException se)
		{
			System.out.println("Error while updating delivery: " + se);
		}
		return id;
	}
	
	public Delivery find_delivery(int id)
	{
		String var = "id=";
		return single_where(var, "", id);
	}
	
	private Delivery single_where(String var, String where_clause, int int_where_clause)
	{
		ResultSet results;
		Delivery del = null;
		String query = make_query(var);
		try
		{
			PreparedStatement stmt = UtilityFunctions.prepare_statement(con, query, where_clause, int_where_clause);
			stmt.setQueryTimeout(5);
			results = stmt.executeQuery();
			if(results.next())
			{
				del = create_delivery(results);
			}
			stmt.close();
		}
		catch(Exception e)
		{
			System.out.println("Delivery query exception: " + e);
		}
		return del;
	}
	
	private String make_query(String var)
	{
		String query = "SELECT id, in_progress, date, cost, pay_on_delivery "
				+ "FROM Delivery";
		if(var.length() > 0)
		{
			query += " WHERE " + var + "?";
		}
		return query;
	}
	
	private Delivery create_delivery(ResultSet results)
	{
		Delivery del = new Delivery();
		try
		{
			del.setCost(results.getFloat("cost"));
			del.setId(results.getInt("id"));
			del.setIn_progress(results.getInt("in_progress") == 1);
			del.setPay_on_delivery(results.getInt("pay_on_delivery") == 1);
			java.util.Date date = new java.util.Date();
			date.setTime(results.getDate("date").getTime());
			del.setDate(date);
		}
		catch(SQLException se)
		{
			System.out.println("Error while creating delivery: " + se);
			return null;
		}
		return del;
	}

}
