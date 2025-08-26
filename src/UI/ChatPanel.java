package UI;

import javax.swing.*;
import objects.User;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import controlers.DBManager;
import controlers.UIManager;

public class ChatPanel extends JPanel {
	
	private User user;
	private String contact;
	private Color toolBarColor = Color.decode("#12DCE0");
	
	public ChatPanel(User user , String contact) {
		this.user = user;
		this.contact = contact;
		
		setLayout(new BorderLayout());
		
		add(new toolBar() , BorderLayout.NORTH);
		
		add(new chat() , BorderLayout.CENTER);
		
	}
	
	private class toolBar extends JPanel{
		
		public toolBar() {
			
			setLayout(new BorderLayout());
			setBackground(toolBarColor);
			
			JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.CENTER , 5 , 0));
			leftPanel.setBackground(toolBarColor);
			
			JButton exitButton = new JButton("<-");
			exitButton.setBorderPainted(false);
			exitButton.setFont(getFont().deriveFont(18f));
			exitButton.setBackground(toolBarColor);
			exitButton.addActionListener(e -> UIManager.userMain(user));
			
			JLabel avatar = new JLabel(DBManager.getImage("chatGuy.png")); //put here the ImageIcon object
			
			JLabel contactName = new JLabel(contact);
			contactName.setFont(new Font("Ubuntu" , Font.BOLD , 20));
			
			leftPanel.add(exitButton); leftPanel.add(avatar); leftPanel.add(contactName);
			
			JButton optionsButton = new JButton(DBManager.getImage("points.png"));
			optionsButton.setBorderPainted(false);
			optionsButton.setBackground(toolBarColor);
				
			add(leftPanel , BorderLayout.WEST);
			add(optionsButton , BorderLayout.EAST);
			
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			
		}
		
	}
	
	private class chat extends JPanel{
		
		public chat() {
			
			setLayout(new BorderLayout());
			
			JPanel messagesPanel = new JPanel();
			
			JPanel sendMessagePanel = new JPanel(new BorderLayout());
			
			JTextField textField = new JTextField();
			textField.setFont(new Font("Ubuntu" , Font.PLAIN , 18));
			
			JButton sendButton = new JButton(DBManager.getImage("send.png"));
			
			sendMessagePanel.add(textField , BorderLayout.CENTER);
			sendMessagePanel.add(sendButton , BorderLayout.EAST);
			
			add(messagesPanel , BorderLayout.CENTER);
			add(sendMessagePanel , BorderLayout.SOUTH);
		}
	}

}
