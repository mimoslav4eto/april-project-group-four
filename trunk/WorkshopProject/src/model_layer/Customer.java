package model_layer;

public class Customer extends Entity
{

	private CustomerType cust_type;
	private String preferences;

	public Customer()
	{
		// TODO Auto-generated constructor stub
	}
	

	public Customer(String name, String phone_nr, String email, String address, String zipcode, String city, CustomerType cust_type, String preferences)
	{
		super(name, phone_nr, email, address, zipcode, city);
		this.cust_type = cust_type;
		this.preferences = preferences;
	}


	public CustomerType getCust_type()
	{
		return cust_type;
	}

	public void setCust_type(CustomerType cust_type)
	{
		this.cust_type = cust_type;
	}

	public String getPreferences()
	{
		return preferences;
	}

	public void setPreferences(String preferences)
	{
		this.preferences = preferences;
	}
	
	


}
