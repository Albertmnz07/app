package panels.utilPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

import Main.MainInterface;
import objects.Message;


public class MessageView extends JPanel{
	
	public MessageView(Message message) {
		
		setLayout(new BorderLayout());
		
		JLabel text = new JLabel(message.getText());
		text.setFont(new Font("Ubuntu" , Font.PLAIN , 15));
		add(text , BorderLayout.WEST);
		
		JLabel hour = new JLabel(message.getCompleteHour());
		hour.setFont(new Font("Ubuntu" , Font.PLAIN , 12));
		add(hour , BorderLayout.EAST);
		
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		int width = (int) (MainInterface.getInstance().getWidth() * 0.75);
		int height = (int) (MainInterface.getInstance().getHeight() * 0.1);
		setPreferredSize(new Dimension(width , height));
		setMaximumSize(new Dimension(width , height));
		
		// ADD LISTENER FOR RESIZING

		
	}

}
