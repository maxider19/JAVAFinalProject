import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class DbaGUI extends JFrame {
	
	private JLabel movieLabel = new JLabel("Enter Movie Title:");
	private JLabel movRating = new JLabel("Rating:");
	private JLabel movTime = new JLabel("Time:");
	private JLabel movScreen = new JLabel("Screen:");
	
	private JTextField movieField = new JTextField();
	
	private JComboBox movieRating;
	private JComboBox movieTime;
	private JComboBox movieScreen;
	
	private JButton addMovie = new JButton("Add Movie");
	private JButton deleteMovie = new JButton("Delete Movie");
	private JButton backButton = new JButton("Back");
	
	static String Name;
	static int Rating;
	static int Time;
	static int Screen;
	Client conn;
	
	public DbaGUI(final Client conn){

		String[] rating = new String[5];
		for(int i = 0; i<5; i++ ){
			rating[i] = Integer.toString(i + 1);
		}
		movieRating = new JComboBox(rating);
		
		String[] time = new String[6];
		for(int i = 0; i<5; i++){
			time[i] = Integer.toString(10 + 3*i);
		}
		movieTime = new JComboBox(time);
		
		String[] screen = new String[5];
		for(int i =0; i<5; i++){
			screen[i] = Integer.toString(i+1);
		}
		movieScreen = new JComboBox(screen);
		
		JPanel top = new JPanel(new GridLayout(1,1));
		top.add(movieLabel);
		top.add(movieField);
		add(top, BorderLayout.NORTH);
		
		JPanel center = new JPanel(new GridLayout(3,3));
		center.add(movRating);
		center.add(movieRating);
		center.add(movTime);
		center.add(movieTime);
		center.add(movScreen);
		center.add(movieScreen);
		add(center, BorderLayout.CENTER);
		
		JPanel bottom = new JPanel(new GridLayout(1,3));
		bottom.add(addMovie);
		bottom.add(deleteMovie);
		bottom.add(backButton);
		add(bottom, BorderLayout.SOUTH);
		
		addMovie.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		try {//calling DBA server function add()
	    			Name = movieField.getText().trim();
	    			Rating = (Integer) movieRating.getSelectedItem();
	    			Time = (Integer) movieTime.getSelectedItem();
	    			Screen = (Integer) movieScreen.getSelectedItem();
	    			//checking if any text field is left blank
	    			if(movieField.getText().equals("") || movieRating.getSelectedItem().equals("") || movieTime.getSelectedItem().equals("") || movieScreen.getSelectedItem().equals("")){
	    				 JOptionPane.showMessageDialog(null, "Please fill all the required info!");
	    				 return;
	    			 }//end of if
	    			//server.add();
	    		} catch (Exception e1) {
	    			// TODO Auto-generated catch block
	    			JOptionPane.showMessageDialog(null, "Cannot add movie! Enter valid info.");
	    		}
	    	}
	    });
		
		backButton.addActionListener( new java.awt.event.ActionListener() {
	    	public void actionPerformed(ActionEvent e){
	    		conn.userGui = new initialGUI(conn);
	    		conn.userGui.pack();
	    		conn.userGui.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    		conn.userGui.setLocationRelativeTo(null);
	    		conn.userGui.setVisible(true);
	    	}
	    });
	}
	
	/*
	public static void main(String[] args) {
		
	    DbaGUI frame = new DbaGUI();   
	    frame.setTitle("Register");
	    //frame.setSize(450, 150);
	    frame.setLocationRelativeTo(null); // Center the frame
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
	}
	*/

}
