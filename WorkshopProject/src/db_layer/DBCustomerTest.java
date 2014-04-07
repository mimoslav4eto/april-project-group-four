package db_layer;

import static org.junit.Assert.*;
import model_layer.Customer;
import model_layer.CustomerType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ctr_layer.CustomerCtr;

public class DBCustomerTest {
	DBCustomer customer=new DBCustomer();;
	int var;
	/**
	 * Creates 1000 Customers and Updates them. 
	 * I doeasn't include delete, because Customers cannot be deleted from the system.
	 * Only part of their fields are null-ed for security reasons.
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		for (int k=0; k<50; k++)
		{
			testInsert_type();
		}
		
		for (int i=1;i<=200;i++)
		{
			testInsert_customer();
			
			
		}
		for (int j =100; j<=200; j++)
		{
			var = j;
			testUpdate_customer();
		}
		
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testInsert_type()
	{
		CustomerType test =new CustomerType(random_float(), random_float(), random_float());
		assertEquals(customer.insert_customer_type(test) == 1, true);
	}

	@Test
	public void testInsert_customer() {
		Customer test = new Customer("Mikkel Jensen", String.valueOf(random_number(8)),"abc@gmail.com", "Boulevarden",
				String.valueOf(random_number(4)), "Aalborg", customer.find_customer_type(2), "Special customer with different discounts");
		assertEquals((customer.insert_customer(test))!=-1,true);
		
	}
	@Test
	public void testUpdate_customer() {
		Customer test = customer.find_customer(var);
		test.setAddress("Boulevarden");
		test.setCity("Copenhagen");
		test.setEmail("abcd@gmail.com");
		test.setName("Jens Larsen");
		test.setPhone_nr(String.valueOf(random_number(8)));
		test.setPreferences("Ordinray Customer with nothig special");
		test.setZipcode(String.valueOf(random_number(4)));
		assertEquals(customer.update_customer(test)!=-1,true);
	}
	

	private int random_number(int digits)
	{
	       long timeSeed = System.nanoTime();

	        double randSeed = Math.random() * 1000;
	        long midSeed = (long) (timeSeed * randSeed);

	        String s = midSeed + "";
	        String subStr = s.substring(0, digits);

	        int finalSeed = Integer.parseInt(subStr);

	        return finalSeed;
	}
	private float random_float()
	{
	       long timeSeed = System.nanoTime();

	        double randSeed = Math.random() * 100;
	        float midSeed = (float) ((timeSeed%1000) * randSeed);

	        return midSeed;
	}

}
