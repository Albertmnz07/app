package panels;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import interfaces.cleneable;
import objects.User;
import utils.FieldWithImage;
import utils.DBManager;

public class AddContactPanel extends JPanel implements cleneable {
	
	private FieldWithImage nameEntry, idEntry;
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
		nameEntry = new FieldWithImage("/images/guy.png" , "name" , false , null);
		add(nameEntry , config);
		
		config.gridy = 2;
		idEntry = new FieldWithImage("/images/id.png" , "id" , false , null);
		add(idEntry , config);
		
		config.gridy = 3;
		createButton = new JButton("Create");
		createButton.setFont(new Font("Ubuntu" , Font.BOLD , 15));
		createButton.setPreferredSize(new Dimension(130 , 35));
		createButton.addActionListener(e -> {
			if (DBManager.canAddContact(user , nameEntry.getText())){
				DBManager.addContact(user, nameEntry.getText());
			}
		});
		add(createButton , config);
		
		config.gridy = 4;
		errorLabel = new JLabel("");
		errorLabel.setFont(new Font("Ubuntu", Font.PLAIN, 20));
		
		config.gridy = 5;
		exitButton = new JButton("<-");
		exitButton.setFont(new Font("Ubuntu" , Font.BOLD , 15));
		exitButton.setPreferredSize(new Dimension(130 , 35));
		exitButton.addActionListener(e -> {
			clean();
			MainInterface.userPanel(user);
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
}