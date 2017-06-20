import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Message implements Serializable{
	
	String username;
	String password;
	String fullName;
	String emailAddress;
	String mailAddress;
	int phoneNumber;
	String userType;
	int operationType;
	
	String movieName;
	String movieReview;
	String movieRating;
	
	String screen;
	String pay;
	String firstShow;
	String secondShow;
	String thirdShow;
	String fourthShow;
	String date;
	
	String status;
	String errorMessage;
	
	ArrayList<String> movieArray;
	ArrayList<movieReservation> arrayMovieReservation;
	
	private String ticketIdNumber;
	private movieReservation movieReservation;
	
	public Message(String movieName,String date,String screen,String pay,String firstShow,String secondShow,String thirdShow,String fourthShow,String userType,int optype){
		this.movieName=movieName;
		this.date=date;
		this.screen=screen;
		this.pay=pay;
		this.firstShow=firstShow;
		this.secondShow=secondShow;
		this.thirdShow=thirdShow;
		this.fourthShow=fourthShow;
		this.userType=userType;
		this.operationType=optype;
	}
	
	public Message(String movieName,String date,String userType,int opType){
		this.movieName=movieName;
		this.date=date;
		this.userType=userType;
		this.operationType=opType;
	}
	
	public Message(String movieName,String movieReview,String movieRating,String userType,int optype){
		this.movieName=movieName;
		this.movieReview=movieReview;
		this.movieRating=movieRating;
		this.userType=userType;
		this.operationType=optype;
	}
	
	public Message(String username,String password,String fullName,String emailAddress,String mailAddress,int phoneNumber,String userType,int optype){
		this.username=username;
		this.password=password;
		this.fullName=fullName;
		this.emailAddress=emailAddress;
		this.mailAddress=mailAddress;
		this.phoneNumber=phoneNumber;
		this.userType=userType;
		this.operationType=optype;
	}
	
	public Message(String username,String password,String customerType) {
		this.username=username;
		this.password=password;
		this.userType=customerType;
	}
	
	public String getticketIdNumber() {
		return ticketIdNumber;
	}

	public void setticketIdNumber(String ticketIdNumber) {
		this.ticketIdNumber = ticketIdNumber;
	}

	private ArrayList<showTime> showList;
	public ArrayList<movieReservation> getarrayMovieReservation() {
		return arrayMovieReservation;
	}

	public void setarrayMovieReservation(ArrayList<movieReservation> arrayMovieReservation) {
		this.arrayMovieReservation = arrayMovieReservation;
	}

	public movieReservation getmovieReservation() {
		return movieReservation;
	}
	
	public void setmovieReservation(movieReservation movieReservation) {
		this.movieReservation = movieReservation;
	}

	public ArrayList<showTime> getShowList() {
		return showList;
	}
	
	public void setShowList(ArrayList<showTime> showList) {
		this.showList = showList;
	}

	public String getdate() {
		return date;
	}

	public void setdate(String date) {
		this.date = date;
	}

	public String getscreen() {
		return screen;
	}

	public void setscreen(String screen) {
		this.screen = screen;
	}

	public String getpay() {
		return pay;
	}

	public void setpay(String pay) {
		this.pay = pay;
	}

	public String getfirstShow() {
		return firstShow;
	}

	public void setfirstShow(String firstShow) {
		this.firstShow = firstShow;
	}

	public String getsecondShow() {
		return secondShow;
	}

	public void setsecondShow(String secondShow) {
		this.secondShow = secondShow;
	}

	public String getthirdShow() {
		return thirdShow;
	}

	public void setthirdShow(String thirdShow) {
		this.thirdShow = thirdShow;
	}

	public String getfourthShow() {
		return fourthShow;
	}

	public void setfourthShow(String fourthShow) {
		this.fourthShow = fourthShow;
	}
	
	public ArrayList<String> getmovieArray() {
		return movieArray;
	}


	public void setmovieArray(ArrayList<String> movieArray) {
		this.movieArray = movieArray;
	}

	public String getMovieName() {
		return movieName;
	}


	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}


	public String getMovieReview() {
		return movieReview;
	}


	public void setMovieReview(String movieReview) {
		this.movieReview = movieReview;
	}


	public String getmovieRating() {
		return movieRating;
	}


	public void setmovieRating(String movieRating) {
		this.movieRating = movieRating;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public int getOperationType() {
		return operationType;
	}

	public void setOperationType(int operationType) {
		this.operationType = operationType;
	}
	
	public Message(int opType) {
		this.operationType=opType;
	}

}
