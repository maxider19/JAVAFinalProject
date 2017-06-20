import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class viewTicketGUI extends JFrame {
	
	Client conn;
	
	private JLabel ticketInfo;
	private JLabel enterTicketDateLabel = new JLabel("Ticket Date:");
	
	private JTextField dateTextField = new JTextField("");
	
	private JTextArea ticketInformationArea;
	private JScrollPane jpInfoArea;
	private JTextArea infoArea;
	
	private JButton getDetailsButton = new JButton("Get Ticket Details");
	private JButton closeButton = new JButton("Close");
	
	public static String USER;
	public static String FULL_NAME;
	
	static JFrame j;
	
	public viewTicketGUI(final Client conn){
		this.conn=conn;
		USER=conn.message.getUsername();
		FULL_NAME=conn.message.getFullName();
		
		initGUI();
		doTheLayout();
		setTitle("View Tickets Gui");
		
		getDetailsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				
				String dateText = dateTextField.getText().trim();
				int opType=5;
				
				if(!validateDate(dateText)){
					
					JOptionPane.showMessageDialog(null, "Please enter valid date format like in MM/dd/yyyy format", "Validation Error",JOptionPane.ERROR_MESSAGE);
					dateTextField.requestFocus();
					
				} else{
					try {
						conn.viewTicket(dateText,USER,opType);
						if(conn.message.getOperationType() == 5){
							if(conn.message.getStatus().equals("Y")){
								for(int i=0;i<conn.message.getarrayMovieReservation().size();i++){
									infoArea.append(conn.message.getarrayMovieReservation().get(i).toString1() + "\n");
								}
								
							} else{
								JOptionPane.showMessageDialog(null, "No reservations were found","Failure",JOptionPane.ERROR_MESSAGE);
								infoArea.setText("");
							}
						}
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				conn.customerGUI.setVisible(true);
				conn.viewTicketGui.setVisible(false);
			}
		});
	}
	
	private void initGUI(){
		ticketInfo = new JLabel("Ticket Information:");
		jpInfoArea = new JScrollPane(infoArea);
		infoArea = new JTextArea(5,25);
		infoArea.setEditable(false);
	    infoArea.setLineWrap(true);
	    infoArea.setWrapStyleWord(true);
		infoArea.append("Ticket info...");
	}
	
	private void doTheLayout(){
		
		JPanel top = new JPanel(new FlowLayout());
		top.add(enterTicketDateLabel);
		top.add(dateTextField);
		add(top, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new FlowLayout());
		center.add(ticketInfo);
		center.add(infoArea);
		center.add(jpInfoArea);
		add(center, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new FlowLayout());
		bottom.add(getDetailsButton);
		bottom.add(closeButton);
		add(bottom, BorderLayout.SOUTH);
	}
	/*
	public static void main(String[] args){
		LoginGUI frame = new LoginGUI(); 
	    frame.setTitle("Payment");
	    //frame.setSize(450, 150);
	    frame.setLocationRelativeTo(null); // Center the frame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
	*/
	private boolean validateDate(String dateString) {
		boolean validate = true;

		try {
			DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
			formatter.setLenient(false);
			Date date = formatter.parse(dateString);
		} catch (Exception e) { 
			validate = false;
		}
		return validate;
	}
	

}
