package utils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.JTextComponent;


public class FieldWithImage extends JPanel {
	
	public FieldWithImage(String rute , String text , boolean password , Color bg) {
		this.text = text;
		this.password = password;
		
		setBackground(bg);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200 , 30));
		
		ImageIcon icon = new ImageIcon(getClass().getResource(rute));
		JLabel imageLabel = new JLabel(icon);
		imageLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		imageLabel.addMouseListener(new FocusImage());
		add(imageLabel , BorderLayout.WEST);
		
		if (password) {
			entry = new JPasswordField(text);
			((JPasswordField) entry).setEchoChar((char) 0);
			showPassword = new JCheckBox("Show");
			showPassword.setOpaque(false);
			showPassword.addActionListener(new ShowPassword());
			setPreferredSize(new Dimension(200 , 55));
			add(showPassword , BorderLayout.SOUTH);
		} else {
			entry = new JTextField(text);
		}
		
		entry.setFont(holderFont);
		entry.setForeground(Color.GRAY);
		entry.addFocusListener(new FieldFocus());
		add(entry , BorderLayout.CENTER);
		
	}
	
	public String getText() {
		return placeHolder ? "" : entry.getText();
	}
	
	public boolean isPlaceHolder() {
		return placeHolder;
	}
	
	public void cleanField() {
		enablePlaceHolder();
		if (password) {
			showPassword.setSelected(false);
		}
	}
	
	private void enablePlaceHolder(){
		placeHolder = true;
		entry.setFont(holderFont);
		entry.setForeground(Color.GRAY);
		entry.setText(text);
		updateEchoChar();
	}
	
	private void disablePlaceHolder() {
		entry.setText("");
		placeHolder = false;
		entry.setFont(normalFont);
		entry.setForeground(Color.BLACK);
		updateEchoChar();
	}
	
	private class FieldFocus implements FocusListener{

		public void focusGained(FocusEvent e) {
			if (placeHolder) {
				disablePlaceHolder();
			}
		}

		public void focusLost(FocusEvent e) {
			if (entry.getText().isEmpty()) {
				enablePlaceHolder();
			}
		}	
	}
	
	private class FocusImage extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			entry.requestFocus();
		}
	}
	
	// ((JPasswordField) entry).setEchoChar((char) 0);
	private class ShowPassword implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			updateEchoChar();
		}
	}
	
	private void updateEchoChar() { //called to determinate the echoChar depending the situation
		if (!(entry instanceof JPasswordField)) return;
		
		if (placeHolder) {
			((JPasswordField) entry).setEchoChar((char) 0);
		} else {
			if (showPassword.isSelected()) {
				((JPasswordField) entry).setEchoChar((char) 0);
			} else {
				((JPasswordField) entry).setEchoChar('*');
			}
		}
	}
	
	private String text;
	private JTextComponent entry;
	private boolean placeHolder = true;
	private final Font normalFont = new Font("Ubuntu" , Font.PLAIN , 20);
	private Font holderFont = normalFont.deriveFont(Font.ITALIC , 18f);
	private JCheckBox showPassword;
	private boolean password;
}

