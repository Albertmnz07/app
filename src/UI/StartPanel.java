package UI;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import controlers.UIManager;
import controlers.DBManager;
import interfaces.cleneable;
import utilPanels.FieldWithImage;

public class StartPanel extends JPanel implements cleneable {
	

	public StartPanel() {
		instance = this;
		setLayout(new GridBagLayout());
		setBackground(Color.CYAN);
		
		GridBagConstraints config = new GridBagConstraints();
		config.fill = GridBagConstraints.NONE;
		config.anchor = GridBagConstraints.CENTER;
		config.gridx = 0;
		config.insets = new Insets(5,0,10,0);
		//grid configuration
		
		config.gridy = 0;
		add(createLabel("<html> <div style='text-align:center;'> Welcome to<br>Ilom Chat</div><html/>") , config);
		
		//------------------FIELDS------------------------------------------------------
		config.gridy = 1;
		userField = new FieldWithImage("guy.png" , "username" , false , Color.CYAN);
		add(userField , config);
		
		config.gridy = 2;
		passwordField = new FieldWithImage("key.png" , "password" , true , Color.CYAN);
		add(passwordField , config);
		
		//--------------------Log in------------------------------------------------------------
		config.gridy = 3;
		loginButton = new JButton("Log in");
		loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		loginButton.setPreferredSize(new Dimension(150 , 35));
		loginButton.setFont(new Font("Ubuntu" , Font.BOLD , 15));
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (userField.isPlaceHolder() || passwordField.isPlaceHolder()) {
					startError("Please, fill both gaps"); return;
				}
				DBManager.tryLogIn(userField.getText() , passwordField.getText());
			}
		});
		add(loginButton , config);
		
		config.gridy = 4;
		errorLabel = new JLabel("");
		errorLabel.setFont(font);
		add(errorLabel , config);
		
		//----------------------Sign up------------------------------------------------------------
		config.gridy = 5; 
		config.insets = new Insets(20, 0, 10, 0);
		add(createLabel("Haven't account?") , config);
		
		config.gridy = 6;
		config.insets = new Insets(10, 0, 20, 0);
		signInButton = new JButton("Sign In");
		signInButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
		signInButton.setPreferredSize(new Dimension(150 , 35));
		signInButton.setFont(new Font("Ubuntu" , Font.BOLD , 15));
		
		signInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.signIn();
			}
		});
		
		add(signInButton , config);
		
		config.gridy = 7;
		config.insets = new Insets(30 , 0 , 10 , 0);
		add(createLabel("Developed by Alberto") , config);

		
		
		JLabel dummy = new JLabel(); //dummy to get the focus, so that any object get the focus
		dummy.setFocusable(true);
		SwingUtilities.invokeLater(() -> dummy.requestFocusInWindow());
		add(dummy , config);
		//fix problems with focus

	}
	
	public static StartPanel getInstance() {
		return instance;
	}
	
	private JLabel createLabel(String text) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setForeground(Color.BLACK);
		return label;
	}
	
	public void startError(String error) {
		errorLabel.setText(error);
	}
	
	public void clean() {
		userField.cleanField();
		passwordField.cleanField();
	}
	
	private static Font font = new Font("Ubuntu" , Font.PLAIN , 25);
	private FieldWithImage userField , passwordField;
	private JButton loginButton , signInButton;
	private JLabel errorLabel;
	private static StartPanel instance; //to send the instance, so that update the error from other classes

}
