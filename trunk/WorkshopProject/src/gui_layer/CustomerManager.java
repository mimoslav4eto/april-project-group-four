package gui_layer;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomerManager extends JFrame {

	private JPanel contentPane;
	private JTextField tf_name;
	private JTextField tf_email;
	private JTextField tf_phone;
	private JTextField tf_address;
	private JTextField tf_zip;
	private JTextField tf_city;
	private JTextField tf_id;
	private JButton btn_ok;
	private JTextArea textArea;
	private JTable table;
	private Object[][] filling;
	private Object[] column_names={"id","Discount Price", "Discount %", "Free Shipping"};
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
	public CustomerManager(int id, int state) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		switch (state)
		{
			case 0: setTitle("New Customer");
					break;
			case 1: setTitle("Edit Customer");
					break;
			case 2: setTitle("View Customer");
					break; 
		}
		setBounds(100, 100, 357, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		filling=new Object[0][0];
		make_table();
		fill_table();
		make_fields();
		make_buttons();
	}
	private void make_table()
	{
			table = new JTable()
			{
				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
			};
			
			table.setFont(new Font("Tahoma", Font.PLAIN, 11));
			table.setFillsViewportHeight(true);
			table.setModel(new DefaultTableModel(filling, column_names));
			table.setAutoCreateRowSorter(true);
			table.setRowSelectionAllowed(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.getTableHeader().setReorderingAllowed(false);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			DefaultTableCellRenderer right_renderer = new DefaultTableCellRenderer();
			right_renderer.setHorizontalAlignment(JLabel.LEFT);
			table.setDefaultRenderer(String.class, right_renderer);
			
			JScrollPane scrollPane = new JScrollPane(table);
			contentPane.add(scrollPane);
			scrollPane.setBounds(0, 92, 351, 156);
	}
	private void fill_table()
	{
		//filling=customers.get_all_customers();
		filling=new Object[0][0];
		table.setModel(new DefaultTableModel(filling, column_names));
	}
	private void make_fields()
	{
		tf_id = new JTextField();
		tf_id.setBounds(240, 65, 61, 20);
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
		tf_name.setBounds(2, 15, 110, 20);
		contentPane.add(tf_name);
		tf_name.setColumns(10);
		tf_name.setText("Name");
		tf_name.setForeground(Color.LIGHT_GRAY);
		
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
		tf_phone.setBounds(120, 15, 110, 20);
		contentPane.add(tf_phone);
		tf_phone.setColumns(10);
		tf_phone.setText("Phone Number");
		tf_phone.setForeground(Color.LIGHT_GRAY);
		
		tf_email = new JTextField();
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
		tf_email.setBounds(240, 15, 110, 20);
		contentPane.add(tf_email);
		tf_email.setColumns(10);
		tf_email.setText("E-mail");
		tf_email.setForeground(Color.LIGHT_GRAY);
		
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
		tf_address.setBounds(2, 41, 110, 20);
		contentPane.add(tf_address);
		tf_address.setColumns(10);
		tf_address.setText("Address");
		tf_address.setForeground(Color.LIGHT_GRAY);
		
		tf_zip = new JTextField();
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
		tf_zip.setBounds(120, 41, 110, 20);
		contentPane.add(tf_zip);
		tf_zip.setColumns(10);
		tf_zip.setText("Zip Code");
		tf_zip.setForeground(Color.LIGHT_GRAY);
		
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
		tf_city.setBounds(240, 40, 110, 20);
		contentPane.add(tf_city);
		tf_city.setColumns(10);
		tf_city.setText("City");
		tf_city.setForeground(Color.LIGHT_GRAY);
		
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
		scroll_text.setBounds(0, 259, 351, 86);
        scroll_text.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll_text.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scroll_text);
	}
	private void make_buttons()
	{
		contentPane.setLayout(null);
		btn_ok = new JButton("OK");
		btn_ok.setBounds(101, 356, 47, 23);
		contentPane.add(btn_ok); 
		
		JButton btn_cancel = new JButton("Cancel");
		btn_cancel.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent arg0)
			{
				dispose();
			}
		});
		btn_cancel.setBounds(178, 356, 65, 23);
		contentPane.add(btn_cancel);
		
		JLabel lblChooseCustomerType = new JLabel("Choose Customer type");
		lblChooseCustomerType.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblChooseCustomerType.setBounds(2, 69, 207, 23);
		contentPane.add(lblChooseCustomerType);
	}
}
