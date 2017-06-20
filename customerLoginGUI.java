import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class customerLoginGUI extends JFrame {
	
	Client conn;
	
	private static final long serialVersionUID = 1L;
	
	public JLabel usernameLabel;
	public JLabel passwordLabel;
	
	public JTextField usernameField;
	public JTextField passwordField;
	
	public JButton loginButton;
	public JButton closeButton;
	public JButton registerButton;

	public customerLoginGUI(final Client conn) {
		// TODO Auto-generated constructor stub
		
		this.conn=conn;
		setTitle("Customer Login Gui");
		initGUI();
		doTheLayout();
		closeButton.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				conn.userGui = new initialGUI(conn);
				conn.userGui.pack();
				conn.userGui.setLocationRelativeTo(null);
				conn.userGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				conn.userGui.setVisible(true);
			}
		});
		
		loginButton.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				String username=usernameField.getText().trim();
				String password=passwordField.getText().trim();
				String userType="customer";
				if(username.equals(null) || password.equals(null) || password.equals("") || username.equals("")){
					JOptionPane.showMessageDialog(null, "Invalid Username/Password");
				}
					
				else{
					try {
						conn.customerLogin(username, password,userType);
						if((conn.message.getStatus().equals("Y")) && conn.message.getUserType().equals("customer")){
							JOptionPane.showMessageDialog(null,"Welcome!Login Successful");
							conn.bookTicketGui = new BookTicketGUI(conn);
							conn.bookTicketGui.pack();
							conn.bookTicketGui.setLocationRelativeTo(null);
							conn.bookTicketGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							conn.bookTicketGui.setVisible(true);
							conn.customerLogin.setVisible(false);
						}
						else{
							JOptionPane.showMessageDialog(null,"Login unSuccessful");
							usernameField.setText("");
							passwordField.setText("");
						}
					} catch (ClassNotFoundException | IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,"Login Not Successful");
					}	
				}
				
			}
		});
		
		registerButton.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				conn.customerLogin.setVisible(false);
				conn.registerGui = new NewUserGUI(conn); 
				conn.registerGui.setTitle("Register");
			    //frame.setSize(450, 150);
				conn.registerGui.setLocationRelativeTo(null); // Center the frame
				conn.registerGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				conn.registerGui.pack();
				conn.registerGui.setVisible(true);
				
			}
		});
	}
		
	private void initGUI(){
		
		usernameLabel= new JLabel("Username:");
		usernameField=new JTextField("");
		passwordLabel= new JLabel("Password:");
		passwordField=new JTextField("");
		loginButton=new JButton("Login");
		registerButton=new JButton("Register");
		closeButton=new JButton("Close");
		
	}	
		
	public void doTheLayout(){
		
		JPanel center = new JPanel(new GridLayout(2,2));
		center.add(usernameLabel);
		center.add(usernameField);
		center.add(passwordLabel);
		center.add(passwordField);
		add(center, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new GridLayout(1,3));
		bottom.add(loginButton);
		bottom.add(registerButton);
		bottom.add(closeButton);
		add(bottom, BorderLayout.SOUTH);
	}	

}
