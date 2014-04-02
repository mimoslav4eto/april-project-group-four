package ctr_layer;

import model_layer.Customer;
import db_layer.DBCustomer;

public class CustomerCtr
{
	DBCustomer db;
	Customer cust;
	public CustomerCtr()
	{
		db = new DBCustomer();
		cust = new Customer();
	}
	
	public String find_customer_name(int id)
	{
		if (cust.getId() == id)
		{
			return cust.getName();
		}
		cust = db.find_customer(id);
		
		if (cust != null)
		{
			return cust.getName();
		}
		return null;
	}

}
