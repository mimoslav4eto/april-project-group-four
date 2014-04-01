package model_layer;
import java.util.ArrayList;
public class Order extends Sale
{
	private ArrayList<SaleLineItem> items;

	public Order()
	{
		items = new ArrayList<SaleLineItem>();
	}
	
	public void add_item(SaleLineItem sli)
	{
		items.add(sli);
	}

	public ArrayList<SaleLineItem> getItems()
	{
		return items;
	}

	public void setItems(ArrayList<SaleLineItem> items)
	{
		this.items = items;
	}

}
