import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class Client {
	
	private Socket conn;
	public Socket getConn() {
		return conn;
	}
	public void setConn(Socket conn) {
		this.conn = conn;
	}
	
	public initialGUI userGui;
	public customerLoginGUI customerLogin;
	public NewUserGUI registerGui;
	public LoginGUI customerGUI;
	public cancelTicketGUI cancelTicketGui;
	public viewTicketGUI viewTicketGui;
	//public BookTicketGUI bookTicketGui;
	ObjectOutputStream clientOutputStream;
	ObjectInputStream clientInputStream;
	public Message message;
	DbaGUI dbaGui;
	BookTicketGUI bookTicketGui;
	
	private final int REGISTER_OP=0;
	private final int LOGIN_OP=1;
	private final int GET_SHOWS_DATE=2;
	private final int GET_RANK=3;
	private final int BOOK_TICKET=4;
	private final int VIEW_TICKET=5;
	private final int GET_TICKET=6;
	private final int CANCEL_TICKET=7;
	
	
	private final int REGISTERA_OP=10;
	private final int LOGIN_OP_ADMIN=11;
	private final int MOVIEADD_OP_ADMIN=12;
	private final int MOVIEVIEW_OP_ADMIN=13;
	private final int MOVIEUPDATE_OP_ADMIN=14;
	private final int MOVIEDELETE_OP_ADMIN=15;
	private final int MOVIEGET_OP_ADMIN=16;
	private final int UPDATESHOW_OP_ADMIN=17;
	private final int GETSHOW_OP_ADMIN=18;
	
	
	public Client(int port) throws IOException {
		viewGui(); 
		connect("localhost", port); 
	}
	
    public void viewGui(){
		
		userGui = new initialGUI(this);
		userGui.pack();
		userGui.setVisible( true );
		userGui.setLocationRelativeTo(null); // Center the frame
	    userGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
	}
	
	public void connect(String hostAddress,int  connectingPort) throws IOException{
		try{
			conn = new Socket(hostAddress, connectingPort);
			clientOutputStream = new ObjectOutputStream(conn.getOutputStream());
			clientInputStream = new ObjectInputStream(conn.getInputStream());
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Please start server first!");
			System.exit(0);
		}
	}
	
    public void registerCustomer(String username, String password, String fullname, String emailAddress,String mailAddress,int phoneNumber , String userType, int opType) throws IOException, ClassNotFoundException {
		sendMessage(username,password,fullname, mailAddress,emailAddress,phoneNumber,userType,opType);		
	}
    
    public void customerLogin(String username,String password,String customerType) throws IOException, ClassNotFoundException{
		message=new Message(username,password,customerType);
		message.setOperationType(LOGIN_OP);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
    
    public void adminLogin(String username,String password,String customerType) throws IOException, ClassNotFoundException{
		message=new Message(username,password,customerType);
		message.setOperationType(LOGIN_OP_ADMIN);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
    public void viewMovie(String movieName,String movieReview,String movieRank,String userType,int opType) throws IOException, ClassNotFoundException{
		message=new Message(movieName,movieReview,movieRank,userType,opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
    
    public void addMovie(String movieName,String movieReview,String movieRank,String userType,int opType) throws IOException, ClassNotFoundException{
		//message=new Message(username,password,customerType);
		message=new Message(movieName,movieReview,movieRank,userType,opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void updateMovie(String movieName,String movieReview,String movieRank,String userType,int opType) throws IOException, ClassNotFoundException{
		message=new Message(movieName,movieReview,movieRank,userType,opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void deleteMovie(String movieName,String movieReview,String movieRank,String userType,int opType) throws IOException, ClassNotFoundException{
		message=new Message(movieName,movieReview,movieRank,userType,opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void getMovies(int opType) throws IOException, ClassNotFoundException{
		message=new Message(opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void updateShow(String selectMovieString,String showDateString,String roomNumber,String price,String firstShowAvailable,String secondShowAvailable,String thirdShowAvailable,String fourthShowAvailable,String userType,int opType) throws IOException, ClassNotFoundException{
		message=new Message(selectMovieString,showDateString,roomNumber,price,firstShowAvailable,secondShowAvailable,thirdShowAvailable,fourthShowAvailable,userType,opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void viewShowInfo(String movieName,String showDateString,String userType,int opType) throws IOException, ClassNotFoundException{
		message=new Message(movieName,showDateString,userType,opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void getShowDates(String showDate,int opType) throws IOException, ClassNotFoundException{
		message.setdate(showDate);
		message.setOperationType(opType);
		message.setStatus("No");
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void getMovieRating(String movieName,int opType) throws IOException, ClassNotFoundException{
		message.setMovieName(movieName);
		message.setOperationType(opType);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void bookTicket(movieReservation reservation,int opType) throws IOException, ClassNotFoundException{
		message.setOperationType(opType);
		message.setmovieReservation(reservation);
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void viewTicket(String dateText,String username,int opType) throws IOException, ClassNotFoundException{
		message.setOperationType(opType);
		message.setdate(dateText);
		message.setUsername(username);
		message.setStatus("No");
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	public void getTicket(String ticketNumber,String username,int opType) throws IOException, ClassNotFoundException{
		message.setOperationType(opType);
		message.setticketIdNumber(ticketNumber);
		message.setUsername(username);
		message.setStatus("No");
		clientOutputStream.writeObject(message);
		receivingMessage();
	}
	
	public void CancelTicket(String referenceNumber,String username,int opType) throws IOException, ClassNotFoundException{
		message.setOperationType(opType);
		message.setdate(referenceNumber);
		message.setUsername(username);
		message.setStatus("No");
		clientOutputStream.writeObject(message);
		receivingMessage();
	}

	public void sendMessage(String username, String password, String fullname, String emailAddress,String mailAddress,int phoneNumber , String userType, int opType) throws IOException, ClassNotFoundException {	
		message = new Message (username,password,fullname,emailAddress,mailAddress,phoneNumber,userType, opType);
		clientOutputStream.writeObject(message);
       	receivingMessage();	
	}
	
	private void receivingMessage() throws IOException, ClassNotFoundException {
		
		message = (Message)clientInputStream.readObject();
		
		if(message!=null){
			switch (message.operationType) { 
			case REGISTER_OP: if (message.getStatus().equals("Yes")){
					System.out.println("Customer Registration Successfull!");
		            }else{
		            System.out.println("Customer Registration Unsuccessfull, Please try again!");
		            }
	                break;    
			case LOGIN_OP: if (message.getStatus().equals("Yes")){
					System.out.println(" Customer Login successful!");
	            }else{
	            	System.out.println("Customer Login Unsuccessful!");
	            }
                break;
                
			case REGISTERA_OP: if (message.getStatus().equals("Yes")){
				System.out.println("Admin Registration Successfull!");
	            }else{
	            System.out.println("Admin Registration Unsuccessfull!");
	            }
                break;     
			case LOGIN_OP_ADMIN: if (message.getStatus().equals("Yes")){
				System.out.println("Admin Login successful!");
            }else{
            	System.out.println("Admin Login Unsuccessful!");
            }
            break;
			
			case BOOK_TICKET:if (message.getStatus().equals("Yes")){
				System.out.println("Ticket Booking Successfull!");
			}else{
				System.out.println("Ticket Booking Failed!");
			}
			break;
			
			case CANCEL_TICKET:if (message.getStatus().equals("Yes")){
				System.out.println("Cancellation Done!");
			}else{
				System.out.println("Cancellation not done, please try again!");
			}
			break;
			}
		}        
	}
    
    public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		int port=8080;
		Client client = new Client(port);
	}

}
