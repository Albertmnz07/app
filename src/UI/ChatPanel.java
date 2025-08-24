package UI;

import javax.swing.*;
import objects.User;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import controlers.DBManager;
import controlers.UIManager;

public class ChatPanel extends JPanel {
	
	private User user;
	private String contact;
	
	public ChatPanel(User user , String contact) {
		this.user = user;
		this.contact = contact;
		
		setLayout(new BorderLayout());
		
		add(new toolBar() , BorderLayout.NORTH);
		
		add(new chat() , BorderLayout.SOUTH);
		
	}
	
	private class toolBar extends JPanel{
		
		public toolBar() {
			
			setLayout(new BorderLayout());
			
			JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER , 5 , 0));
			
			JButton exitButton = new JButton("<-");
			exitButton.addActionListener(e -> UIManager.userMain(user));
			
			JLabel avatar = new JLabel(DBManager.getImage("guy.png")); //put here the ImageIcon object
			
			JLabel contactName = new JLabel(contact);
			
			leftPanel.add(exitButton); leftPanel.add(avatar); leftPanel.add(contactName);
			
			JButton optionsButton = new JButton(DBManager.getImage("points.png"));
			
			add(leftPanel , BorderLayout.WEST);
			add(optionsButton , BorderLayout.EAST);
		}
		
	}
	
	private class chat extends JPanel{
		
		public chat() {
			add(new JLabel("nigga"));
		}
	}

}
