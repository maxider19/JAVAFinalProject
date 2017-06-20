import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class BookTicketGUI extends JFrame {
	Client conn;
	private static final long serialVersionUID = 1L;
	
	private JLabel movieLabel = new JLabel("Select Movie:");
	private JLabel movTime = new JLabel("Time:");
	private JLabel noOfSeats = new JLabel("Select number of seats:");
	private JLabel dateLabel = new JLabel("Date To Book Ticket");
	private JLabel creditCardLabel = new JLabel("Payment");
	private JLabel creditCardNumberLabel = new JLabel("Credit Card Number(16 Digit)");
	private JLabel creditCardCVVNumberLabel = new JLabel("CVV Number");
	private JLabel creditCardExpiryDateLabel = new JLabel("Expiration Date (MM/yy)");
	private JLabel amountLabel = new JLabel("Amount");
	private JLabel screenLabel = new JLabel("Screen");
	private JLabel movieRatingLabel = new JLabel("Rating");
	
	private JTextField showDateTextField = new JTextField(10);
	private JTextField movieRatingTextField = new JTextField(5);
	private JTextField numberOfAvailableSeatsTextBox = new JTextField(10);
	private JTextField amountTextField = new JTextField(10);
	private JTextField screenTextField = new JTextField(10);
	private JTextField creditCardNumberTextField = new JTextField(15);
	private JTextField creditCardCVVNumberTextField = new JTextField(5);
	private JTextField creditCardExpirationDateTextField = new JTextField(5);
	
	private JComboBox numberofTicketsComboBox = new JComboBox();
	private JComboBox selectMovieComboBox = new JComboBox();
	
	public static String USER;
	public static String FULL_NAME;
	
	private JComboBox movieComboBox = new JComboBox();
	private JComboBox numberOfSeats;
	
	private JButton buyTicket = new JButton("Buy");
	private JButton closeButton = new JButton("Close");
	private JButton getMoviesButton = new JButton("Find Movies");
	
	private JRadioButton firstShowRadioButton = new JRadioButton("10:00 AM");
	private JRadioButton secondShowRadioButton = new JRadioButton("2:00 PM");
	private JRadioButton thirdShowRadioButton = new JRadioButton("5:00 PM");
	private JRadioButton fourthShowRadioButton = new JRadioButton("9:00 PM");
	
	private ArrayList<showTime> showList;
	
	private ButtonGroup group = new ButtonGroup();
	
	
	public BookTicketGUI(final Client conn){
		this.conn=conn;
		USER=conn.message.getUsername();
		FULL_NAME=conn.message.getFullName();
		selectMovieComboBox.addItem("Select a Movie");
		numberOfAvailableSeatsTextBox.setEnabled(false);
		movieRatingTextField.setEnabled(false);
		amountTextField.setEnabled(false);
		screenTextField.setEnabled(false);
		numberofTicketsComboBox.addItem("Select number of Tickets ");
		for(int i=1;i<=10;i++){
			numberofTicketsComboBox.addItem(i + " Ticket");
		}
		disableRadioButtons();
		
		group.add(firstShowRadioButton);
		group.add(secondShowRadioButton);
		group.add(thirdShowRadioButton);
		group.add(fourthShowRadioButton);
		
		getMoviesButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String showDate = showDateTextField.getText().trim();
				int opType=2;
				disableRadioButtons();
				if(!validateDate(showDate)){
					JOptionPane.showMessageDialog(null, "Enter valid date (MM/dd/yyyy)");
					showDateTextField.requestFocus();
				} else{
					try {
						conn.getShowDates(showDate,opType);
						if(conn.message.getStatus().equals("Y")){
							int movieCount = selectMovieComboBox.getItemCount();
							//Loop through all the products in the Product Combo Box and remove the First item
							for(int y=0;y<movieCount;y++){
								try{
									// Removing the value in the First Inde
									selectMovieComboBox.removeItemAt(0);
								}catch(Exception ex){
									ex.printStackTrace();
								}
							}
							
							selectMovieComboBox.addItem("Select a Movie");
							for(int i=0;i<conn.message.getShowList().size();i++){
								selectMovieComboBox.addItem(conn.message.getShowList().get(i).getmovieName());
							}
							showList = conn.message.getShowList();	
						}
						else{
							JOptionPane.showMessageDialog(null, "No Movie Shows Available on These Days.Check Show Details");
						}
						
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
				
			}
		});	
		
		selectMovieComboBox.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//customerSelectMovieLabel.setTe
				disableRadioButtons();
				if(selectMovieComboBox.getSelectedIndex()!=0){
					if(selectMovieComboBox.getSelectedItem()!=null){
						String moviename = selectMovieComboBox.getSelectedItem().toString();
						for(int i=0;i<showList.size();i++){
							if(moviename.equals(showList.get(i).getmovieName())){
								
								if(showList.get(i).getfirstShow().equals("Yes")){
									firstShowRadioButton.setEnabled(true);
								}
								if(showList.get(i).getsecondShow().equals("Yes")){
									secondShowRadioButton.setEnabled(true);
								}
								if(showList.get(i).getthirdShow().equals("Yes")){
									thirdShowRadioButton.setEnabled(true);
								}
								if(showList.get(i).getfourthShow().equals("Yes")){
									fourthShowRadioButton.setEnabled(true);
								}	
							}
						}
						int opType=3;
						try {
							conn.getMovieRating(moviename,opType);
						} catch (ClassNotFoundException | IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(conn.message.getStatus().equals("Yes")&&conn.message.operationType==3){
							if(conn.message.getStatus().equals("Yes")){
								
								movieRatingTextField.setText(conn.message.getmovieRating());
							}
						}
					}
									
				} else{
					disableRadioButtons();
				}
			}
		});
		
		firstShowRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				
				String moviename = selectMovieComboBox.getSelectedItem().toString();
				for(int i=0;i<showList.size();i++){
					
					if(moviename.equals(showList.get(i).getmovieName())){
						numberOfAvailableSeatsTextBox.setText(showList.get(i).getfirstShowSeats());
						screenTextField.setText(showList.get(i).getscreen());
						amountTextField.setText(showList.get(i).getpay());						
					}
				}
			}
		});	
		
		secondShowRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String moviename = selectMovieComboBox.getSelectedItem().toString();
				
				for(int i=0;i<showList.size();i++){
					if(moviename.equals(showList.get(i).getmovieName())){			
						numberOfAvailableSeatsTextBox.setText(showList.get(i).getsecondShowSeats());
						screenTextField.setText(showList.get(i).getscreen());
						amountTextField.setText(showList.get(i).getpay());			
					}
				}
			}
		});
		
		thirdShowRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String moviename = selectMovieComboBox.getSelectedItem().toString();
				
				for(int i=0;i<showList.size();i++){
					if(moviename.equals(showList.get(i).getmovieName())){			
						numberOfAvailableSeatsTextBox.setText(showList.get(i).getthirdShowSeats());
						screenTextField.setText(showList.get(i).getscreen());
						amountTextField.setText(showList.get(i).getpay());			
					}
				}
			}
		});
		
		fourthShowRadioButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				String moviename = selectMovieComboBox.getSelectedItem().toString();
				
				for(int i=0;i<showList.size();i++){
					if(moviename.equals(showList.get(i).getmovieName())){			
						numberOfAvailableSeatsTextBox.setText(showList.get(i).getfourthShowSeats());
						screenTextField.setText(showList.get(i).getscreen());
						amountTextField.setText(showList.get(i).getpay());			
					}
				}
			}
		});
		
		closeButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				conn.bookTicketGui.setVisible(false);
				conn.customerGUI.setVisible(true);
			}
		});
		
		JPanel top = new JPanel(new GridLayout(1,1));
		top.add(movieLabel);
		top.add(movieComboBox);
		top.add(dateLabel);
		top.add(showDateTextField);
		top.add(getMoviesButton);
		top.add(movieRatingLabel);
		top.add(movieRatingTextField);
		top.add(movTime);
		top.add(firstShowRadioButton);
		top.add(secondShowRadioButton);
		top.add(thirdShowRadioButton);
		top.add(fourthShowRadioButton);
		add(top, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(3,3));
		top.add(screenLabel);
		top.add(screenTextField);
		center.add(screenLabel);
		center.add(screenTextField);
		center.add(noOfSeats);
		center.add(numberOfSeats);
		add(center, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new GridLayout(1,2));
		bottom.add(creditCardLabel);
		bottom.add(creditCardNumberLabel);
		bottom.add(creditCardNumberTextField);
		bottom.add(creditCardCVVNumberLabel);
		bottom.add(creditCardCVVNumberTextField);
		bottom.add(creditCardExpiryDateLabel);
		bottom.add(creditCardExpirationDateTextField);
		bottom.add(amountLabel);
		bottom.add(buyTicket);
		bottom.add(closeButton);
		add(bottom, BorderLayout.SOUTH);
		
		buyTicket.addActionListener( new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					boolean validateForBookTickets = validateForBookTickets();
					
					if(validateForBookTickets){
						
						String movieDate = showDateTextField.getText().trim();
						String username = USER;
						String fullName = FULL_NAME;
						
						String creditCardNumber = creditCardNumberTextField.getText().trim();
						String movieName = selectMovieComboBox.getSelectedItem().toString();
						
						String showTime;
					
						if(firstShowRadioButton.isSelected() == true){
							showTime="10:00 AM";
						} else if(secondShowRadioButton.isSelected() == true){
							showTime="2:00 PM";
						} else if(thirdShowRadioButton.isSelected() == true){
							showTime="5:00 PM";
						} else{
							showTime="9:00 PM";
						}
						String hours = "";
						if(showTime.equals("10:00 AM")){
							hours ="10:00:00";
						} else if(showTime.equals("2:00 PM")){
							hours ="14:00:00";
						} else if(showTime.equals("5:00 PM")){
							hours ="17:00:00";
						} else{
							hours ="21:00:00";
						}
						
						String dateTest =movieDate;
						String[] split = dateTest.split("/");
					
						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
						java.util.Date date = dateFormat.parse(String.valueOf(Integer.parseInt(split[2]) +1) + "-" + split[0] + "-" + split[1] +" " + hours);
						long time = date.getTime();
						Timestamp obj1 = new Timestamp(time);
						Timestamp timestamp = new Timestamp(System.currentTimeMillis());
						long milliseconds = obj1.getTime() - timestamp.getTime();
					    int seconds = (int) milliseconds / 1000;
					 
					    int hours1 = seconds / 3600;
					     
						String roomNumber = screenTextField.getText().trim();
						String numberOfTickets = numberofTicketsComboBox.getSelectedItem().toString();
						split = numberOfTickets.split(" ");
						String n =split[0];
						String price = amountTextField.getText().trim();
						price = price.substring(1, price.length());
						String amountPaid = String.valueOf(Double.parseDouble(price) * Double.parseDouble(n));
						String receiptNumber="";
						int random = (int)(Math.random() * 1000); 
						receiptNumber = movieName+ "-" + String.valueOf(random);
						
						movieReservation reservation = new movieReservation(movieDate, username,fullName, movieName, showTime, 
								roomNumber, numberOfTickets, creditCardNumber, amountPaid, receiptNumber);
						
						if(hours1<0){					
							JOptionPane.showMessageDialog(null, "Sorry it is not possible as Show Time is past the Current Time");
						
						}else{
							int opType=4;
							//Message message = new Message();
							conn.bookTicket(reservation,opType);	
							if(conn.message.getStatus().equals("Yes")&&conn.message.getOperationType()==4){
									JOptionPane.showMessageDialog(null, "Reservation Successful- Here is a Reciept\n" + conn.message.getmovieReservation().toString(),"Success",JOptionPane.INFORMATION_MESSAGE);
									conn.bookTicketGui.setVisible(false);
									conn.customerGUI.setVisible(true);
									//f.setVisible(false);
							} else if(conn.message.getStatus().equals("N0")) {
								JOptionPane.showMessageDialog(null, "Reservation Failed");
							} else if(conn.message.getStatus().equals("M")) {
								JOptionPane.showMessageDialog(null, "Reservation Failed-Already a ticket is booked and cannot nook another ticket at the same point.");
								
							}
						}
					}
					
				} catch(Exception ex){
					JOptionPane.showMessageDialog(null, "Please try again!");
				}
			}
		
		});	
	}
	
	boolean validateForBookTickets() {
		
		String movieDate = showDateTextField.getText().trim();
		String creditCardNumber = creditCardNumberTextField.getText().trim();
		String cvvNumber = creditCardCVVNumberTextField.getText().trim();
		String expirationDate = creditCardExpirationDateTextField.getText().trim();
		String numberOfAvailableTickets = numberOfAvailableSeatsTextBox.getText().trim();
		int n = 0;
		
		if(numberofTicketsComboBox.getSelectedIndex() != 0){
			String[] split = numberofTicketsComboBox.getSelectedItem().toString().split(" ");
			n = Integer.parseInt(split[0]);
		}
	
		boolean validate = true;

		if(numberOfAvailableTickets.equals("0") || (n> Integer.parseInt(numberOfAvailableTickets))){
			JOptionPane.showMessageDialog(null, "Tickets not available for the desired show- Please select any other show", "Validation Error",JOptionPane.ERROR_MESSAGE);
			numberOfAvailableSeatsTextBox.requestFocus();
			validate = false;
		} else if(!validateDate(movieDate)){
			JOptionPane.showMessageDialog(null, "Please enter valid date format like MM/dd/yyyy", "Validation Error",JOptionPane.ERROR_MESSAGE);
			showDateTextField.requestFocus();
			validate = false;
		} else if(selectMovieComboBox.getSelectedIndex() == 0){
			JOptionPane.showMessageDialog(null, "Please Movie from the Movie List", "Validation Error",JOptionPane.ERROR_MESSAGE);
			selectMovieComboBox.requestFocus();
			validate = false;
		} else if(firstShowRadioButton.isSelected() == false && secondShowRadioButton.isSelected() == false && thirdShowRadioButton.isSelected() == false && fourthShowRadioButton.isSelected() == false){
			JOptionPane.showMessageDialog(null, "Please select the show time", "Validation Error",JOptionPane.ERROR_MESSAGE);
			validate = false;
		} else if(numberofTicketsComboBox.getSelectedIndex() == 0){
			JOptionPane.showMessageDialog(null, "Please select number of Tickets", "Validation Error",JOptionPane.ERROR_MESSAGE);
			numberofTicketsComboBox.requestFocus();
			validate = false;
		}else if(creditCardNumber.equals("")){
			JOptionPane.showMessageDialog(null, "Credit Card Number is empty", "Validation Error",JOptionPane.ERROR_MESSAGE);
			creditCardNumberTextField.requestFocus();
			validate = false;
		}else if(!validateCreditCard(creditCardNumber)){
			JOptionPane.showMessageDialog(null, "Please enter valid 16 digit Credit Card Number","Input error",JOptionPane.ERROR_MESSAGE);
			creditCardNumberTextField.requestFocus();
			validate = false;
		}else if(cvvNumber.equals("")){
			JOptionPane.showMessageDialog(null, "Cvv Number cannot is empty", "Validation Error",JOptionPane.ERROR_MESSAGE);
			creditCardCVVNumberTextField.requestFocus();
			validate = false;
		}else if(!validateCVV(cvvNumber)){
			JOptionPane.showMessageDialog(null, "Please enter valid 3 digit Cvv number","Input error",JOptionPane.ERROR_MESSAGE);
			creditCardCVVNumberTextField.requestFocus();
			validate = false;
		} else if(expirationDate.equals("") && !validateExpiryDate(expirationDate)){
			JOptionPane.showMessageDialog(null, "Please enter Credit Card Expiration Date in MM/yy format","Input error",JOptionPane.ERROR_MESSAGE);
			creditCardExpirationDateTextField.requestFocus();
			validate = false;
			
		}
		return validate;
	}
	
	protected boolean validateCreditCard(String phone) {
		
		boolean validPhoneNumber = true;
		Pattern phoneNumberPattern = Pattern.compile("\\d{16}");
		Matcher matcher = phoneNumberPattern.matcher(phone);
		if(matcher.matches()){
		} else{
			validPhoneNumber = false;
		}
		return validPhoneNumber;
	}
	
	protected boolean validateCVV(String phone) {
		
		boolean validPhoneNumber = true;
		Pattern phoneNumberPattern = Pattern.compile("\\d{3}");
		Matcher matcher = phoneNumberPattern.matcher(phone);
		if(matcher.matches()){
		} else{
			validPhoneNumber = false;
		}
		return validPhoneNumber;
	}
	
	protected boolean validateExpiryDate(String date) {
		
		boolean validPhoneNumber = true;
		
		try{
			String[] split = date.split("/");
			
			Integer.parseInt(split[0]);
			Integer.parseInt(split[1]);
			
		}catch(Exception e){
			validPhoneNumber = false;
		}
		return validPhoneNumber;
	}
	
	private void disableRadioButtons() {
		firstShowRadioButton.setEnabled(false);
		secondShowRadioButton.setEnabled(false);
		thirdShowRadioButton.setEnabled(false);
		fourthShowRadioButton.setEnabled(false);
		group.clearSelection();
		numberOfAvailableSeatsTextBox.setText("");
		screenTextField.setText("");
		amountTextField.setText("");
		movieRatingTextField.setText("");
		
	}
	
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
