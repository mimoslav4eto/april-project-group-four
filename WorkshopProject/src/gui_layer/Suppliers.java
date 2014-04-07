package gui_layer;
import ctr_layer.SupplyLineCtr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
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

public class Suppliers extends SuperGUI {
	private String[] column_names={"id", "Name", "City", "Country", "Phone Number"};
	private Object[][] filling;
	protected SupplyLineCtr suppliers;
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JPanel panel_4;
	private JPanel panel_5;
	private JPanel panel_6;
	private JTextField tf_zip;
	private JTextField tf_city;
	private JTextField tf_id;
	private JTextField tf_name;
	private JTextField tf_phone;
	private JTextField tf_email;
	private JTextField tf_address;
	private JTextField tf_country;
	private JTextField tf_cvr;
	private JTextArea txt_desc;
	private Dimension dim;
	private JButton btn_ok;

	/**
	 * Create the frame.
	 */
	public Suppliers() 
	{
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
		setTitle("Suppliers");
		setSize(730, 513);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		suppliers=new SupplyLineCtr();
		dim= Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		make_manager();
		Object[][] temp=suppliers.get_all_suppliers();
		refill_table(temp);
		btn_delete.setVisible(false);
		
	}
	private void refill_table(Object[][] temp)
	{
		if (temp!=null)
		{
			filling=new Object[temp.length][5];
			for (int i=0;i<temp.length;i++)
			{
				filling[i][0]=temp[i][0];
				filling[i][1]=temp[i][1];
				filling[i][2]=temp[i][4];
				filling[i][3]=temp[i][5];
				filling[i][4]=temp[i][7];
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
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Manage Supplier", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{187, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0 , 80, 48, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
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
		tf_id.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(tf_id);
		tf_id.setColumns(13);
		tf_id.setForeground(Color.LIGHT_GRAY);
		tf_id.setEnabled(false);
		
		tf_name = new JTextField();
		tf_name.setHorizontalAlignment(SwingConstants.CENTER);
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
		tf_name.setColumns(13);
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
		tf_phone.setHorizontalAlignment(SwingConstants.CENTER);
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
		tf_phone.setColumns(13);
		tf_phone.setText("Phone Number");
		tf_phone.setForeground(Color.LIGHT_GRAY);

		tf_email = new JTextField();
		tf_email.setHorizontalAlignment(SwingConstants.CENTER);
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
		tf_email.setColumns(13);
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
		tf_address.setHorizontalAlignment(SwingConstants.CENTER);
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
		tf_address.setColumns(13);
		tf_address.setText("Address");
		tf_address.setForeground(Color.LIGHT_GRAY);

		tf_zip = new JTextField();
		tf_zip.setHorizontalAlignment(SwingConstants.CENTER);
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
		tf_zip.setColumns(13);
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
		tf_city.setHorizontalAlignment(SwingConstants.CENTER);
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
		tf_city.setColumns(13);
		tf_city.setText("City");
		tf_city.setForeground(Color.LIGHT_GRAY);
		
		tf_country = new JTextField();
		tf_country.setHorizontalAlignment(SwingConstants.CENTER);
		tf_country.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_country.getText().equals("Country"))||(tf_country.getText().equals("Not valid")))
				{
					tf_country.setText("");
					tf_country.setForeground(Color.black);
					tf_country.setBackground(Color.white);
					
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_country.getText().isEmpty())
				{
					tf_country.setText("Country");
					tf_country.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		panel_4.add(tf_country);
		tf_country.setColumns(13);
		tf_country.setText("Country");
		tf_country.setForeground(Color.LIGHT_GRAY);
		
		
		
		panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 4;
		panel.add(panel_5, gbc_panel_5);
		
		
		tf_cvr = new JTextField();
		tf_cvr.setHorizontalAlignment(SwingConstants.CENTER);
		tf_cvr.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((tf_cvr.getText().equals("CVR Number"))||(tf_cvr.getText().equals("Not valid")))
				{
					tf_cvr.setText("");
					tf_cvr.setForeground(Color.black);
					tf_cvr.setBackground(Color.white);
					
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (tf_cvr.getText().isEmpty())
				{
					tf_cvr.setText("CVR Number");
					tf_cvr.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		tf_cvr.setText("CVR Number");
		tf_cvr.setForeground(Color.LIGHT_GRAY);
		tf_cvr.setColumns(27);
		panel_5.add(tf_cvr);
		
		
		txt_desc = new JTextArea();
		txt_desc.setLineWrap(true);
		txt_desc.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent arg0)
			{
				if ((txt_desc.getText().equals("Description"))||(txt_desc.getText().equals("Not valid")))
				{
					txt_desc.setText("");
					txt_desc.setForeground(Color.black);
					txt_desc.setBackground(Color.white);
				}
			}
			public void focusLost(FocusEvent arg1)
			{
				if (txt_desc.getText().isEmpty())
				{
					txt_desc.setText("Description");
					txt_desc.setForeground(Color.LIGHT_GRAY);
				}
			}
		});
		txt_desc.setText("Description");
		txt_desc.setForeground(Color.LIGHT_GRAY);
		
		JScrollPane scroll_text = new JScrollPane(txt_desc);
		scroll_text.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll_text.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints gbc_scroll_text = new GridBagConstraints();
		gbc_scroll_text.fill = GridBagConstraints.BOTH;
		gbc_scroll_text.insets = new Insets(0, 0, 5, 0);
		gbc_scroll_text.gridx = 0;
		gbc_scroll_text.gridy = 5;
		panel.add(scroll_text, gbc_scroll_text);
		
		panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(0, 0, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 6;
		panel.add(panel_6, gbc_panel_6);
		
		btn_ok = new JButton("OK");
		panel_6.add(btn_ok);
	
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
		
		tf_country.setText("Country");
		tf_country.setForeground(Color.LIGHT_GRAY);
		tf_country.setEnabled(true);
		tf_country.setBackground(Color.white);
		
		tf_address.setText("Address");
		tf_address.setForeground(Color.LIGHT_GRAY);
		tf_address.setEnabled(true);
		tf_address.setBackground(Color.white);
		
		tf_cvr.setText("CVR Number");
		tf_cvr.setForeground(Color.LIGHT_GRAY);
		tf_cvr.setEnabled(true);
		tf_cvr.setBackground(Color.white);
		
		txt_desc.setText("Description");
		txt_desc.setForeground(Color.LIGHT_GRAY);
		txt_desc.setEnabled(true);
		txt_desc.setBackground(Color.white);
	}
	protected void search()
	{
		Object[][] temp=null;
		String info = field_search.getText();
		if (is_number(info))
		{
			if (suppliers.supplier_exists(Integer.parseInt(info)))
			{
			temp = new Object[1][5];
			temp[0] = suppliers.get_supplier_by_id(Integer.parseInt(info));
			}
		}
		else
		{
			if (suppliers.get_suppliers_by_name(info)!=null)
			{
				
				temp = suppliers.get_suppliers_by_name(info);
			}
		}
		refill_table(temp);
	}
	
	protected void clear()
	{
		clear_fields();
		filling=suppliers.get_all_suppliers();
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
				System.out.println("Error while parsing id for editing: " + e);
			}
			String name = tf_name.getText();
			String address = tf_address.getText();
			String city = tf_city.getText();
			String country = tf_country.getText();
			String cvr = tf_cvr.getText();
			String description = txt_desc.getText();
			String email = tf_email.getText();
			String phone_nr = tf_phone.getText();
			String zipcode = tf_zip.getText();
			if (id==-1)
			{
				suppliers.add_supplier(name, phone_nr, email, address, zipcode, city, cvr, description, country);
				JOptionPane.showMessageDialog(this, "Successully created a Supplier.", "Success", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
			else
			{
				suppliers.update_supplier(id, name, phone_nr, email, address, zipcode, city, cvr, description, country);
				JOptionPane.showMessageDialog(this, "Successully edited a Supplier.", "Success", JOptionPane.INFORMATION_MESSAGE);
				clear();
			}
			clear();
			
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
		if ((tf_zip.getText().equals("Zip Code"))||(tf_zip.getText().equals("")))
		{
			tf_zip.setBackground(Color.red);
			tf_zip.setText("Not valid");
			is_ready=false;
		}
		if ((tf_city.getText().equals("City"))||(tf_city.getText().equals("")))
		{
			tf_city.setBackground(Color.red);
			tf_city.setText("Not valid");
			is_ready=false;
		}
		if ((tf_country.getText().equals("Country"))||(tf_country.getText().equals("")))
		{
			tf_country.setBackground(Color.red);
			tf_country.setText("Not valid");
			is_ready=false;
		}
		if ((tf_cvr.getText().equals("CVR Number"))||(tf_cvr.getText().equals("")))
		{
			tf_cvr.setBackground(Color.red);
			tf_cvr.setText("Not valid");
			is_ready=false;
		}
		if ((txt_desc.getText().equals("Description"))||(txt_desc.getText().equals("")))
		{
			txt_desc.setBackground(Color.red);
			txt_desc.setText("Not valid");
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
			fill_fields(suppliers.get_supplier_by_id(t_id),false);
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
				Object[] temp=suppliers.get_supplier_by_id(t_id);
				fill_fields(temp,true);
			}
		}
	}
	
	private void fill_fields(Object[] fill,boolean is_edit)
	{
		tf_id.setText(String.valueOf(fill[0]));
		tf_name.setText(String.valueOf(fill[1]));
		tf_phone.setText(String.valueOf(fill[7]));
		tf_email.setText(String.valueOf(fill[6]));
		tf_address.setText(String.valueOf(fill[2]));
		tf_zip.setText(String.valueOf(fill[3]));
		tf_city.setText(String.valueOf(fill[4]));
		tf_country.setText(String.valueOf(fill[5]));
		tf_cvr.setText(String.valueOf(fill[8]));
		txt_desc.setText(String.valueOf(fill[9]));
		if (!is_edit)
		{
			tf_name.setEnabled(false);
			tf_phone.setEnabled(false);
			tf_email.setEnabled(false);
			tf_address.setEnabled(false);
			tf_zip.setEnabled(false);
			tf_city.setEnabled(false);
			tf_country.setEnabled(false);
			tf_cvr.setEnabled(false);
			txt_desc.setEnabled(false);
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
			tf_country.setEnabled(true);
			tf_country.setForeground(Color.black);
			tf_cvr.setEnabled(true);
			tf_cvr.setForeground(Color.black);
			txt_desc.setEnabled(true);
			txt_desc.setForeground(Color.black);
		}
	}
}