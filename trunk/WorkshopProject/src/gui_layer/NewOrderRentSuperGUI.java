package gui_layer;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.awt.Dimension;

import javax.swing.ListSelectionModel;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JRadioButton;

import ctr_layer.CustomerCtr;

abstract public class NewOrderRentSuperGUI extends JFrame {

	protected JPanel contentPane;
	protected CustomerCtr cust_ctr;
	private JPanel panel_2;
	private JPanel top_panel;
	protected JPanel button_panel;
	private JPanel panel_1; 
	private JScrollPane scrollPane;
	protected JTable table;
	private String[] column_names = {};
	private Object[][] filling;
	protected boolean creating;
	protected JButton btn_add;
	private JButton btn_cancel;
	private JPanel panel;
	//private JButton btn_create;
	private JLabel title_lbl;
	protected int id;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					SuperGUI frame = new SuperGUI();
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
	public NewOrderRentSuperGUI(boolean i_creating, int i_id) 
	{
		creating = i_creating;
		id = i_id;
		cust_ctr = new CustomerCtr();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 570, 450);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		top_panel = new JPanel();
		contentPane.add(top_panel, BorderLayout.NORTH);
		top_panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		top_panel.add(panel_1, BorderLayout.WEST);
		
		
		button_panel = new JPanel();
		contentPane.add(button_panel, BorderLayout.SOUTH);
		button_panel.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		button_panel.add(panel_2, BorderLayout.EAST);
		
		panel = new JPanel();
		button_panel.add(panel, BorderLayout.WEST);
		
		
		make_buttons();
		make_table();
	}
	
	private void make_buttons()
	{
		
		title_lbl = new JLabel();
		panel_1.add(title_lbl);
		if(creating)
		{
			title_lbl.setText("Create new Order");

			JButton btn_delete = new JButton("Delete");
			btn_delete.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg1)
				{
					delete();
				}
			});
			
			btn_add = new JButton("Add product");
			btn_add.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg4)
				{
					add();
				}
			});
			panel_2.add(btn_add);
			
			JButton btn_edit = new JButton("Edit");
			btn_edit.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg2)
				{
					edit();
				}
			});
			panel_2.add(btn_edit);
			panel_2.add(btn_delete);
			
			JButton btn_create = new JButton("Create");
			btn_create.addMouseListener(new MouseAdapter()
			{
				public void mouseClicked(MouseEvent arg2)
				{
					create();
				}
			});
			panel.add(btn_create);
			filling = new Object[0][0];
			
		}
		else
		{
			title_lbl.setText("Order details");
		}
		
		
		btn_cancel = new JButton("Cancel");
		panel_2.add(btn_cancel);
		
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
		table.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					edit();
				}
			}
		});
		DefaultTableCellRenderer right_renderer = new DefaultTableCellRenderer();
		right_renderer.setHorizontalAlignment(JLabel.LEFT);
		table.setDefaultRenderer(String.class, right_renderer);
		
		scrollPane = new JScrollPane(table);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setBorder(new EmptyBorder(5, 10, 5, 10));
		
	}
	
	protected void fill_table(Object[][] fill, String[] columns)
	{
		filling = fill;
		column_names=columns;
		table.setModel(new MyTableModel(filling, column_names));
	}
	
	abstract protected void create();
	
	abstract protected void edit();
	
	abstract protected void add();
	
	abstract protected void delete();
	
	
	
	protected boolean is_number(String temp)
	{
		try
		{
			int i = Integer.parseInt(temp);
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}

}


