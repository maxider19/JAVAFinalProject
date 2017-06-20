
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewUserGUI extends JFrame {
	
	private JLabel usernameLabel = new JLabel("Username:");
	private JLabel passwordLabel = new JLabel("Enter new Password:");
	private JLabel fullNameLabel = new JLabel("Full Name:");;
	private JLabel customerAddressLabel = new JLabel("Enter the address:");
	private JLabel emailLabel = new JLabel("Enter the Email:");
	private JLabel phoneNumberLabel = new JLabel("Enter the phone number:");
	
	static JTextField usernameField = new JTextField();
	static JTextField passwordField = new JTextField();
	private JTextField fullNameField = new JTextField();
	private JTextField customerAddressField = new JTextField();
	private JTextField emailField = new JTextField();
	private JTextField phoneNumberField = new JTextField();
	
	private JButton addUser = new JButton("Register");
	private JButton cancel = new JButton("Cancel");
	Client conn;
	
	public NewUserGUI(final Client conn){
		
		this.conn=conn;
		setTitle("Customer Register Gui");
		JPanel top = new JPanel(new GridLayout(2,2));
	    top.add(usernameLabel);
	    top.add(usernameField);
	    top.add(passwordLabel);
	    top.add(passwordField);
	    add(top, BorderLayout.NORTH);
	    
		JPanel center = new JPanel(new GridLayout(2,2));
		center.add(fullNameLabel);
		center.add(fullNameField);
		center.add(customerAddressLabel);
		center.add(customerAddressField);
		add(center, BorderLayout.CENTER);
		
	    JPanel bottom = new JPanel(new GridLayout(3,3));
	    bottom.add(emailLabel);
	    bottom.add(emailField);
	    bottom.add(phoneNumberLabel);
	    bottom.add(phoneNumberField);
	    bottom.add(addUser);
	    bottom.add(cancel); 
	    add(bottom, BorderLayout.SOUTH);
	    
	    cancel.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		conn.userGui.setVisible(true);
	    		conn.registerGui.setVisible(false);
	    	}
	    });
	    
		addUser.addActionListener( new java.awt.event.ActionListener() {
			
			public void actionPerformed(ActionEvent e){
				try{
					if(registerCustomer(e)){
						JOptionPane.showMessageDialog(null,"Customer Successfully Registered");
						conn.registerGui.setVisible(false);
						conn.customerLogin.setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(null,"Registration Unsuccessful");
						usernameField.setText("");
						passwordField.setText("");
						fullNameField.setText("");
						customerAddressField.setText("");
						emailField.setText("");
						phoneNumberField.setText("");
					}
//					RegisteredUserGUI frame = new RegisteredUserGUI(); 
//				    frame.setTitle("Login");
//				    //frame.setSize(450, 150);
//				    frame.setLocationRelativeTo(null); // Center the frame
//				    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				    frame.pack();
//				    frame.setVisible(true);
					
				}catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		});
	
	}
	
	boolean registerCustomer(ActionEvent e) throws IOException, ClassNotFoundException{
		boolean result=false;		
		String username=usernameField.getText();
		String password=passwordField.getText();
		String fullname=fullNameField.getText();
		String emailAddress=emailField.getText();
		String mailAddress=customerAddressField.getText();
		String phonenumber=phoneNumberField.getText();
		int phonenumberInteger=0;
		
		String userType="customer";
		int registerOp=0;
		if(validateFields(username,password,fullname,emailAddress,mailAddress,phonenumber)){
				try{
					phonenumberInteger=Integer.parseInt(phonenumber);
					conn.registerCustomer(username,password,fullname,emailAddress,mailAddress,phonenumberInteger,userType,registerOp);
					if(conn.message.getStatus().equals("Y")){
						result= true;
					}
					else
						result=false;
				}catch(Exception ex){
					JOptionPane.showMessageDialog(null,"Phone number entered is invalid");
					phoneNumberField.setText("");
					result=false;
				}
		}	
		return result;
	}
	
	boolean validateFields(String username,String password,String fullname,String emailAddress,String mailAddress,String phonenumber){
		 boolean validate=true;
		 if(username.equals("")||password.equals("")||fullname.equals("")||emailAddress.equals("")){
			 JOptionPane.showMessageDialog(null,"No Filed Can be Blank");
			 validate=false;
		 }
		 else if(!validEmail(emailAddress)){
			JOptionPane.showMessageDialog(null, "Please enter valid email","Input error",JOptionPane.ERROR_MESSAGE);
			emailField.requestFocus();
			validate = false;
		 }
		 else if(!validPhoneNumber(phonenumber)){
				JOptionPane.showMessageDialog(null, "Please enter valid Phone number","Input error",JOptionPane.ERROR_MESSAGE);
				phoneNumberField.requestFocus();
				validate = false;
		}		 
		return validate;
	}
	
	boolean validEmail(String email) {
		
		boolean validEmail = true;
		Pattern emailPattern = Pattern.compile("^[!#$%&'*+-/=?^_`{|}~a-zA-Z0-9._&]+@[a-zA-Z0-9.]+$");
		Matcher matcher = emailPattern.matcher(email);
		if(matcher.matches()){
			validEmail = true;
		} else{
			validEmail = false;
		}
		return validEmail;
	}
	
	protected boolean validPhoneNumber(String phone) {
		
		boolean validPhoneNumber = true;
		Pattern phoneNumberPattern = Pattern.compile("\\d{3}\\d{3}\\d{4}");
		Matcher matcher = phoneNumberPattern.matcher(phone);
		if(matcher.matches()){
		} else{
			validPhoneNumber = false;
		}
		return validPhoneNumber;
	}
    /*
	public static void main(String[] args) {
		
	    NewUserGUI frame = new NewUserGUI(); 
	    
	    frame.setTitle("Register");
	    //frame.setSize(450, 150);
	    frame.setLocationRelativeTo(null); // Center the frame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
	*/

}
