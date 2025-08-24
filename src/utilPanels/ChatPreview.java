package utilPanels;

import objects.User;

import javax.swing.*;

import controlers.DBManager;
import java.awt.Point;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

public class ChatPreview extends JPanel {
	
	@SuppressWarnings("unused")
	private User user; 
	private Font font = new Font("Ubuntu" , Font.BOLD , 20);
	
	public ChatPreview(User user , String contact) {
		this.user = user;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		setCursor(toolkit.createCustomCursor(DBManager.getImage("hearth.png").getImage(), new Point(0,0), "hearth"));
		setBackground(Color.cyan);
		
		setLayout(new GridBagLayout());
		GridBagConstraints config = new GridBagConstraints();
		config.fill = GridBagConstraints.NONE;
		config.anchor = GridBagConstraints.WEST;
		config.gridy = 0;
		config.gridx = 0;
		config.insets = new Insets(0,10,0,0);
		
		JLabel imageLabel = new JLabel(DBManager.getImage("bigGuy.png"));
		add(imageLabel , config);
		
		config.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		rightPanel.setBackground(Color.cyan);
		
		JLabel nameLabel = new JLabel(contact);
		nameLabel.setFont(font);
		rightPanel.add(nameLabel , BorderLayout.NORTH);
		
		JPanel downPanel = new JPanel(new BorderLayout());
		downPanel.setBackground(Color.cyan);
		
		JLabel message = new JLabel(DBManager.getLastMessage(user, contact).getText());
		message.setFont(font);
		
		JLabel hourLabel = new JLabel(DBManager.getLastMessage(user, contact).getCompleteHour());
		hourLabel.setFont(font.deriveFont(14f));
		
		downPanel.add(message , BorderLayout.WEST);
		downPanel.add(hourLabel , BorderLayout.EAST);
		
		rightPanel.add(downPanel , BorderLayout.SOUTH);
		
		config.gridx = 1;
		config.weightx = 1.0;
		config.fill = GridBagConstraints.HORIZONTAL;
		add(rightPanel , config);
		
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		setPreferredSize(new Dimension(400, 80));
		setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

	}

}
