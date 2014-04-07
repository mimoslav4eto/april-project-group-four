package gui_layer;

import java.awt.*;

import model_layer.*;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

public class MainMenu extends JFrame
{
	private final double VERSION = 1.0;
	private JPanel contentPane;
	private JDesktopPane desktopPane;
	private JMenuBar menuBar;
	private int[] position;
	
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
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainMenu frame = new MainMenu();
					frame.setVisible(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	public MainMenu()
	{
		super("Western Style® Group 4");
		setBounds(0,0, 1300, 700);
		position = new int[2];
		position[0] = 50;
		position[1] = 50;
		
		
		
		contentPane = new JPanel(false);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		desktopPane = new JDesktopPane();
		contentPane.add(desktopPane, BorderLayout.CENTER);
		desktopPane.setOpaque(false);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		make_sales();
		make_rents();
		make_products();
		make_customers();
		make_program();

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
	}
	
	private void make_program()
	{
		JMenu mnProgram = new JMenu("Program");
		menuBar.add(mnProgram);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				show_about();
			}
		});
		mnProgram.add(mntmAbout);
		
		JMenuItem mntmLogOut = new JMenuItem("Exit");
		mntmLogOut.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null,
						"Are you sure you want to Exit out of the System?",
						"Exit", JOptionPane.YES_NO_OPTION))
				{
				System.exit(0);
				}
			}
		});
		mnProgram.add(mntmLogOut);
	}
	
	private void make_customers()
	{
		JMenu customers = new JMenu("Customers");
		menuBar.add(customers);

		JMenuItem mntmNewMenuItem = new JMenuItem("Manage Customers");
		mntmNewMenuItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Customer frame = new Customer();
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
		});
		customers.add(mntmNewMenuItem);

		JMenuItem mntmManageGroups = new JMenuItem("Manage Customer Types");
		mntmManageGroups.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CustomerType frame = new CustomerType();
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
		});
		customers.add(mntmManageGroups);
	}
	
	private void make_products()
	{
		JMenu mnProducts = new JMenu("Products");
		menuBar.add(mnProducts);
		
		JMenuItem mntmManageProducts = new JMenuItem("Manage products");
		mntmManageProducts.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Products frame = new Products();
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
		});
		mnProducts.add(mntmManageProducts);
	}
	
	private void make_sales()
	{
		JMenu mnSales = new JMenu("Order");
		menuBar.add(mnSales);
		
		JMenuItem mntmManageSales = new JMenuItem("Manage orders");
		mntmManageSales.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				OrderGUI frame = new OrderGUI();
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
		});
		
		JMenuItem mntmNewSale = new JMenuItem("New order");
		mnSales.add(mntmNewSale);
		mntmNewSale.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				NewOrderGUI frame = new NewOrderGUI(false,0);
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
		});
		mnSales.add(mntmManageSales);
		
	}
	
	private void make_rents()
	{
		JMenu mnNewMenu = new JMenu("Rent");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewLease = new JMenuItem("New Rent");
		mntmNewLease.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				NewRentGUI frame = new NewRentGUI(false,0);
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);

			}

		});
		mnNewMenu.add(mntmNewLease);
		
		JMenuItem mntmManageLeases = new JMenuItem("Manage Rents");
		mntmManageLeases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RentGUI frame = new RentGUI();
				set_location();
				frame.setLocation(position[0]+3, position[1]+3);
				desktopPane.add(frame);
				frame.setVisible(true);
			}
		});
		mnNewMenu.add(mntmManageLeases);
	}
	
	private void set_location()
	{
		JInternalFrame last_frame = desktopPane.getSelectedFrame();
		if(last_frame != null)
		{
			Point p = last_frame.getLocationOnScreen();
			position[0] = (int) p.getX();
			position[1] = (int) p.getY();
		}
	}
	
	
	private void show_about()
	{
		JOptionPane.showMessageDialog(this, "Western Style Beta for Western Style A/S by Group4 v" + VERSION,
				"About", JOptionPane.INFORMATION_MESSAGE);
	}
}