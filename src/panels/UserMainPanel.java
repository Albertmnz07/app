package panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.*;
import objects.User;
import utilPanels.ChatPreview;
import utils.DBManager;

public class UserMainPanel extends JPanel {
	
	private User user;
	private JButton addButton , configButton;
	
	public UserMainPanel(User user) {
		 this.user = user;
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
			addContact.addActionListener(e -> MainInterface.addContactPanel(user));
//			addContact.setPreferredSize(new Dimension(200 , 50));
			addContact.setFont(getFont().deriveFont(14f));
			JMenuItem addGroup = new JMenuItem("New group");
			addGroup.setFont(getFont().deriveFont(14f));
			
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
	
	private class ChatsPanel extends JPanel{
		
		public ChatsPanel() {
			
			setLayout(new BoxLayout(this , BoxLayout.Y_AXIS));
			
			for (String contact: DBManager.getContactList(user)) {
				add(new ChatPreview(user , contact));
			}
			
		}
	}


}



