package utilPanels;

import objects.User;
import utils.DBManager;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ChatPreview extends JPanel {
	
	@SuppressWarnings("unused")
	private User user; 
	private Font font = new Font("Ubuntu" , Font.BOLD , 20);
	
	public ChatPreview(User user , String contact) {
		this.user = user;
		
		setLayout(new GridBagLayout());
		GridBagConstraints config = new GridBagConstraints();
		config.fill = GridBagConstraints.NONE;
		config.anchor = GridBagConstraints.WEST;
		config.gridy = 0;
		config.gridx = 0;
		config.insets = new Insets(0,10,0,0);
		
		JLabel imageLabel = new JLabel(new ImageIcon(getClass().getResource("/images/bigGuy.png")));
		add(imageLabel , config);
		
		config.fill = GridBagConstraints.HORIZONTAL;
		
		JPanel rightPanel = new JPanel(new BorderLayout());
		
		JLabel nameLabel = new JLabel(contact);
		nameLabel.setFont(font);
		rightPanel.add(nameLabel , BorderLayout.NORTH);
		
		JPanel downPanel = new JPanel(new BorderLayout());
		
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
