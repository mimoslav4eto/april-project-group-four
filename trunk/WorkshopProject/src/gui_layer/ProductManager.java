package gui_layer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductManager extends JFrame {

	private JPanel contentPane;
	private JTextField tf_name;
	private JTextField tf_price;
	private JTextField tf_retail;
	private JTextField tf_amount;
	private JTextField tf_min;
	private JTextField tf_id;
	private JButton btn_ok;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					CustomerManager frame = new CustomerManager(-1, 0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProductManager(int id, int state) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		switch (state)
		{
			case 0: setTitle("New Product");
					break;
			case 1: setTitle("Edit Product");
					break;
			case 2: setTitle("View Product");
					break; 
		}
		setBounds(100, 100, 357, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		make_fields();
		make_buttons();
	}
	
	private void make_fields()
	{
		tf_id = new JTextField();
		tf_id.setBounds(132, 15, 61, 20);
		contentPane.add(tf_id);
		tf_id.setColumns(10);
		tf_id.setVisible(false);
		
		tf_name = new JTextField();
		tf_name.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_name.getText().equals("Name"))&&(tf_name.getForeground()!=Color.black))
				{
					tf_name.setText("");
					tf_name.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_name.getText().isEmpty())
				{
					tf_name.setText("Name");
					tf_name.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_name.setBounds(12, 15, 110, 20);
		contentPane.add(tf_name);
		tf_name.setColumns(10);
		tf_name.setText("Name");
		tf_name.setForeground(Color.LIGHT_GRAY);
		
		tf_retail = new JTextField();
		tf_retail.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_retail.getText().equals("Phone Number"))&&(tf_retail.getForeground()!=Color.black))
				{
					tf_retail.setText("");
					tf_retail.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_retail.getText().isEmpty())
				{
					tf_retail.setText("Phone Number");
					tf_retail.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_retail.setBounds(12, 77, 110, 20);
		contentPane.add(tf_retail);
		tf_retail.setColumns(10);
		tf_retail.setText("Retail Price");
		tf_retail.setForeground(Color.LIGHT_GRAY);
		
		tf_price = new JTextField();
		tf_price.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_price.getText().equals("E-mail"))&&(tf_price.getForeground()!=Color.black))
				{
					tf_price.setText("");
					tf_price.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_price.getText().isEmpty())
				{
					tf_price.setText("E-mail");
					tf_price.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_price.setBounds(12, 46, 110, 20);
		contentPane.add(tf_price);
		tf_price.setColumns(10);
		tf_price.setText("Price");
		tf_price.setForeground(Color.LIGHT_GRAY);
		
		tf_amount = new JTextField();
		tf_amount.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_amount.getText().equals("Address"))&&(tf_amount.getForeground()!=Color.black))
				{
					tf_amount.setText("");
					tf_amount.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_amount.getText().isEmpty())
				{
					tf_amount.setText("Address");
					tf_amount.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_amount.setBounds(12, 108, 110, 20);
		contentPane.add(tf_amount);
		tf_amount.setColumns(10);
		tf_amount.setText("Amount");
		tf_amount.setForeground(Color.LIGHT_GRAY);
		
		tf_min = new JTextField();
		tf_min.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_min.getText().equals("Zip Code"))&&(tf_min.getForeground()!=Color.black))
				{
					tf_min.setText("");
					tf_min.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_min.getText().isEmpty())
				{
					tf_min.setText("Zip Code");
					tf_min.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_min.setBounds(12, 139, 110, 20);
		contentPane.add(tf_min);
		tf_min.setColumns(10);
		tf_min.setText("Minimum Amount");
		tf_min.setForeground(Color.LIGHT_GRAY);
	}
	private void make_buttons()
	{
		contentPane.setLayout(null);
		btn_ok = new JButton("OK");
		btn_ok.setBounds(99, 170, 47, 23);
		contentPane.add(btn_ok); 
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				dispose();
			}
		});
		btn_cancel.setBounds(176, 170, 65, 23);
		contentPane.add(btn_cancel);
	}
}
