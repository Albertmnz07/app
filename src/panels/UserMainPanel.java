package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.*;
import objects.User;

public class UserMainPanel extends JPanel {
	
	private User user;
	private JButton addButton , configButton;
	
	public UserMainPanel(User user) {
		 this.user = user;
		 setLayout(new BorderLayout());
		 
		 add(new ToolBar() , BorderLayout.NORTH);
		 add(new ChatPanel() , BorderLayout.CENTER);
		 
		 
	}
	
	
	private class ToolBar extends JPanel{
		
		public ToolBar() {
			setLayout(new BorderLayout());
			
			JPopupMenu addMenu = new JPopupMenu();
			
			JMenuItem addContact = new JMenuItem("New contact");
			addContact.addActionListener(e -> MainInterface.addContactPanel(user));
			JMenuItem addGroup = new JMenuItem("New group");
			
			addMenu.add(addContact);
			addMenu.add(addGroup);
			
			 addButton = new JButton();
			 addButton.setIcon(new ImageIcon(getClass().getResource("/images/add.png")));
			 addButton.addActionListener(e -> addMenu.show(addButton, -100, 0));
			 add(addButton , BorderLayout.WEST);
			
			 JPanel centerPanel = new JPanel();
			 centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 0 , 0));
			 centerPanel.add(new JLabel("Chats"));
			 add(centerPanel , BorderLayout.CENTER);
			 
			 configButton = new JButton();
			 configButton.setIcon(new ImageIcon(getClass().getResource("/images/userConfig.png")));
			 add(configButton , BorderLayout.EAST);
			 
		}
	}
	
	private class ChatPanel extends JPanel{
		
		public ChatPanel() {
			
		}
	}


}



