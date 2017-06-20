import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class cancelTicketGUI extends JFrame {
	
	Client conn;
	
	private static final long serialVersionUID = 1L;
	GridBagConstraints gbc = new GridBagConstraints();
	private JLabel confirmationIDLabel = new JLabel("Please enter Confirmation Number");
	private JLabel cancellationInfoLabel = new JLabel("Cancellation Information");
	
	private JTextField confirmationTextField = new JTextField("");
	private JTextArea cancellationInformationArea = new JTextArea(5,25);
	
	private JButton getDetailsButton = new JButton("Get Details");
	private JButton cancelTicketButton = new JButton("Cancel Ticket");
	
	public static String USER;
	public static String FULL_NAME;
	
	public cancelTicketGUI(final Client conn) {
		this.conn=conn;
		//cancellationInfoLabel.setFont(new Font("Serif", Font.BOLD, 20));
		USER=conn.message.getUsername();
		FULL_NAME=conn.message.getFullName();
		cancellationInformationArea.setEditable(false);
		doTheLayout();
		
		getDetailsButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				boolean validateForCancelTicket = validateForCancelTicket();
				if(validateForCancelTicket){		
					String referenceID = confirmationTextField.getText().trim();
					int opType=6;
					try {
						conn.getTicket(referenceID,USER,opType);
						if(conn.message.getStatus().equals("Yes") && conn.message.getOperationType()==6){
							if(conn.message.getmovieReservation().getUserName().equals(USER)){
								cancellationInformationArea.setText(conn.message.getmovieReservation().toString1());
							} else{
								JOptionPane.showMessageDialog(null, "Please enter Valid Cancellation Reference ID","Validation Error",JOptionPane.ERROR_MESSAGE);
								cancellationInformationArea.setText("");
							}
						} else{
						
							JOptionPane.showMessageDialog(null, "Please enter Valid Cancellation Reference Number","Validation Error",JOptionPane.ERROR_MESSAGE);
							cancellationInformationArea.setText("");
						}
					
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Wrong info");
					}
				}
			}
			
			public boolean validateForCancelTicket(){
				
				boolean validate = true;
				String referenceNumber = confirmationTextField.getText().trim();
				String textArea = cancellationInformationArea.getText().trim();
				
				if(referenceNumber.equals("")){
					JOptionPane.showMessageDialog(null, "Please enter Reference Number");
					validate = false;
					confirmationTextField.requestFocus();
				} 
				return validate;
			}
		});
		
		cancelTicketButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				boolean validateForCancelTicket = validateForCancelTicket();
				if(validateForCancelTicket){
					String referenceNumber = confirmationTextField.getText().trim();
					int opType=7;
					try {
						conn.CancelTicket(referenceNumber, USER, opType);
						if(conn.message.getStatus() != null){
							if(conn.message.getStatus().equals("Y")){
								String hours = conn.message.getmovieReservation().getShowTime();
								if(hours.equals("10:00 AM")){
									hours ="10:00:00";
									
								}else if(hours.equals("2:00 PM")){
									hours ="14:00:00";
									
								}else if(hours.equals("5:00 PM")){
									hours ="17:00:00";
								}else{
									hours ="21:00:00";
								}
								
								String dateTest =conn.message.getmovieReservation().getMovieDate();
								String[] split = dateTest.split("/");
				
								try{
									DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
									Date date = dateFormat.parse(String.valueOf(Integer.parseInt(split[2]) +1) + "-" + split[0] + "-" + split[1] +" " + hours);
									long time = date.getTime();
									Timestamp obj = new Timestamp(time);
									Timestamp timestamp = new Timestamp(System.currentTimeMillis());
									long milliseconds = obj.getTime() - timestamp.getTime();
								    int seconds = (int) milliseconds / 1000;
								    int hours1 = seconds / 3600;
								    
								    if(hours1>=1){	
										JOptionPane.showMessageDialog(null, "Cancellation Successful- The Reservation has been Successfully deleted from the database.","Success",JOptionPane.INFORMATION_MESSAGE);
										cancellationInformationArea.setText("");
										confirmationTextField.setText("");
								    }else{
								    	JOptionPane.showMessageDialog(null, "Cancellation Failure - Tickets can be cancelled only 1 hour before the Movie ShowTime ","Validation Error",JOptionPane.ERROR_MESSAGE);
										cancellationInformationArea.setText("");	
								    }
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}else{
								JOptionPane.showMessageDialog(null, "Deletion Failure","Validation Error",JOptionPane.ERROR_MESSAGE);
							    cancellationInformationArea.setText("");
							}
						}else{
							JOptionPane.showMessageDialog(null, "Deletion Failure","Validation Error",JOptionPane.ERROR_MESSAGE);
							cancellationInformationArea.setText("");
						}
					
					}catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			private boolean validateForCancelTicket() {
				
				boolean validate = true;
				String referenceNumber = confirmationTextField.getText().trim();
				String textArea = cancellationInformationArea.getText().trim();
		
				if(referenceNumber.equals("")){
					JOptionPane.showMessageDialog(null, "Please enter Reference Number","Validation Error",JOptionPane.ERROR_MESSAGE);
					validate = false;
					confirmationTextField.requestFocus();
				} else if(textArea.equals("")){
					JOptionPane.showMessageDialog(null, "Please enter the Details and then Cancel the Ticket","Validation Error",JOptionPane.ERROR_MESSAGE);
					validate = false;
					getDetailsButton.requestFocus();
				}
				return validate;
			}
		});
	}
	
	private void doTheLayout(){
		
		JPanel top = new JPanel(new FlowLayout());
		top.add(confirmationIDLabel);
		top.add(confirmationTextField);
		add(top, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new FlowLayout());
		center.add(cancellationInfoLabel);
		center.add(cancellationInformationArea);
		add(center, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new FlowLayout());
		bottom.add(getDetailsButton);
		bottom.add(cancelTicketButton);
		add(bottom, BorderLayout.SOUTH);
		
	}
}
