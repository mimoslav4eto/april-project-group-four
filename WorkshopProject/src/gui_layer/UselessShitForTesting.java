package gui_layer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import ctr_layer.CustomerCtr;
import ctr_layer.OrderCtr;
import ctr_layer.RentCtr;
import ctr_layer.SupplyLineCtr;

public class UselessShitForTesting extends JFrame
{

	private JPanel contentPane;
	private JTextField textField;
	private CustomerCtr cc;
	private SupplyLineCtr slc;
	private RentCtr rc;
	private OrderCtr oc;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					UselessShitForTesting frame = new UselessShitForTesting();
					frame.setVisible(true);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UselessShitForTesting()
	{
		cc = new CustomerCtr();
		slc = new SupplyLineCtr();
		oc = new OrderCtr();
		rc = new RentCtr();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textField = new JTextField();
		contentPane.add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
//		textField.setText(String.valueOf(cc.add_customer_type(22, 33, 12)));
		
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, BorderLayout.WEST);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		contentPane.add(textField_2, BorderLayout.EAST);
		textField_2.setColumns(10);
		Date date = new Date();
		Date next_day = new Date();
		next_day.setTime(date.getTime() + 24*60*60*1000);
		
		
//		textField.setText("Adding supplier successfull: " + String.valueOf(slc.add_supplier("John", "223322", "email", "address", "DJ9234", "city", "reg45353", null, "France")));
//		textField_1.setText("Adding product successfull: " + String.valueOf(slc.add_product("product1", 25.34f, 4f, 1f, 24, 200, 29)));
//		textField_2.setText("Finding product: 1 " + String.valueOf(slc.supplier_exists(28) && slc.product_exists(1)));
		LinkedList<int[]> ids_amounts = new LinkedList<int[]>();
		for (int i = 1; i <= 11; i++)
		{
			int[] data = {i, i*5};
			ids_amounts.add(data);
		}
		
		textField.setText("Adding rent successfull: " + String.valueOf(rc.add_rent_with_del(3, true, date, date, next_day, ids_amounts)));
		textField_1.setText("Adding rent without delivery successfull: " + String.valueOf(rc.add_rent_without_del(5, date, next_day, ids_amounts)));
		textField_2.setText(String.valueOf(rc.rent_exists(5)));
//		textField_1.setText(String.valueOf(cc.add_customer("Bob", "535062444", "some@new.com", "nowhere", "DK9220", "Aalborg", "No real preferences", 1)));
//		textField_2.setText(cc.find_customer_name(3));
	}

}
