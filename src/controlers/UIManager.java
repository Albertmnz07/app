package controlers;


import java.awt.Dimension;
import javax.swing.JPanel;
import Main.MainInterface;
import objects.User;
import panels.UI.AddContactPanel;
import panels.UI.ChatPanel;
import panels.UI.SignInPanel;
import panels.UI.StartPanel;
import panels.UI.UserMainPanel;

public class UIManager {
	
	private static MainInterface main;
	
	
	public static MainInterface getMainInterface() {
		return main == null ? MainInterface.getInstance() : main;
	}
	
	public static void changePanel(JPanel panel) {
		main = getMainInterface();
		main.getContentPane().removeAll();
		main.getContentPane().add(panel);
		main.revalidate();
		main.repaint();
	}
	
	public static void setSize(int x , int y) {
		getMainInterface().setSize(new Dimension(x,y));
	}
	
	public static void start() {
		changePanel(new StartPanel());
		setSize(400 , 550);
	}
	
	public static void signIn() {
		changePanel(new SignInPanel());
		setSize(400 , 650);
	}
	
	public static void userMain(User user) {
		changePanel(new UserMainPanel(user));
	}
	
	public static void addContact(User user) {
		changePanel(new AddContactPanel(user));
	}
	
	public static void chat(User user , String contact) {
		changePanel(new ChatPanel(user , contact));
	}

}
