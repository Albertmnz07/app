package panels.utilPanels;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import controlers.DBManager;


public class FieldWithImage extends JPanel {
	
	public FieldWithImage(String rute , String text , boolean password , Color bg) {
		this.text = text;
		this.password = password;
		
		setBackground(bg);
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(200 , 30));
		
		JLabel imageLabel = new JLabel(DBManager.getImage(rute));
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
	
	public void setText(String text) {
		entry.setText(text);
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
	
	public void setDocumentListener(DocumentListener d) {
		Document doc = entry.getDocument();
		doc.addDocumentListener(d);
	}
	
	private String text;
	private JTextComponent entry;
	private boolean placeHolder = true;
	private final Font normalFont = new Font("Ubuntu" , Font.PLAIN , 20);
	private Font holderFont = normalFont.deriveFont(Font.ITALIC , 18f);
	private JCheckBox showPassword;
	private boolean password;
}

