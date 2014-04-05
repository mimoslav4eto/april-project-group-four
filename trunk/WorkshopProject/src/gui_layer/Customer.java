package gui_layer;
import ctr_layer.CustomerCtr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

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
	protected CustomerCtr customers;
	private JPanel panel;
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
	private JTextArea txt_pref;
	private boolean is_opened;
	private Dimension dim;
	private JPanel panel_5;
	private JButton btn_ok;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
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
		
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
					view();
			}
		});
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					edit();
				}
				else if (e.getClickCount() == 1)
				{
					view();
				}
			}
		});
		table.addKeyListener(new KeyAdapter()
		{
			public void keyTyped(KeyEvent e)
			{
				if (e.getKeyChar() == '\u007F')
				{
					delete();
				}
			}
		});
		setTitle("Customers");
		setSize(730, 513);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		is_opened=false;
		customers=new CustomerCtr();
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		make_manager();
		Object[][] temp=customers.get_non_deleted_customers();
		refill_table(temp);
		
	}
	private void refill_table(Object[][] temp)
	{
		if (temp!=null)
		{
			filling=new Object[temp.length][4];
			for (int i=0;i<temp.length;i++)
			{
				filling[i][0]=temp[i][0];
				filling[i][1]=temp[i][1];
				filling[i][2]=temp[i][6];
				filling[i][3]=temp[i][3];
			}
			fill_table(filling,column_names);
		}
		else 
		{
			fill_table(new Object[0][0],column_names);
		}
	}
	
	private void make_manager()
	{
		panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Customer", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 80, 48, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		tf_id = new JTextField("ID");
		panel_1.add(tf_id);
		tf_id.setColumns(10);
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setEnabled(false);
		
		tf_name = new JTextField();
		panel_1.add(tf_name);
		tf_name.addFocusListener(new FocusAdapter()
		{
			public void focusGained(FocusEvent arg0)
			{
				if (((tf_name.getText().equals("Name"))&&(tf_name.getForeground()!=Color.black))||(tf_name.getText().equals("Not Valid")))
				{
					tf_name.setText("");
					tf_name.setForeground(Color.black);
					tf_name.setBackground(Color.white);
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
		gbc_panel_2.insets = new Insets(0, 0, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		
		tf_phone = new JTextField();
		tf_phone.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if (((tf_phone.getText().equals("Phone Number"))&&(tf_phone.getForeground()!=Color.black))||(tf_phone.getText().equals("Not Valid")))
				{
					tf_phone.setText("");
					tf_phone.setForeground(Color.black);
					tf_phone.setBackground(Color.white);
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
				if ((tf_email.getText().equals("E-mail"))||(tf_email.getText().equals("Not valid")))
				{
					tf_email.setText("");
					tf_email.setForeground(Color.black);
					tf_email.setBackground(Color.white);
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
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		
		tf_address = new JTextField();
		tf_address.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_address.getText().equals("Address"))||(tf_address.getText().equals("Not valid")))
				{
					tf_address.setText("");
					tf_address.setForeground(Color.black);
					tf_address.setBackground(Color.white);
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
				if ((tf_zip.getText().equals("Zip Code"))||(tf_zip.getText().equals("Not valid")))
				{
					tf_zip.setText("");
					tf_zip.setForeground(Color.black);
					tf_zip.setBackground(Color.white);
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
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		
		tf_city = new JTextField();
		tf_city.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_city.getText().equals("City"))||(tf_city.getText().equals("Not valid")))
				{
					tf_city.setText("");
					tf_city.setForeground(Color.black);
					tf_city.setBackground(Color.white);
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
				if ((tf_type.getText().equals("Customer Type"))||(tf_type.getText().equals("Not valid")))
				{
					tf_type.setText("");
					tf_type.setForeground(Color.black);
					tf_type.setBackground(Color.white);
					
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
		tf_type.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				if(!is_opened)
				{
					is_opened=true;
					make_table_types();
				}
			}
		});
		panel_4.add(tf_type);
		tf_type.setColumns(10);
		tf_type.setText("Customer Type");
		tf_type.setForeground(Color.LIGHT_GRAY);
		
		
		txt_pref = new JTextArea();
		txt_pref.setLineWrap(true);
		txt_pref.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((txt_pref.getText().equals("Preferences"))||(txt_pref.getText().equals("Not valid")))
				{
					txt_pref.setText("");
					txt_pref.setForeground(Color.black);
					txt_pref.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (txt_pref.getText().isEmpty())
				{
					txt_pref.setText("Preferences");
					txt_pref.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		txt_pref.setText("Preferences");
		txt_pref.setForeground(Color.LIGHT_GRAY);
		
		JScrollPane scroll_text = new JScrollPane(txt_pref);
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
		
		btn_ok = new JButton("OK");
		panel_5.add(btn_ok);
		btn_ok.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				if (tf_name.isEnabled())
				{
					create_edit();
				}
			}
		});
	}
	private void make_table_types()
	{
		Table p=new Table(this);
		p.setVisible(true);
	}
	private void clear_fields()
	{
		tf_id.setText("ID");
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setBackground(Color.white);
		
		tf_name.setText("Name");
		tf_name.setForeground(Color.LIGHT_GRAY);
		tf_name.setEnabled(true);
		tf_name.setBackground(Color.white);
		
		tf_phone.setText("Phone Number");
		tf_phone.setForeground(Color.LIGHT_GRAY);
		tf_phone.setEnabled(true);
		tf_phone.setBackground(Color.white);
		
		tf_email.setText("E-mail");
		tf_email.setForeground(Color.LIGHT_GRAY);
		tf_email.setEnabled(true);
		tf_email.setBackground(Color.white);
		
		tf_zip.setText("Zip Code");
		tf_zip.setForeground(Color.LIGHT_GRAY);
		tf_zip.setEnabled(true);
		tf_zip.setBackground(Color.white);
		
		tf_city.setText("City");
		tf_city.setForeground(Color.LIGHT_GRAY);
		tf_city.setEnabled(true);
		tf_city.setBackground(Color.white);
		
		tf_type.setText("Customer Type");
		tf_type.setForeground(Color.LIGHT_GRAY);
		tf_type.setEnabled(true);
		tf_type.setBackground(Color.white);
		
		tf_address.setText("Address");
		tf_address.setForeground(Color.LIGHT_GRAY);
		tf_address.setEnabled(true);
		tf_address.setBackground(Color.white);
		
		txt_pref.setText("Preferences");
		txt_pref.setForeground(Color.LIGHT_GRAY);
		txt_pref.setEnabled(true);
		txt_pref.setBackground(Color.white);
	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (customers.customer_exists(Integer.parseInt(info)))
			{
			temp = new Object[1][4];
			temp[0] = customers.get_customer_by_id(Integer.parseInt(info));
			}
		}
		else
		{
			if (customers.get_customer_by_name(info)!=null)
			{
				
				temp = customers.get_customer_by_name(info);
			}
		}
		refill_table(temp);
	}
	
	protected void clear()
	{
		clear_fields();
		filling=customers.get_non_deleted_customers();
		refill_table(filling);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	protected void create()
	{
		clear_fields();
	}
	
	protected void create_edit()
	{
		if ((is_ready())&&(tf_name.isEnabled()))
		{
			int id=-1;
			try
			{
				id = Integer.parseInt(tf_id.getText());
			}
			catch (Exception e)
			{
			}
			String name = tf_name.getText();
			String phone_nr = tf_phone.getText();
			String email = tf_email.getText();
			String address = tf_address.getText();
			String zipcode = tf_zip.getText();
			String city = tf_city.getText();
			int type_id = Integer.parseInt(tf_type.getText());
			String preferences = txt_pref.getText();
			if (id==-1)
			{
				customers.add_customer(name, phone_nr, email, address, zipcode, city, preferences, type_id);
				JOptionPane.showMessageDialog(this, "Successully created a customer.", "Success", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
			else
			{
				customers.update_customer(id, name, phone_nr, email, address, zipcode, city, preferences, type_id);
				JOptionPane.showMessageDialog(this, "Successully edited a customer.", "Success", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
		}
	}
	private boolean is_ready()
	{
		boolean is_ready=true;
		if ((tf_name.getText().equals("Name"))||(tf_name.getText().equals("")))
		{
			tf_name.setBackground(Color.red);
			tf_name.setText("Not Valid");
			is_ready=false;
		}
		if ((tf_phone.getText().equals("Phone Number"))||(tf_phone.getText().equals(""))||(!tf_phone.getText().matches("\\+?[0-9]+(:?-[0-9]+)*")))
		{
			tf_phone.setBackground(Color.red);
			tf_phone.setText("Not Valid");
			is_ready=false;
		}
		if ((tf_email.getText().equals("E-mail"))||(tf_email.getText().equals("")))
		{
			tf_email.setBackground(Color.red);
			tf_email.setText("Not valid");
			is_ready=false;
		}
		if ((tf_address.getText().equals("Address"))||(tf_address.getText().equals("")))
		{
			tf_address.setBackground(Color.red);
			tf_address.setText("Not valid");
			is_ready=false;
		}
		if ((tf_zip.getText().equals("Zip Code"))||(tf_address.getText().equals("")))
		{
			tf_zip.setBackground(Color.red);
			tf_zip.setText("Not valid");
			is_ready=false;
		}
		if ((tf_city.getText().equals("City"))||(tf_address.getText().equals("")))
		{
			tf_city.setBackground(Color.red);
			tf_city.setText("Not valid");
			is_ready=false;
		}
		if ((tf_type.getText().equals("Customer Type"))||(tf_address.getText().equals(""))||(!is_number(tf_type.getText()))||(!customers.customer_type_exists(Integer.parseInt(tf_type.getText()))))
		{
			tf_type.setBackground(Color.red);
			tf_type.setText("Not valid");
			is_ready=false;
		}
		
		if ((txt_pref.getText().equals("Preferences"))||(tf_address.getText().equals("")))
		{
			txt_pref.setBackground(Color.red);
			txt_pref.setText("Not valid");
			is_ready=false;
		}
		
		return is_ready;
	}
	
	protected void view()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			int t_id=(int)table.getModel().getValueAt(row_index, 0);
			fill_fields(customers.get_customer_by_id(t_id),false);
		}
	}
	
	protected void edit()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			
			if( table.getModel().getValueAt(row_index, 0) != null)
			{
				int t_id=(int) table.getModel().getValueAt(row_index, 0);
				Object[] temp=customers.get_customer_by_id(t_id);
				fill_fields(temp,true);
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
					customers.delete_customer((int)table.getModel().getValueAt(row_index, 0));
					filling=customers.get_non_deleted_customers();
					fill_table(filling,column_names);
				}
			}
		}
	}
	private void fill_fields(Object[] fill,boolean is_edit)
	{
		tf_id.setText(""+fill[0]);
		tf_name.setText(""+fill[1]);
		tf_phone.setText(""+fill[6]);
		tf_email.setText(""+fill[5]);
		tf_address.setText(""+fill[2]);
		tf_zip.setText(""+fill[3]);
		tf_city.setText(""+fill[4]);
		tf_type.setText(""+fill[8]);
		txt_pref.setText(""+fill[7]);
		if (!is_edit)
		{
			tf_name.setEnabled(false);
			tf_phone.setEnabled(false);
			tf_email.setEnabled(false);
			tf_address.setEnabled(false);
			tf_zip.setEnabled(false);
			tf_city.setEnabled(false);
			tf_type.setEnabled(false);
			txt_pref.setEnabled(false);
		}
		else
		{
			tf_id.setForeground(Color.black);
			tf_name.setEnabled(true);
			tf_name.setForeground(Color.black);
			tf_phone.setEnabled(true);
			tf_phone.setForeground(Color.black);
			tf_email.setEnabled(true);
			tf_email.setForeground(Color.black);
			tf_address.setEnabled(true);
			tf_address.setForeground(Color.black);
			tf_zip.setEnabled(true);
			tf_zip.setForeground(Color.black);
			tf_city.setEnabled(true);
			tf_city.setForeground(Color.black);
			tf_type.setEnabled(true);
			tf_type.setForeground(Color.black);
			txt_pref.setEnabled(true);
			txt_pref.setForeground(Color.black);
		}
	}

class Table extends SuperGUI {
	
	private Object[][] filling;
	private Object[] column_names={"id","Discount Price", "Discount %", "Free Shipping"};
    private Point initialClick;
	private Customer instance;
	
	public Table(Customer instance) {
		this.instance=instance;
		setTitle("Choose Customer Type");
		setSize(450, 300);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		filling= customers.get_all_customer_types();
		fill_table(filling, column_names);
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				choose();
			}
		});
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	is_opened=false;
		    }
		});
	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (customers.customer_type_exists(Integer.parseInt(info)))
			{
			temp = new Object[1][4];
			temp[0] = customers.get_customer_type(Integer.parseInt(info));
			}
		}
		fill_table(temp,column_names);
	}
	protected void clear()
	{
		filling=customers.get_all_customer_types();
		fill_table(filling, column_names);
		field_search.setText("Name/ID");
		field_search.setForeground(Color.LIGHT_GRAY);
	}
	
	protected void choose()
	{
		int row_index = table.getSelectedRow();
		if (row_index != -1)
		{
			int t_id=(int)table.getModel().getValueAt(row_index, 0);
			tf_type.setText(""+t_id);
	}
	}
}

}
