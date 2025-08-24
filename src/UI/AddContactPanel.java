package UI;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import controlers.UIManager;
import controlers.DBManager;
import interfaces.cleneable;
import objects.User;
import utilPanels.FieldWithImage;

public class AddContactPanel extends JPanel implements cleneable {
	
	private FieldWithImage nameEntry, idEntry;
	@SuppressWarnings("unused")
	private User user;
	private JButton createButton , exitButton;
	private JLabel errorLabel;
	private static AddContactPanel instance;
	
	public AddContactPanel(User user) {
		this.user = user;
		instance = this;
		setLayout(new GridBagLayout()); // Set the layout to GridBagLayout
		GridBagConstraints config = new GridBagConstraints(); // Create GridBagConstraints for layout configuration
		config.fill = GridBagConstraints.NONE;
		config.anchor = GridBagConstraints.CENTER;
		config.gridx = 0;
		config.insets = new Insets(7, 0, 16, 0); // Set default insets for spacing
		
		config.gridy = 0;
		JLabel titleLabel = new JLabel("New contact");
		titleLabel.setFont(new Font("Ubuntu", Font.PLAIN, 25));
		add(titleLabel, config);
		
		config.gridy = 1;
		nameEntry = new FieldWithImage("guy.png" , "name" , false , null);
		add(nameEntry , config);
		
		config.gridy = 2;
		idEntry = new FieldWithImage("id.png" , "id" , false , null);
		idEntry.setDocumentListener(new checkIdFormat());
		add(idEntry , config);
		
		config.gridy = 3;
		createButton = new JButton("Create");
		createButton.setFont(new Font("Ubuntu" , Font.BOLD , 15));
		createButton.setPreferredSize(new Dimension(130 , 35));
		createButton.addActionListener(e -> {
			if (!(nameEntry.isPlaceHolder() || idEntry.isPlaceHolder())) {
				if (DBManager.canAddContact(user , nameEntry.getText() , Integer.valueOf(idEntry.getText()))){
				DBManager.addContact(user, nameEntry.getText() , Integer.valueOf(idEntry.getText()));
				UIManager.userMain(user);
				}
			} else {
				errorLabel.setText("Please, fill both gaps");
			}
			
		});
		add(createButton , config);
		
		config.gridy = 4;
		errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Ubuntu", Font.PLAIN, 20));
		add(errorLabel , config);
		
		config.gridy = 5;
		exitButton = new JButton("<-");
		exitButton.setFont(new Font("Ubuntu" , Font.BOLD , 15));
		exitButton.setPreferredSize(new Dimension(130 , 35));
		exitButton.addActionListener(e -> {
			clean();
			UIManager.userMain(user);
		});
		add(exitButton , config);
		
	}
	
	public static AddContactPanel getInstance() {
		return instance;
	}
	
	public void addContactError(String error) {
		errorLabel.setText(error);
	}
	
	public void clean() {
		nameEntry.cleanField();
		idEntry.cleanField();
	}
	
	private class checkIdFormat implements DocumentListener{
		
		String previusText = "";
		String changed = "";
		
		public void insertUpdate(DocumentEvent e) {
			
			Document info = e.getDocument();
			int offset = e.getOffset();
			int length = e.getLength();
			try {
				changed = info.getText(offset, length);
			} catch (BadLocationException e1) {
				System.out.println("error");
			}
			
			for (int i = 0 ; i < changed.length() ; i++) {
				Character c = changed.charAt(i);
				if (!(Character.isDigit(c))) {
					errorLabel.setText("Please set a number in id");
					SwingUtilities.invokeLater(() -> idEntry.setText(previusText));
					return;
				}
			}
			
			previusText = idEntry.getText();
			
		}

		public void removeUpdate(DocumentEvent e) {
			
		}

		public void changedUpdate(DocumentEvent e) {
			
		}
		
	}
}