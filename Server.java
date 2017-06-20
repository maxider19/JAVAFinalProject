import java.awt.Container;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Server {

	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	ServerGui gui;
	
	private final int REGISTER_OP = 0;
	private final int LOGIN_OP = 1;
	private final int GET_SHOWS_DATE=2;
	private final int GET_RANK=3;
	private final int BOOK_TICKET=4;
	private final int VIEW_TICKET=5;
	private final int GET_TICKET=6;
	private final int CANCEL_TICKET=7;
	
	private final int REGISTERA_OP=10;
	private final int ADMIN_OP=11;
	private final int MOVIEADD_OP_ADMIN=12;
	private final int MOVIEVIEW_OP_ADMIN=13;
	private final int MOVIEUPDATE_OP_ADMIN=14;
	private final int MOVIEDELETE_OP_ADMIN=15;
	private final int MOVIEGET_OP_ADMIN=16;
	private final int UPDATESHOW_OP_ADMIN=17;
	private final int GETSHOW_OP_ADMIN=18;
	
	public Server(int port) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		viewGui();
		startListening(port);
	}	
	
		
	public void startListening(int listeningPort) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		try {
			ServerSocket serverSocket = new ServerSocket(listeningPort);
			// this will load the MySQL driver, each DB has its own driver
		      Class.forName("com.mysql.jdbc.Driver");
		     try{
		    	 connect = DriverManager.getConnection("jdbc:mysql://localhost/kisoks", "root", "");
		     }catch(Exception ex){
		    	 JOptionPane.showMessageDialog(null, "Please Start Server first!");
		    	 System.exit(1);
		     }
		      // setup the connection with the DB
		      
			  gui.txtDisplayResults.append("Server Is Listening ON Port:  " + listeningPort + "\n");
			
			  while (true) {
			        // Listen for a new connection request
			        Socket socket = serverSocket.accept();
			        
			        gui.txtDisplayResults.append("Connection Request ....... Connection Accepted" + "\n");

			        // Create a new thread for the connection
			        HandleAClient task = new HandleAClient(socket);

			        // Start the new thread
			        new Thread(task).start();
			       
			  }
			  
		} catch (IOException e) {
			e.printStackTrace();
			gui.txtDisplayResults.append(e.getMessage()+"   :It couldn't listen on the port "+listeningPort + "\n");
			
		}		
	}
	
	public void addAdmin (Message message) throws SQLException{
		
		try{
			statement = connect.createStatement();
			preparedStatement = connect.prepareStatement("insert into registeredUser(username,password,fullname,mailAddress,emailAddress,phoneNumber,userType) values (?, ?, ?, ?, ?, ?, ?)");
		    preparedStatement.setString(1, message.username);
		    preparedStatement.setString(2, message.password);
		    preparedStatement.setString(3, message.fullName);
		    preparedStatement.setString(4, message.mailAddress);
		    preparedStatement.setString(5, message.emailAddress);
		    preparedStatement.setInt(6, message.phoneNumber);
		    preparedStatement.setString(7,message.userType);
		    int i=preparedStatement.executeUpdate();
		    
		    if(i>0){
		    	message.setStatus("Yes");
		    }
		    else{
		    	message.setStatus("No");
		    }
		}catch(Exception ex){
			message.setStatus("No");
			
		}
}
	
	

	
	public void addCustomer (Message message) throws SQLException{
		
			try{
				statement = connect.createStatement();
				preparedStatement = connect.prepareStatement("insert into registeredUser(username,password,fullname,mailAddress,emailAddress,phoneNumber,userType) values (?, ?, ?, ?, ?, ?, ?)");
			    preparedStatement.setString(1, message.username);
			    preparedStatement.setString(2, message.password);
			    preparedStatement.setString(3, message.fullName);
			    preparedStatement.setString(4, message.mailAddress);
			    preparedStatement.setString(5, message.emailAddress);
			    preparedStatement.setInt(6, message.phoneNumber);
			    preparedStatement.setString(7,message.userType);
			    int i=preparedStatement.executeUpdate();
			    if(i>0){
			    	message.setStatus("Yes");
			    }
			    else{
			    	message.setStatus("No");
			    }
			    
			}catch(Exception ex){
				message.setStatus("No");
			}
	}
	
	public void customerLogin (Message message) throws SQLException{
		
		try{
			String query="SELECT * from registeredUser where userName=? and password=? and userType=?";
			preparedStatement = connect.prepareStatement(query);
			
			preparedStatement.setString(1, message.username);
			preparedStatement.setString(2, message.password);
			preparedStatement.setString(3, message.userType);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){
				message.setStatus("Yes");
				message.setUsername(resultSet.getString("userName"));
				message.setFullName(resultSet.getString("fullName"));
				message.setUserType(resultSet.getString("userType"));
			}
			else{
				message.setStatus("No");
			}
			
		}catch(Exception ex){
			message.setStatus("No");
			message.setErrorMessage("Wrong customer username/password, please try again!");
		}
		
	}
	
	public void adminLogin (Message message) throws SQLException{
		try{
			String query="SELECT * from registeredUser where userName=? and password=? and userType=?";
			preparedStatement = connect.prepareStatement(query);
			
			preparedStatement.setString(1, message.username);
			preparedStatement.setString(2, message.password);
			preparedStatement.setString(3, message.userType);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()){
				message.setStatus("Yes");
				message.setFullName(resultSet.getString("userName"));
				message.setFullName(resultSet.getString("fullName"));
				message.setUserType(resultSet.getString("userType"));
			}
			else{
				message.setStatus("No");
			}
		}catch(Exception ex){
				message.setStatus("No");
				message.setErrorMessage("Wrong admin username/password, please try again!");
		}		
	}
	
	public void viewMovie (Message message) throws SQLException{
		try{
			String query = "SELECT * from movie where name = ?  ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			
			preparedStmt.setString(1, message.getMovieName());
			
			ResultSet resultset = preparedStmt.executeQuery();
			
			if(resultset.next()){
				message.setStatus("Yes");
				
				message.setMovieName(resultset.getString("name"));
				message.setmovieRating(resultset.getString("rank"));
				message.setMovieReview(resultset.getString("review"));
				
			} else{
				message.setStatus("No");
			}
			
		} catch(Exception e){
				
				message.setStatus("No");
		}
	}
	
	public void addMovie (Message message) throws SQLException{
		try{
			String query = "INSERT INTO Movie (name,rank,review) values (?,?,?)";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			
			preparedStmt.setString(1, message.getMovieName());
			preparedStmt.setString(2, message.getmovieRating());
			preparedStmt.setString(3, message.getMovieReview());
			int i = preparedStmt.executeUpdate();
			
			if(i>0){
				message.setStatus("Yes");
			} else {
				message.setStatus("No");
			}
		}catch(Exception e){
				message.setStatus("No");
		}
	}
	
	public void updateMovie (Message message) throws SQLException{
		try{
			String query1 = "Delete From movie where name = ?";
			PreparedStatement preparedStmt1 = connect.prepareStatement(query1);
			preparedStmt1.setString(1, message.getMovieName());
			int i1 = preparedStmt1.executeUpdate();
			
			if(i1>0){
				String query = "INSERT INTO movie (name,rank,review) values (?,?,?)";
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				
				preparedStmt.setString(1, message.getMovieName());
				preparedStmt.setString(2,message.getmovieRating());
				preparedStmt.setString(3, message.getMovieReview());
				int i = preparedStmt.executeUpdate();
				
				if(i>0){
					message.setStatus("Yes");
				} else {
					message.setStatus("No");
				}
			} else {
				message.setStatus("No");
			}
		}catch(Exception e){
				message.setStatus("No");
		}
	}
	
	public void deleteMovie (Message message) throws SQLException{
		try{
			String query = "Delete From movie where name = ?";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			preparedStmt.setString(1, message.getMovieName());
			
			int i = preparedStmt.executeUpdate();
			
			if(i>0){
				message.setStatus("Yes");
			} else {
				message.setStatus("No");
			}	
		}catch(Exception e){
				message.setStatus("No");
		}
	}
	
	public void getMovies (Message message) throws SQLException{
		try{
			String query = "SELECT * from movie ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			ResultSet resultset = preparedStmt.executeQuery();
			ArrayList<String> list = new ArrayList<String>();
			
			while(resultset.next()){
				message.setStatus("Yes");
				list.add(resultset.getString("name"));	
			} 
			message.setmovieArray(list);
		}catch(Exception e){	
			message.setStatus("No");
		}
	}
	
	public void updateShowTimes(Message message) throws SQLException{
		boolean update = false;		
		try{
			String query = "Select * from showtime where showDate = ? and movieName = ? ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
		    preparedStmt.setString(1, message.getdate());
		    preparedStmt.setString(2, message.getMovieName());
			ResultSet resultset = preparedStmt.executeQuery();
			
			if(resultset.next()){
				update = true;
				 query = "UPDATE showtime set firstShowAvailable =?, secondShowAvailable=?,thirdShowAvailable=?,fourthShowAvailable=?,roomNumber=?,price=? where showDate = ? and movieName = ? ";		 
				 preparedStmt = connect.prepareStatement(query);				 
				 preparedStmt.setString(1, message.getfirstShow());
				 preparedStmt.setString(2, message.getsecondShow());
				 preparedStmt.setString(3, message.getthirdShow());
				 preparedStmt.setString(4, message.getfourthShow());
				 preparedStmt.setString(5, message.getscreen());
				 preparedStmt.setString(6, message.getpay());
				 preparedStmt.setString(7, message.getdate());
				 preparedStmt.setString(8, message.getMovieName()); 
				 
				 int i = preparedStmt.executeUpdate();
				 
				 if(i>0){
					 message.setStatus("Yes");
				 } else{
					 message.setStatus("No");
				 }	
			}
		}catch(Exception e){
				message.setStatus("No");
				message.setErrorMessage(e.toString());
		}
		
		if(update == false){
			try{
				String query = "INSERT INTO showtime (movieName,showDate,firstShowAvailable,secondShowAvailable,thirdShowAvailable,fourthShowAvailable,roomNumber,price,firstShowCapacity,secondShowCapacity,thirdShowCapacity,fourthShowCapacity) values (?,?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement preparedStmt = connect.prepareStatement(query);
				preparedStmt.setString(1, message.getMovieName());
				preparedStmt.setString(2, message.getdate());
				preparedStmt.setString(3, message.getfirstShow());
				preparedStmt.setString(4, message.getsecondShow());
				preparedStmt.setString(5, message.getthirdShow());
				preparedStmt.setString(6, message.getfourthShow());
				preparedStmt.setString(7, message.getscreen());
				preparedStmt.setString(8, message.getpay());
				
				if(message.getfirstShow().equals("Yes")){
					preparedStmt.setString(9, "50");
				} else{
					preparedStmt.setString(9, "0");	
				}
				if(message.getsecondShow().equals("Yes")){
					preparedStmt.setString(10, "50");
				} else{
					preparedStmt.setString(10, "0");
				}
				if(message.getthirdShow().equals("Yes")){
					preparedStmt.setString(11, "50");
				} else{
					preparedStmt.setString(11, "0");
				}
				if(message.getfourthShow().equals("Yes")){
					preparedStmt.setString(12, "50");
				} else{
					preparedStmt.setString(12, "0");
				}
				
				int i = preparedStmt.executeUpdate();
				
				if(i>0){
					message.setStatus("Yes");
				} else {
					message.setStatus("No");
				}
			}catch(Exception e){
				message.setStatus("No");
				message.setErrorMessage(e.toString());
			}
		}
	}
	
	public void getShowTimes(Message message) throws SQLException{
		try{	
			message.setStatus("No");
			String query = "SELECT * from showtime where movieName =? and showDate =? ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			preparedStmt.setString(1, message.getMovieName());
			preparedStmt.setString(2, message.getdate());
			ResultSet resultset = preparedStmt.executeQuery();
			
			if(resultset.next()){
				message.setfirstShow(resultset.getString("firstShowAvailable"));
				message.setsecondShow(resultset.getString("secondShowAvailable"));
				message.setthirdShow(resultset.getString("thirdShowAvailable"));
				message.setfourthShow(resultset.getString("fourthShowAvailable"));
				message.setscreen(resultset.getString("roomNumber"));
				message.setpay(resultset.getString("price"));
				message.setStatus("Yes");
			} 
			else{
				message.setStatus("No");
			}
		}catch(Exception ex){
			message.setStatus("F");
			ex.printStackTrace();
		}
	}
	
	public void getShowsDate(Message message) throws SQLException{		
		try{
		String query = "Select * from showtime where showDate = ?";
		PreparedStatement preparedStmt = connect.prepareStatement(query);	
	    preparedStmt.setString(1, message.getdate());
		ResultSet resultset = preparedStmt.executeQuery();
		ArrayList<showTime> list = new ArrayList<showTime>();
		
		while(resultset.next()){
			showTime object = new showTime();
			object.setmovieName(resultset.getString("movieName"));
			object.setdate(resultset.getString("showDate"));
			object.setfirstShow(resultset.getString("firstShowAvailable"));
			object.setfirstShowSeats(resultset.getString("firstShowCapacity"));
			object.setsecondShow(resultset.getString("secondShowAvailable"));
			object.setsecondShowSeats(resultset.getString("secondShowCapacity"));
			object.setthirdShow(resultset.getString("thirdShowAvailable"));
			object.setthirdShowSeats(resultset.getString("thirdShowCapacity"));
			object.setfourthShow(resultset.getString("fourthShowAvailable"));
			object.setfourthShowSeats(resultset.getString("fourthShowCapacity"));
			object.setscreen(resultset.getString("roomNumber"));
			object.setpay(resultset.getString("price"));
			message.setStatus("Yes");
			list.add(object);
		} 
		message.setShowList(list);
		}catch(Exception e){
			message.setStatus("No");
			message.setErrorMessage(e.toString());
		}	
	}
	
	public void getRating(Message message) throws SQLException{			
		// Check Login
		try{
		String query = "SELECT * from movie where name =? ";
		PreparedStatement preparedStmt = connect.prepareStatement(query);
		preparedStmt.setString(1, message.getMovieName());
		ResultSet resultset = preparedStmt.executeQuery();
		
		while(resultset.next()){
			message.setStatus("Yes");
			message.setmovieRating(resultset.getString("Rating"));
		} 
		} catch(Exception e){
			message.setStatus("No");
		}
	}

	private void bookTicket(Message message) throws SQLException {
		// TODO Auto-generated method stub
		// Creating a New Reservation
		
		boolean insert = true;
		String query="SELECT * from Reservation where userFK = ? and movie_date =?";
		
		PreparedStatement preparedStmt = connect.prepareStatement(query);
		
		preparedStmt.setString(1, message.getmovieReservation().getUserName());
		preparedStmt.setString(2, message.getmovieReservation().getMovieDate());
		
		
		ResultSet rs = preparedStmt.executeQuery();
		if(rs.next()){
			insert = false;
		} 
	
		if(insert == true){
			try{
				query = "INSERT INTO Reservation (reservationPK,userFK,showFK,movie_date,show_time,room_number,number_of_tickets," + "credit_card_number,amount_paid) values (?,?,?,?,?,?,?,?,?)";
				preparedStmt = connect.prepareStatement(query);
				
				preparedStmt.setString(1, message.getmovieReservation().getconfirmationNumber());
				preparedStmt.setString(2, message.getmovieReservation().getUserName());
				preparedStmt.setString(3, message.getmovieReservation().getMovieName());
				preparedStmt.setString(4, message.getmovieReservation().getMovieDate());
				preparedStmt.setString(5, message.getmovieReservation().getShowTime());
				preparedStmt.setString(6, message.getmovieReservation().getscreen());
				preparedStmt.setString(7, message.getmovieReservation().getNumberOfTickets());
				preparedStmt.setString(8, message.getmovieReservation().getCreditCardNumber());
				preparedStmt.setString(9, message.getmovieReservation().getAmountPaid());
				int i = preparedStmt.executeUpdate();
				
				if(i>0){
					message.setStatus("Yes");
				} else {
					message.setStatus("No");
				}
		
			}catch(Exception e){
				message.setStatus("No");
				message.setErrorMessage(e.toString());
			}
			showTime object = new showTime();
		
		try{
			query = "Select * from showtime where showDate = ? and movieName =?";
			preparedStmt = connect.prepareStatement(query);
		    preparedStmt.setString(1, message.getmovieReservation().getMovieDate());
		    preparedStmt.setString(2, message.getmovieReservation().getMovieName());
			ResultSet resultset = preparedStmt.executeQuery();
			
			if(resultset.next()){
				message.setStatus("Yes");
				object.setmovieName(resultset.getString("movieName"));
				object.setdate(resultset.getString("showDate"));
				object.setfirstShow(resultset.getString("firstShowAvailable"));
				object.setfirstShowSeats(resultset.getString("firstShowCapacity"));
				object.setsecondShow(resultset.getString("secondShowAvailable"));
				object.setsecondShowSeats(resultset.getString("secondShowCapacity"));
				object.setthirdShow(resultset.getString("thirdShowAvailable"));
				object.setthirdShowSeats(resultset.getString("thirdShowCapacity"));
				object.setfourthShow(resultset.getString("fourthShowAvailable"));
				object.setfourthShowSeats(resultset.getString("fourthShowCapacity"));
				object.setscreen(resultset.getString("roomNumber"));
				object.setpay(resultset.getString("price"));
			} 
			
			String[] split = message.getmovieReservation().getNumberOfTickets().split(" ");
			
			if(message.getmovieReservation().getShowTime().equals("10:00 AM")){
				
				int firstShowCapacity = Integer.parseInt(object.getfirstShowSeats());
				firstShowCapacity = firstShowCapacity - Integer.parseInt(split[0].trim());
				object.setfirstShowSeats(String.valueOf(firstShowCapacity));
				
			} else if(message.getmovieReservation().getShowTime().equals("2:00 PM")){
				int secondShowCapacity = Integer.parseInt(object.getsecondShowSeats());
				secondShowCapacity = secondShowCapacity - Integer.parseInt(split[0].trim());
				object.setsecondShowSeats(String.valueOf(secondShowCapacity));
				
			}else if(message.getmovieReservation().getShowTime().equals("5:00 PM")){
				int thirdShowCapacity = Integer.parseInt(object.getthirdShow());
				thirdShowCapacity = thirdShowCapacity - Integer.parseInt(split[0].trim());
				object.setthirdShowSeats(String.valueOf(thirdShowCapacity));
				
			} else{
				int fourthShowCapacity = Integer.parseInt(object.getfourthShowSeats());
				fourthShowCapacity = fourthShowCapacity - Integer.parseInt(split[0].trim());
				object.setfourthShowSeats(String.valueOf(fourthShowCapacity));
			}
			
		}catch(Exception e){
			message.setStatus("No");
			message.setErrorMessage(e.toString());
		}
		
		try{
			query = "UPDATE showtime Set firstShowCapacity ='" + object.getfirstShowSeats() + "' , secondShowCapacity ='" +object.getsecondShowSeats() +  "',thirdShowCapacity ='" + object.getthirdShow() + "',fourthShowCapacity ='" + object.getfourthShowSeats() + "' where movieName= '"+ message.getmovieReservation().getMovieName() + "' and showDate='" + message.getmovieReservation().getMovieDate() + "'";	
			preparedStmt = connect.prepareStatement(query);
			int i = preparedStmt.executeUpdate();
			if(i>0){
				message.setErrorMessage("Seats updated successfully");		
			}else{
				message.setErrorMessage("Seats not updated");
			}
		}catch(Exception e){
			message.setErrorMessage("Seats not updated");
		}
		} else{
			message.setStatus("M");
		}
	}
	
	private void viewTicket(Message message){	
		try{
			
			message.setStatus("No");			
			String query = "SELECT * from Reservation where userFK =? and movie_date =? ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			preparedStmt.setString(1, message.getUsername());
			preparedStmt.setString(2, message.getdate());			
			ResultSet resultset = preparedStmt.executeQuery();
			ArrayList<movieReservation> list = new ArrayList<movieReservation>();
			
			while(resultset.next()){
				message.setStatus("Yes");
				String movieDate = resultset.getString("movie_date");
				String userName = resultset.getString("userFK");
				String movieName = resultset.getString("showFK");
				String showTime = resultset.getString("show_time");
				String roomNumber = resultset.getString("room_number");
				String numberOfTickets = resultset.getString("number_of_tickets");
				String creditCardNumber = resultset.getString("credit_card_number");
				String amountPaid = resultset.getString("amount_paid");
				
				String recieptNumber = resultset.getString("reservationPK");
				movieReservation reservation = new movieReservation(movieDate, userName, "", movieName, showTime, roomNumber, numberOfTickets, creditCardNumber, amountPaid, recieptNumber);
				list.add(reservation);
			} 
			message.setarrayMovieReservation(list);
			} catch(Exception e){
				message.setStatus("No");
				e.printStackTrace();
			}
	}
	
	private void getTicket(Message message){
		
		// Check Login		
		try{
			String query = "Select * from Reservation where reservationPK =? ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			preparedStmt.setString(1, message.getticketIdNumber());
			ResultSet resultset = preparedStmt.executeQuery();
			
			while(resultset.next()){
				message.setStatus("Yes");
				String movieDate = resultset.getString("movie_date");
				String userName = resultset.getString("userFK");
				String movieName = resultset.getString("showFK");
				String showTime = resultset.getString("show_time");
				String roomNumber = resultset.getString("room_number");
				String numberOfTickets = resultset.getString("number_of_tickets");
				String creditCardNumber = resultset.getString("credit_card_number");
				String amountPaid = resultset.getString("amount_paid");
				String recieptNumber = message.getticketIdNumber();
				movieReservation reservation = new movieReservation(movieDate, userName, "", movieName, showTime, roomNumber, numberOfTickets, creditCardNumber, amountPaid, recieptNumber);
				message.setmovieReservation(reservation);
			}
		}catch(Exception e){
			message.setStatus("No");
		}
	}
	
	private void cancelTicket(Message message){
		try{
			String query = "Select * from reservation where reservationPK =? ";
			PreparedStatement preparedStmt = connect.prepareStatement(query);
			preparedStmt.setString(1, message.getticketIdNumber());
			ResultSet resultset = preparedStmt.executeQuery();
			
			while(resultset.next()){
				message.setStatus("Yes");
				String movieDate = resultset.getString("movie_date");
				String userName = resultset.getString("userFK");
				String movieName = resultset.getString("showFK");
				String showTime = resultset.getString("show_time");
				String roomNumber = resultset.getString("room_number");
				String numberOfTickets = resultset.getString("number_of_tickets");
				String creditCardNumber = resultset.getString("credit_card_number");
				String amountPaid = resultset.getString("amount_paid");
				String recieptNumber = message.getticketIdNumber();
				movieReservation reservation = new movieReservation(movieDate, userName, "", movieName, showTime, roomNumber, numberOfTickets, creditCardNumber, amountPaid, recieptNumber);
				message.setmovieReservation(reservation);
			} 
		}catch(Exception e){
			message.setStatus("No");
		}
		
		try{	
			String hours = message.getmovieReservation().getShowTime();
			if(hours.equals("10:00 AM")){
				hours ="10:00:00";
			} else if(hours.equals("2:00 PM")){
				hours ="14:00:00";
			} else if(hours.equals("5:00 PM")){
				hours ="17:00:00";
			} else{
				hours ="21:00:00";
			}
			String dateTest =message.getmovieReservation().getMovieDate();
			String[] split = dateTest.split("/");
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:MM:ss");
			java.util.Date date = dateFormat.parse(String.valueOf(Integer.parseInt(split[2]) +1) + "-" + split[0] + "-" + split[1] +" " + hours);
			long time = date.getTime();
			Timestamp object1 = new Timestamp(time);
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			long milliseconds = object1.getTime() - timestamp.getTime();
		    int seconds = (int) milliseconds / 1000;

		    // calculate hours minutes and seconds
		    int hours1 = seconds / 3600;
		    
		    if(hours1>=1){
		    	String query = "Delete from reservation where reservationPK =? ";
		    	PreparedStatement preparedStmt = connect.prepareStatement(query);
		    	preparedStmt.setString(1, message.getticketIdNumber());
		    	int i = preparedStmt.executeUpdate();
		    	if(i >0){
		    		message.setStatus("Yes");
		    	}else{
		    		message.setStatus("No");
		    	}
		    	showTime object = new showTime();
		    	try{
		    		query = "Select * from showtime where showDate = ? and movieName =?";
			    	preparedStmt = connect.prepareStatement(query);
			    	preparedStmt.setString(1, message.getmovieReservation().getMovieDate());
			    	preparedStmt.setString(2, message.getmovieReservation().getMovieName());
			    	ResultSet resultset = preparedStmt.executeQuery();
			    	if(resultset.next()){
			    		message.setStatus("Yes");	
			    		object.setmovieName(resultset.getString("movieName"));
			    		object.setdate(resultset.getString("showDate"));
			    		object.setfirstShow(resultset.getString("firstShowAvailable"));
			    		object.setfirstShowSeats(resultset.getString("firstShowCapacity"));
			    		object.setsecondShow(resultset.getString("secondShowAvailable"));
			    		object.setsecondShowSeats(resultset.getString("secondShowCapacity"));
			    		object.setthirdShow(resultset.getString("thirdShowAvailable"));
			    		object.setthirdShowSeats(resultset.getString("thirdShowCapacity"));
			    		object.setfourthShow(resultset.getString("fourthShowAvailable"));
			    		object.setfourthShowSeats(resultset.getString("fourthShowCapacity"));
			    		object.setscreen(resultset.getString("roomNumber"));
			    		object.setpay(resultset.getString("price"));	
			    	}
			    		
			    	split = message.getmovieReservation().getNumberOfTickets().split(" ");
			    	
			    	if(message.getmovieReservation().getShowTime().equals("10:00 AM")){
			    		
			    		int firstShowCapacity = Integer.parseInt(object.getfirstShowSeats());
			    		firstShowCapacity = firstShowCapacity + Integer.parseInt(split[0].trim());
			    		object.setfirstShowSeats(String.valueOf(firstShowCapacity));	
			    		
			    	} else if(message.getmovieReservation().getShowTime().equals("2:00 PM")){
			    		
			    		int secondShowCapacity = Integer.parseInt(object.getsecondShowSeats());
			    		secondShowCapacity = secondShowCapacity + Integer.parseInt(split[0].trim());
			    		object.setsecondShowSeats(String.valueOf(secondShowCapacity));
			    			
			    	} else if(message.getmovieReservation().getShowTime().equals("5:00 PM")){
			    		
			    		int thirdShowCapacity = Integer.parseInt(object.getthirdShowSeats());
			    		thirdShowCapacity = thirdShowCapacity + Integer.parseInt(split[0].trim());
			    		object.setthirdShowSeats(String.valueOf(thirdShowCapacity));
			    			
			    	} else{
			    		
			    		int fourthShowCapacity = Integer.parseInt(object.getfourthShowSeats());
			    		fourthShowCapacity = fourthShowCapacity + Integer.parseInt(split[0].trim());
			    		object.setfourthShowSeats(String.valueOf(fourthShowCapacity));
			    	}
			    			
		    	}catch(Exception e){
		    		message.setStatus("No");
			    	message.setErrorMessage(e.toString());
			    		
		    	}
		  
		    	try{
		    		query = "UPDATE ShowTime Set first_show_capacity ='" + object.getfirstShowSeats() + "' , second_show_capacity ='" +object.getsecondShowSeats() +  "',third_show_capacity ='" + object.getthirdShowSeats() + "',fourth_show_capacity ='" + object.getfourthShowSeats() + "' where movie_name= '"+ message.getmovieReservation().getMovieName() + "' and show_date='" + message.getmovieReservation().getMovieDate() + "'";
		    		preparedStmt = connect.prepareStatement(query);
		    		i = preparedStmt.executeUpdate();
		    		
		    		if(i>0){
		    			message.setStatus("Yes");
		    		}
		    	}catch(Exception e){
		    		message.setStatus("No");	
		    	}	
		    }
		}catch(Exception ex){
			message.setStatus("No");
		}	
	}
	
	public void viewGui(){
		JFrame frame = new JFrame();
		frame.setTitle("Kisoks Server");
		Container contentPane = frame.getContentPane();
		gui = new ServerGui(this);
		contentPane.add(gui);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void closeAll() throws IOException, SQLException{
	 
     resultSet.close();
     statement.close();
     connect.close();
 	 System.exit(0);
 	 
 	}

	class HandleAClient implements Runnable {
	    private Socket socket; // A connected socket

	    /** Construct a thread */
	    public HandleAClient(Socket socket) {
	      this.socket = socket;
	    }

	    /** Run a thread */
	    public void run() {
	      try {
	        // Create data input and output streams
	        ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
	        ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());

	        // Continuously serve the client
	        while (true) {
	        	Message message = null;	
	    		message = (Message )inputFromClient.readObject();
	            switch (message.operationType) {
	            
	            case REGISTER_OP: addCustomer(message);
	            break;
	            				
	            case LOGIN_OP: customerLogin(message);
				break;
							
	            case ADMIN_OP:adminLogin(message);
	            break;
	            		
	            case REGISTERA_OP:addAdmin(message);
        		break;
	            
	            case MOVIEADD_OP_ADMIN:addMovie(message);
	            break;
	            	
	            case MOVIEVIEW_OP_ADMIN:viewMovie(message);
	            break;
	            	
	            case MOVIEUPDATE_OP_ADMIN:updateMovie(message);
	            break;
	    		
	            case MOVIEDELETE_OP_ADMIN:deleteMovie(message);
            	break;
	            
	            case MOVIEGET_OP_ADMIN:getMovies(message);
	            break;
	            
	            case UPDATESHOW_OP_ADMIN:updateShowTimes(message);
            	break;	
	            
	            case GETSHOW_OP_ADMIN:getShowTimes(message);
            	break;	
	            
	            case GET_SHOWS_DATE:getShowsDate(message);
            	break;
	            
	            case GET_RANK:getRating(message);
            	break;
	            
	            case BOOK_TICKET:bookTicket(message);
            	break;
	            
	            case VIEW_TICKET:viewTicket(message);
            	break;
	            
	            case GET_TICKET:getTicket(message);
            	break;
            	
	            case CANCEL_TICKET:cancelTicket(message);
            	break;
	            }
	    			
	            outputToClient.writeObject(message);; 
	    	  }
	      }catch(IOException e) {
	    	  System.err.println(e);
	      }catch(ClassNotFoundException e) {
	    	  // TODO Auto-generated catch block
			  e.printStackTrace();
	      }catch (SQLException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
	      }
	    }	
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, SQLException {
		int port = 8080; 
	    Server server = new Server(port);
	} 

}
