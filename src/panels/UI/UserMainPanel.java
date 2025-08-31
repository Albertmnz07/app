package panels.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import controlers.UIManager;
import controlers.DBManager;
import objects.User;
import panels.utilPanels.ChatPreview;

public class UserMainPanel extends JPanel {
	
	private User user;
	private JButton addButton , configButton;
	private UserMainPanel instance;
	private Font popupFont = new Font("Ubuntu" , Font.PLAIN , 17);
	
	public UserMainPanel(User user) {
		 this.user = user;
		 this.instance = this;
		 setLayout(new BorderLayout());
		 
		 JScrollPane scrollChat = new JScrollPane(new ChatsPanel());
		 scrollChat.getVerticalScrollBar().setUnitIncrement(16);
		 scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		 
		 add(new ToolBar() , BorderLayout.NORTH);
		 add(scrollChat , BorderLayout.CENTER);
		 
		 
	}
	
	
	private class ToolBar extends JPanel{
		
		public ToolBar() {
			setLayout(new BorderLayout());
			
			JPopupMenu addMenu = new JPopupMenu();
			
			JMenuItem addContact = new JMenuItem("New contact");
			addContact.addActionListener(e -> UIManager.addContact(user));
			addContact.setFont(popupFont);
			JMenuItem addGroup = new JMenuItem("New group");
			addGroup.setFont(popupFont);
			
			addMenu.add(addContact);
			addMenu.add(addGroup);
			
			 addButton = new JButton();
			 addButton.setBackground(Color.decode("#07D1F5"));
			 addButton.setIcon(DBManager.getImage("add.png"));
			 addButton.addActionListener(e -> addMenu.show(addButton, -110, 0));
			 add(addButton , BorderLayout.WEST);
			
			 JPanel centerPanel = new JPanel();
			 centerPanel.setBackground(Color.decode("#0AA0F5"));
			 centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 0 , 0));
			 JLabel chatLabel = new JLabel("Chats");
			 chatLabel.setFont(new Font("Ubuntu" , Font.BOLD , 16));
			 centerPanel.add(chatLabel);
			 add(centerPanel , BorderLayout.CENTER);
			 
			 JPopupMenu configMenu = new JPopupMenu();
			 
			 JMenuItem logOut = new JMenuItem("Log out");
			 logOut.addActionListener(e -> UIManager.start());
			 logOut.setFont(popupFont);
			 JMenuItem settings = new JMenuItem("Settings");
			 settings.setFont(popupFont);
			 
			 configMenu.add(logOut);
			 configMenu.add(settings);
			 
			 configButton = new JButton();
			 configButton.setBackground(Color.decode("#07D1F5"));
			 configButton.setIcon(DBManager.getImage("userConfig.png"));
			 configButton.addActionListener(e -> configMenu.show(configButton, 60, 0));
			 add(configButton , BorderLayout.EAST);
			 
		}
	}
	
	private class ChatsPanel extends JPanel{
		
		public ChatsPanel() {
			
			setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
			
			for (String contact: DBManager.getContactNamesList(user)) {
				ChatPreview chat = new ChatPreview(user , contact);
				chat.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						UIManager.chat(user, contact);
					}
				});
				add(chat);
			}
			
		}
	}
	
	public UserMainPanel getInstance() {
		return instance;
	}

}



