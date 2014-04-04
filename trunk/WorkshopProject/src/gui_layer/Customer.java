package gui_layer;
import ctr_layer.CustomerCtr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import java.awt.FlowLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.DropMode;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Customer extends SuperGUI {
	private Object[] column_names={"id", "Name", "Phone", "Zip code"};
	private Object[][] filling;
	private CustomerCtr customers;
	private JPanel panel;
	private JPanel panel_5;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextField tf_zip;
	private JTextField tf_city;
	private JTextField tf_id;
	private JTextField tf_name;
	private JTextField tf_phone;
	private JTextField tf_email;
	private JTextField tf_address;
	private JTextField tf_type;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JTextArea textArea;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					Customer frame = new Customer();
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
	public Customer() {
		setTitle("Customers");
		setBounds(100, 100, 730, 513);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		customers=new CustomerCtr();
		
		//filling=customers.get_all_customers();
		filling=new Object[0][0];
		fill_table(filling,column_names);
		make_manager();
	}
	
	private void make_manager()
	{
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Customer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 80, 48, 0};
		gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tf_id = new JTextField("ID");
		panel_1.add(tf_id);
		tf_id.setForeground(Color.LIGHT_GRAY);
		
		tf_name = new JTextField();
		panel_1.add(tf_name);
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
		tf_name.setColumns(10);
		tf_name.setText("Name");
		tf_name.setForeground(Color.LIGHT_GRAY);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		
		tf_phone = new JTextField();
		tf_phone.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_phone.getText().equals("Phone Number"))&&(tf_phone.getForeground()!=Color.black))
				{
					tf_phone.setText("");
					tf_phone.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_phone.getText().isEmpty())
				{
					tf_phone.setText("Phone Number");
					tf_phone.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_2.add(tf_phone);
		tf_phone.setColumns(10);
		tf_phone.setText("Phone Number");
		tf_phone.setForeground(Color.LIGHT_GRAY);
		
		tf_email = new JTextField();
		panel_2.add(tf_email);
		tf_email.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_email.getText().equals("E-mail"))&&(tf_email.getForeground()!=Color.black))
				{
					tf_email.setText("");
					tf_email.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_email.getText().isEmpty())
				{
					tf_email.setText("E-mail");
					tf_email.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_email.setColumns(10);
		tf_email.setText("E-mail");
		tf_email.setForeground(Color.LIGHT_GRAY);
		
		panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		
		tf_address = new JTextField();
		tf_address.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_address.getText().equals("Address"))&&(tf_address.getForeground()!=Color.black))
				{
					tf_address.setText("");
					tf_address.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_address.getText().isEmpty())
				{
					tf_address.setText("Address");
					tf_address.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_3.add(tf_address);
		tf_address.setColumns(10);
		tf_address.setText("Address");
		tf_address.setForeground(Color.LIGHT_GRAY);
		
		tf_zip = new JTextField();
		panel_3.add(tf_zip);
		tf_zip.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_zip.getText().equals("Zip Code"))&&(tf_zip.getForeground()!=Color.black))
				{
					tf_zip.setText("");
					tf_zip.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_zip.getText().isEmpty())
				{
					tf_zip.setText("Zip Code");
					tf_zip.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_zip.setColumns(10);
		tf_zip.setText("Zip Code");
		tf_zip.setForeground(Color.LIGHT_GRAY);
		
		panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		
		tf_city = new JTextField();
		tf_city.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_city.getText().equals("City"))&&(tf_city.getForeground()!=Color.black))
				{
					tf_city.setText("");
					tf_city.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_city.getText().isEmpty())
				{
					tf_city.setText("City");
					tf_city.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_4.add(tf_city);
		tf_city.setColumns(10);
		tf_city.setText("City");
		tf_city.setForeground(Color.LIGHT_GRAY);
		
		tf_type = new JTextField();
		tf_type.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_type.getText().equals("Customer Type"))&&(tf_type.getForeground()!=Color.black))
				{
					tf_type.setText("");
					tf_type.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_type.getText().isEmpty())
				{
					tf_type.setText("Customer Type");
					tf_type.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_4.add(tf_type);
		tf_type.setColumns(10);
		tf_type.setText("Customer Type");
		tf_type.setForeground(Color.LIGHT_GRAY);
		
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((textArea.getText().equals("Preferences"))&&(textArea.getForeground()!=Color.black))
				{
					textArea.setText("");
					textArea.setForeground(Color.black);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (textArea.getText().isEmpty())
				{
					textArea.setText("Preferences");
					textArea.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		textArea.setText("Preferences");
		textArea.setForeground(Color.LIGHT_GRAY);
		
		JScrollPane scroll_text = new JScrollPane(textArea);
		scroll_text.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_text.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scroll_text = new GridBagConstraints();
		gbc_scroll_text.fill = GridBagConstraints.BOTH;
		gbc_scroll_text.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_text.gridx = 0;
		gbc_scroll_text.gridy = 4;
		panel.add(scroll_text, gbc_scroll_text);
		
		
		
		panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 5;
		panel.add(panel_5, gbc_panel_5);
		
		btnNewButton = new JButton("OK");
		panel_5.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Cancel");
		panel_5.add(btnNewButton_1);
	}
	
	protected void search()
	{
		Object[][] fill = new Object[1][5];
		String info = field_search.getText();
		if (is_number(info))
		{
			if (customers.existing_customer(info))
			{
			fill[0] = customers.find_customer(info);
			}
			else 
			{
				fill[0]=null;
			}
		}
		else
		{
			if (customers.find_customer_by_name(info)!=null)
			{
				fill[0] = customers.find_customer_by_name(info);
			}
			else 
			{
				fill[0]=null;
			}
		}
		fill_table(fill, column_names);
	}
	
	protected void clear()
	{
		fill_table(filling,column_names);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	
	protected void create()
	{
		CustomerManager p = new CustomerManager(-1, 0);
		p.setVisible(true);
	}
	
	protected void view()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				CustomerManager p = new CustomerManager((int) table.getModel().getValueAt(row_index, 0), 2);
				p.setVisible(true);
			}
		}
	}
	
	protected void edit()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				CustomerManager p = new CustomerManager((int) table.getModel().getValueAt(row_index, 0), 1);
				p.setVisible(true);
			}
		}
	}
	
	protected void delete()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(this,
						"Are you sure you want to delete this customer?",
						"Delete customer", JOptionPane.YES_NO_OPTION))
				{
					customers.delete_customer((String) table.getModel().getValueAt(row_index, 0));
					//filling=customers.get_all_customers();
					filling=new Object[0][0];
					fill_table(filling,column_names);
				}
			}
		}
	}

}
