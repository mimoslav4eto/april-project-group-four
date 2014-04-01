package model_layer;

import java.util.ArrayList;

public class Supplier extends Entity
{
	private String CVR;
	private String description;
	private ArrayList<Product> supplies_with;

	public Supplier()
	{
		// TODO Auto-generated constructor stub
	}



	public String getCVR()
	{
		return CVR;
	}



	public void setCVR(String cVR)
	{
		CVR = cVR;
	}



	public String getDescription()
	{
		return description;
	}



	public void setDescription(String description)
	{
		this.description = description;
	}



	public ArrayList<Product> getSupplies_with()
	{
		return supplies_with;
	}

	public void setSupplies_with(ArrayList<Product> supplies_with)
	{
		this.supplies_with = supplies_with;
	}
	
	public void add_product(Product product)
	{
		supplies_with.add(product);
	}

}
