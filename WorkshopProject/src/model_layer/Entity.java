package model_layer;

public class Entity
{
	private String name;
	private int id;
	private String phone_nr;
	private String email;
	private String address;
	private String zipcode;
	private String city;

	public Entity()
	{
		// TODO Auto-generated constructor stub
	}

	public Entity(String name, String phone_nr, String email, String address, String zipcode, String city)
	{
		this.name = name;
		this.phone_nr = phone_nr;
		this.email = email;
		this.address = address;
		this.zipcode = zipcode;
		this.city = city;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getPhone_nr()
	{
		return phone_nr;
	}

	public void setPhone_nr(String phone_nr)
	{
		this.phone_nr = phone_nr;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getZipcode()
	{
		return zipcode;
	}

	public void setZipcode(String zipcode)
	{
		this.zipcode = zipcode;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}
	

}
