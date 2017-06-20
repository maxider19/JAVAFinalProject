import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class initialGUI extends JFrame{
	
	//adding the buttons of add, subtract, multiply and divide
	JButton jbtRegisteredUser = new JButton("Registered User");
	JButton jbtNewUser = new JButton("New User");
	JButton jbtDBAadministrator = new JButton("DBA Administrtor");
	JButton jbtClose = new JButton("Close");
	Client conn;
	
	public initialGUI(final Client conn){
		
		this.conn=conn;
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(jbtRegisteredUser);
		p1.add(jbtNewUser);
		p1.add(jbtDBAadministrator);
		add(p1, BorderLayout.CENTER);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(jbtClose);
		add(p2, BorderLayout.SOUTH);
		
		jbtRegisteredUser.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		conn.userGui.setVisible(false);
	    		conn.customerLogin = new customerLoginGUI(conn); 
	    		conn.customerLogin.setTitle("Login");
			    //frame.setSize(450, 150);
	    		conn.customerLogin.setLocationRelativeTo(null); // Center the frame
	    		conn.customerLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		conn.customerLogin.pack();
	    		conn.customerLogin.setVisible(true);
	    	}
	    });
		
		jbtNewUser.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		//NewUserGUI.NewUserGUI();
	    		//NewUserGUI.NewUserGUI();
	    		conn.userGui.setVisible(false);
	    		conn.registerGui=new NewUserGUI(conn);
	    		//NewUserGUI frame = new NewUserGUI(); 
	    		conn.registerGui.dispose();
	    		conn.registerGui.pack();
	    		conn.registerGui.setTitle("Buy movie tickets");
	    	    //frame.setSize(450, 150);
	    		conn.registerGui.setLocationRelativeTo(null); // Center the frame
	    		conn.registerGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		conn.registerGui.setVisible(true);
	    	}
	    });
		
		jbtDBAadministrator.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		conn.userGui.setVisible(false);
	    		conn.dbaGui = new DbaGUI(conn); 
	    		conn.dbaGui.setTitle("Register");
	    	    //frame.setSize(450, 150);
	    		conn.dbaGui.setLocationRelativeTo(null); // Center the frame
	    		conn.dbaGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		conn.dbaGui.pack();
	    		conn.dbaGui.setVisible(true);
	    	}
	    });
		
		jbtClose.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		try {
					conn.getConn().close();
					System.exit(0);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Exit");
					//e1.printStackTrace();
				}
	    	}
	    });
		
	}
	
//	public static void main(String[] args) {
//	    initialGUI frame = new initialGUI(); 
//	    
//	    frame.setTitle("Buy movie tickets");
//	    //frame.setSize(450, 150);
//	    frame.pack();
//	    frame.setLocationRelativeTo(null); // Center the frame
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	    frame.setVisible(true);
//	}
	
	

}
