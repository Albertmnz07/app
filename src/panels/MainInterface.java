package panels;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.*;

import interfaces.cleneable;
import objects.User;
import utils.DBManager;

public class MainInterface extends JFrame {
	
	public MainInterface() {
		instance = this;
		setTitle("Ilom Chat");
		setBounds(1300 , 300 , 400 , 550);
		
		panels = new CardLayout();
		mainPanel = new JPanel(panels);
		
		mainPanel.add(new StartPanel() , "Start");
		mainPanel.add(new SignInPanel() , "SignIn");
//		mainPanel.add(new UserMainPanel() , "UserMainPanel");
		
		panels.show(mainPanel, "Start");
		
		add(mainPanel);
		
		setVisible(true);
	}
	
	public static void changePanel(String name , Object panel) { //function to change panels
		
		switch(name) {
		case "start": instance.setSize(new Dimension(400 , 550));
		case "SignIn": instance.setSize(new Dimension(400 , 600));
		}
		
		if (panel instanceof cleneable) {
			((cleneable) panel).clean();
		}
		
		panels.show(mainPanel, name);
	}
	
	public static void userPanel(User user) {
		mainPanel.add(new UserMainPanel(user) , "user");
		panels.show(mainPanel, "user");
	}
	
	public static void addContactPanel(User user) {
		mainPanel.add(new AddContactPanel(user) , "addContact");
		panels.show(mainPanel, "addContact");
	}
	
	public static void main(String[] args) {
		DBManager.checkDB(); //check the existence of data base
		MainInterface app = new MainInterface();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static CardLayout panels;
	private static JPanel mainPanel;
	private static MainInterface instance;
}
