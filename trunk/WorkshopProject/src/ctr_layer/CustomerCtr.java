package ctr_layer;

import model_layer.Customer;
import model_layer.CustomerType;
import db_layer.DBCustomer;

public class CustomerCtr
{
	DBCustomer db;
	Customer cust;
	CustomerType ct;
	public CustomerCtr()
	{
		db = new DBCustomer();
		cust = new Customer();
		ct = new CustomerType();
	}
	
	private Customer find_customer(int id)
	{
		if (cust.getId() == id)
		{
			return cust;
		}
		cust = db.find_customer(id);
		
		if (cust != null)
		{
			return cust;
		}
		return null;
	}
	
	private CustomerType find_customer_type(int id)
	{
		if (ct.getId() == id)
		{
			return ct;
		}
		ct = db.find_customer_type(id);
		
		if(ct != null)
		{
			return ct;
		}
		return null;
		
	}
	
	public boolean add_customer(String name, String phone_nr, String email,
			String address, String zipcode, String city, String preferences, int type_id)
	{
		CustomerType ct = find_customer_type(type_id);
		System.out.println("ID: " +ct.getId());
		cust = new Customer(name, phone_nr, email, address, zipcode, city, ct, preferences);
		int rc = db.insert_customer(cust);
		return rc ==1;
		
	}
	
	public boolean add_customer_type(float price_qual_for_disc, float price_qual_for_free_shipment, float disc_perc)
	{
		CustomerType ct = new CustomerType(price_qual_for_disc, price_qual_for_free_shipment, disc_perc);
		int rc = db.insert_customer_type(ct);
		return rc == 1;
	}
	
	public boolean customer_exists(int customer_id)
	{
		return find_customer(customer_id) != null;
	}

}
