import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class LoginGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JLabel customerLabel = new JLabel("welcomeLabel");
	String welcomeLabel = "";
	private JButton closeButton = new JButton("Close");
	private JButton bookTicket = new JButton("Buy Ticket");
	private JButton cancelTicket = new JButton("Cancel Ticket");
	private JButton viewTicket = new JButton("View Booked Ticket");
	
	Client conn;
	
	public LoginGUI(final Client conn){
		
		if(conn.message!=null){
			String customerName=conn.message.getFullName();
			welcomeLabel="Welcome "+customerName;
		}
		
		this.conn=conn;
		setTitle("Buy Ticket Gui");
		
		viewTicket.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				//System.exit(0);
				conn.viewTicketGui = new viewTicketGUI(conn);
				conn.viewTicketGui.pack();
				conn.viewTicketGui.setVisible( true );
				conn.viewTicketGui.setLocationRelativeTo(null); // Center the frame
				conn.viewTicketGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				conn.customerGUI.setVisible(false);
			}
		});
		bookTicket.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				conn.bookTicketGui = new BookTicketGUI(conn);
				conn.bookTicketGui.pack();
				conn.bookTicketGui.setVisible( true );
				conn.bookTicketGui.setLocationRelativeTo(null); // Center the frame
				conn.bookTicketGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				conn.customerGUI.setVisible(false);
			
			}	
		});
		cancelTicket.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				//viewRegisterGuiCustomer();
				conn.cancelTicketGui = new cancelTicketGUI(conn);
				conn.cancelTicketGui.pack();
				conn.cancelTicketGui.setVisible( true );
				conn.cancelTicketGui.setLocationRelativeTo(null); // Center the frame
				conn.cancelTicketGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				conn.customerGUI.setVisible(false);
				
			}
		});
		closeButton.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				//viewRegisterGuiCustomer();
				conn.customerLogin.setVisible(true);
				conn.customerGUI.setVisible(false);
				
			}
		});
      
	}
	
	public LoginGUI(){
		
		JPanel center = new JPanel(new GridLayout(1,3));
		center.add(bookTicket);
		center.add(cancelTicket);
		center.add(viewTicket);
		add(center, BorderLayout.CENTER);
		
	}
	/*
	public static void main(String[] args){
		LoginGUI frame = new LoginGUI(); 
	    frame.setTitle("Login");
	    //frame.setSize(450, 150);
	    frame.setLocationRelativeTo(null); // Center the frame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
	*/

}
