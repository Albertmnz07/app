package Main;

import javax.swing.*;
import controlers.DBManager;
import panels.UI.StartPanel;

public class MainInterface extends JFrame {
	
	private static MainInterface main;
	
	public MainInterface() {
		setTitle("Ilom Chat");
		setBounds(1300 , 300 , 400 , 550);
		
		add(new StartPanel());
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		DBManager.checkDB(); //check the existence of data bases
		main = new MainInterface();
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static MainInterface getInstance() {
		return main;
	}


}
