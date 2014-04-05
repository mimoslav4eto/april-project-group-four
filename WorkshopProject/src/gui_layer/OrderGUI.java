package gui_layer;

import java.awt.EventQueue;

import javax.swing.UIManager;

public class OrderGUI extends SuperGUI
{

	public OrderGUI()
	{
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					OrderGUI frame = new OrderGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
