package panels.UI;

import javax.swing.*;
import Main.MainInterface;
import objects.Message;
import objects.User;
import panels.utilPanels.MessageView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import controlers.DBManager;
import controlers.UIManager;

public class ChatPanel extends JPanel {
	
	private User user;
	private String contact;
	private Color toolBarColor = Color.decode("#12DCE0");
	private JPanel messagesPanel;
	private GridBagConstraints config;
	private JScrollPane scrollChat;
	private JScrollBar scrollBar;
	private JButton sendButton;
	private JTextField textField;
	
	public ChatPanel(User user , String contact) {
		this.user = user;
		this.contact = contact;
		
		setLayout(new BorderLayout());
		
		scrollChat = new JScrollPane(new Chat());
		scrollChat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollChat.getVerticalScrollBar().setUnitIncrement(16);
		scrollBar = scrollChat.getVerticalScrollBar();
		SwingUtilities.invokeLater(() -> scrollBar.setValue(scrollBar.getMaximum()));
		
		add(new ToolBar() , BorderLayout.NORTH);
		
		add(scrollChat , BorderLayout.CENTER);
		
		add(new SendMessage() , BorderLayout.SOUTH);
		
		addKeyListener(new SendKey());
		
	}
	
	private class ToolBar extends JPanel{
		
		public ToolBar() {
			
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
	
	private class Chat extends JPanel{
		
		public Chat() {
			
			setLayout(new BorderLayout());
			
			messagesPanel = new JPanel();
			messagesPanel.setLayout(new GridBagLayout());

			config = new GridBagConstraints();
			config.gridx = 0;
			config.weightx = 1.0;
			config.fill = GridBagConstraints.NONE; 
			config.insets = new Insets(5, 5, 5, 5);
			config.gridy = 0;

			loadMessages();
			
			add(messagesPanel , BorderLayout.CENTER);
		}
	}
	
	private class SendMessage extends JPanel{
		
		public SendMessage() {
			
			setLayout(new BorderLayout());
			
			textField = new JTextField();
			textField.setFont(new Font("Ubuntu" , Font.PLAIN , 18));
			textField.addActionListener(e -> addMessage(textField.getText()));
			
			sendButton = new JButton(DBManager.getImage("send.png"));
			sendButton.addActionListener(e -> addMessage(textField.getText())
			);
			
			add(textField , BorderLayout.CENTER);
			add(sendButton , BorderLayout.EAST);
			
		}
	}
	
	
	private void loadMessages() {
		for (Message message : DBManager.getMessages(user, contact)) {
			MessageView messageView = new MessageView(message);
			
			if (message.getSender().equals("ME")) {
		        config.anchor = GridBagConstraints.LINE_START;
		    } else {
		        config.anchor = GridBagConstraints.LINE_END; 
		    }
		    
		    messagesPanel.add(messageView, config);
		    config.gridy++;
		    config.anchor = GridBagConstraints.LINE_START;
		}
	}
	
	private void addMessage(String text) { //ELIMINAR ESPACIOS DE LOS MENSAJES
		
		if (!(textField.getText().equals(""))) {
			
			DBManager.sendMessage(user, contact, textField.getText().strip());
			textField.setText("");
			textField.requestFocus();
			
		} else {
			return;
		}
		
		Message message = Message.createMessage("ME" , text);
		MessageView messageView = new MessageView(message);
		
		if (message.getSender().equals("ME")) {
	        config.anchor = GridBagConstraints.LINE_START;
	    } else {
	        config.anchor = GridBagConstraints.LINE_END; 
	    }
	    
	    messagesPanel.add(messageView, config);
	    config.gridy++; 
	    
	    MainInterface.getInstance().revalidate();
		MainInterface.getInstance().repaint();
		scrollBar.setValue(scrollBar.getMaximum());
		
	}
	
	private class SendKey extends KeyAdapter{
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				addMessage(textField.getText());
			}
		}
	}

}
