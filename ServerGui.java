/*
//*************************************************************************
￼￼￼//*																		  *
//*   CIS611     Fall Session 2016 Program Assignment PA07   Harsha Bhadri*
//* 													                  *
//*																		  *
//*						  Program Assignment PA07						  *
//*													 					  *
//*                              Client-Server                            *
//*																		  *
//*																		  *
//*					    Date Created: 10 November 2016	     		      *
//*					     Saved in: HarshaBhadriPA07                       *
//*																		  *
//*************************************************************************
*/
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

class ServerGui extends JPanel{
	

	private static final long serialVersionUID = 1L;
	private JButton btnCloseButton;
	public JLabel clientLabel;
	public JTextArea txtDisplayResults;
	Server connection;
	
	public ServerGui(Server connection){
		
        this.connection = connection;
		initializeComponents();
		initializeLayout();
		
		btnCloseButton.addActionListener( new java.awt.event.ActionListener() {
			
	        public void actionPerformed(ActionEvent e){
	        	
	        	try {
					close_actionPerformed(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	        }
	    });	
	}
	
	private void initializeComponents(){
		
		txtDisplayResults = new JTextArea(30, 40);
		btnCloseButton = new JButton("Close");
		txtDisplayResults.setEditable(false);
	}

	
	private void initializeLayout() {
		
		JPanel displayPanel = new JPanel();
		clientLabel = new JLabel ("Client Request:");
		displayPanel.setLayout( new BorderLayout());
		displayPanel.add( clientLabel, BorderLayout.NORTH );
		displayPanel.add(txtDisplayResults, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout( new FlowLayout());
		buttonPanel.add(btnCloseButton);
		
		JScrollPane scroll = new JScrollPane ( txtDisplayResults );
	    scroll.setVerticalScrollBarPolicy ( ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS );
	    displayPanel.add(scroll);

		setLayout(new BorderLayout());
		add(displayPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

	}

	
	void close_actionPerformed(ActionEvent e) throws IOException{
		System.exit(0);	  
	}
}