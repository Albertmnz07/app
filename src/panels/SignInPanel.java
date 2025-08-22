package panels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;

import interfaces.cleneable;
import utilPanels.FieldWithImage;
import utils.DBManager;

public class SignInPanel extends JPanel implements cleneable {
	
	public SignInPanel() {
		instance = this;
		setBackground(Color.RED);
		setLayout(new GridBagLayout());
		GridBagConstraints config = new GridBagConstraints();
		config.fill = GridBagConstraints.NONE;
		config.anchor = GridBagConstraints.CENTER;
		config.gridx = 0;
		config.insets = new Insets(7 , 0 , 16 , 0);
		
		config.gridy = 0;
		JLabel titleLabel = new JLabel("<html> <div style='text-align:center;'>Create your<br>account</div><html/>");
		titleLabel.setFont(new Font("Ubuntu" , Font.PLAIN , 28));
		add(titleLabel , config);
		
		config.gridy = 1;
		userEntry = new FieldWithImage("/images/guy.png" , "username" , false , Color.RED);
		add(userEntry , config);
		
		config.gridy = 2;
		passwordEntry = new FieldWithImage("/images/key.png" , "password" , true , Color.RED);
		add(passwordEntry , config);
		
		config.gridy = 3;
		confirmPasswordEntry = new FieldWithImage("/images/loop.png" , "confirm password" , true , Color.RED);
		add(confirmPasswordEntry , config);
		
		config.gridy = 4;
		idLabel = new JLabel("Your id will be: " + DBManager.getNextId());
		idLabel.setFont(new Font("Ubuntu" , Font.PLAIN , 20));
		add(idLabel , config);
		
		config.gridy = 5;
		confirmButton = new JButton("Create account");
		confirmButton.setPreferredSize(new Dimension(150 , 40));
		confirmButton.setFont(new Font("Ubuntu" , Font.PLAIN , 15));
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!(userEntry.isPlaceHolder() || passwordEntry.isPlaceHolder() || confirmPasswordEntry.isPlaceHolder())) {
					if (DBManager.canCreateAccount(userEntry.getText(), passwordEntry.getText() , confirmPasswordEntry.getText())) {
						DBManager.createAccount(userEntry.getText(), passwordEntry.getText() , confirmPasswordEntry.getText());
						backStart();
					}
				} else {
					SignInError("Please, fill both gaps");
				}
			}
		});
		add(confirmButton , config);
		
		config.gridy = 6;
		errorLabel = new JLabel("error space");
		errorLabel.setFont(new Font("Ubuntu" , Font.PLAIN , 25));
		add(errorLabel , config);
		
		config.gridy = 7;
		config.insets = new Insets(22 , 0 , 8 , 0);
		JLabel infoLabel = new JLabel("*Your id will be on your profile");
		infoLabel.setFont(new Font("Ubuntu" , Font.PLAIN , 20));
		add(infoLabel , config);
		
		config.gridy = 8;
		config.insets = new Insets(7 , 0 , 3 , 0);
		goBackButton = new JButton("<-");
		goBackButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				backStart();
			}
		});
		add(goBackButton , config);
	}
	
	public static SignInPanel getInstance() {
		return instance;
	}
	
	public void SignInError(String error) {
		errorLabel.setText(error);
	}
	
	private void updateIdLabel() {
		idLabel.setText("Your id will be: " + DBManager.getNextId());
	}
	
	public void clean() {
		userEntry.cleanField();
		passwordEntry.cleanField();
		confirmPasswordEntry.cleanField();
	}
	
	private void backStart() {
		updateIdLabel();
		MainInterface.changePanel("Start" , instance);
	}
	
	private FieldWithImage userEntry , passwordEntry , confirmPasswordEntry;
	private JButton confirmButton , goBackButton;
	private JLabel errorLabel , idLabel;
	private static SignInPanel instance;

}
