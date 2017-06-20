import java.io.Serializable;

public class movieReservation implements Serializable{
	
	private String userName;
	private String fullName;
	private String movieDate;
	private String movieName;
	private String showTime;
	private String screen;
	private String numberOfTickets;
	private String creditCardNumber;
	private String amountPaid;
	private String confirmationNumber;
	
	
	public movieReservation(String movieDate,String userName,String fullName,String movieName,String showTime,String screen,String numberOfTickets,String creditCardNumber,
			String amountPaid,String confirmationNumber){
		
		this.movieDate = movieDate;
		this.fullName = fullName;
		this.userName = userName;
		this.movieName = movieName;
		this.showTime = showTime;
		this.screen = screen;
		this.numberOfTickets = numberOfTickets;
		this.creditCardNumber = creditCardNumber;
		this.amountPaid = amountPaid;
		this.confirmationNumber = confirmationNumber;
		
	}
	
	
	@Override
	public String toString() {
		
		
		String movieReservation ="";
		
		movieReservation = "\nTicket Number: " + this.confirmationNumber +"\n" + "User: " + this.userName + "\n" + "Name: " + this.fullName + "\n" +  "Movie Name: " + this.movieName.toUpperCase() +"\n" + "Show Date: " + this.movieDate +"\n" + "Show Time: " + this.showTime +"\n" + "Number of Tickets: " + this.numberOfTickets +"\n" + "Amount Paid: $" + this.amountPaid + "\n" + "Credit Card (Last 4 Digits): " + "(XXXX)-" + this.creditCardNumber.substring(12, 16) + "\n" + "\n\n" + "NOTE: PLEASE NOTE DOWN THE CONFIRMATION NUMBER FOR FUTURE REFERENCES";
		
		return movieReservation;
		
	}


	public String getMovieDate() {
		return movieDate;
	}


	public void setMovieDate(String movieDate) {
		this.movieDate = movieDate;
	}


	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	public String getscreen() {
		return screen;
	}
	public void setscreen(String screen) {
		this.screen = screen;
	}
	public String getNumberOfTickets() {
		return numberOfTickets;
	}
	public void setNumberOfTickets(String numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getAmountPaid() {
		return amountPaid;
	}
	public void setAmountPaid(String amountPaid) {
		this.amountPaid = amountPaid;
	}
	public String getconfirmationNumber() {
		return confirmationNumber;
	}
	public void setconfirmationNumber(String confirmationNumber) {
		this.confirmationNumber = confirmationNumber;
	}


	public String toString1() {

		String movieReservation ="";
		movieReservation = movieReservation + " Movie Name: " + this.movieName +"\n" + "Show Date: " + this.movieDate +"\n" + "Show Time: " + this.showTime +"\n" + "Number of Tickets: " + this.numberOfTickets +"\n" + "Amount Paid: $" + this.amountPaid + "\n" + "Credit Card (Last 4 Digits): " + "(XXXX)-" + this.creditCardNumber.substring(12, 16) + "\n" + "\nReceipt Number: " + this.confirmationNumber +"\n";
		return movieReservation;
	}
	
	
	
	
	

}
