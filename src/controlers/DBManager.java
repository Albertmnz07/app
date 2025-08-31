package controlers;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import objects.Message;
import objects.User;
import panels.UI.AddContactPanel;
import panels.UI.SignInPanel;
import panels.UI.StartPanel;
import java.awt.Image;
import java.io.File;

public class DBManager {
	
	private static Path db = Paths.get("db");
	private static Path userPath = Paths.get("db" , "user");
	private static Path adminPath = Paths.get("db" , "admin");
	private static Path nextId = Paths.get("db" , "nextId.txt");
	
	public static void checkDB()  {
		createIfNotExists(db , "directory");
		createIfNotExists(userPath , "directory");
		createIfNotExists(adminPath , "directory");
		createIfNotExists(nextId , "text");
		
		//create the nextId if it isn't
		try {
			List<String> content = Files.readAllLines(nextId);
			if (content.isEmpty()) {
				Files.writeString(nextId, "0");
			}
		} catch (IOException error) {
			System.out.println("error");
		} 
		
	}
	
	public static User getUserFromName(String name) {
		return new User(name);
	}
	
	public static User getUserFromId(int id) {
		for (User user: getUserList()) {
			if (user.getId() == id) {
				return new User(user.getName());
			}
		}
		return null;
	}

	
	public static int getNextId() {
		try {
			return Integer.valueOf(Files.readString(nextId));
		} catch (IOException error) {
			return 0;
		}
	}
	
	public static String getUserPassword(String user) {
		return getText(userPath.resolve(user).resolve("password.txt")); //get the password path
	}
	
	public static int getUserId(String user){
		return Integer.valueOf(getText(userPath.resolve(user , "id.txt"))); 
	}
	
	public static String getText(Path path) {
		try {
			return Files.readString(path);
		} catch (IOException error) {
			error.printStackTrace(); //CONTINUE HERE
			System.out.println("error in get text");
			return null;
		}
	}
	
	public static Message getLastMessage(User user , String contact) {
		
		List<String> lines;
		
		try {
			lines = Files.readAllLines(getChatPath(user , contact));
			if (!(lines.isEmpty())) {
				return new Message(lines.get(lines.size() - 1)); 
			}
		} catch (IOException e) {
			System.out.println("error in last message");
		}
		return new Message();
	}
	
	public static Path getUserPath() {
		return userPath;
	}
	
	public static Path getChatPath(User user , String contact) {
		return user.getContactPath().resolve(contact).resolve("chat.txt");
	}
	
	private static ArrayList<User> getUserList(){
		File userPathFile = userPath.toFile();
		String[] files = userPathFile.list();
		ArrayList<User> list = new ArrayList<>();
		for (String user: files) {
			list.add(new User(user));
		}
		return list;
	}
	
	private static ArrayList<String> getUsernamesList() {
		String[] list = userPath.toFile().list();
		return new ArrayList<String>(Arrays.asList(list));
	}
	
	public static ArrayList<String> getContactNamesList(User user){
		String[] contactList = user.getContactPath().toFile().list();
		return contactList == null 
				? new ArrayList<String>() 
				: new ArrayList<String>(Arrays.asList(contactList));
	}
	
	private static ArrayList<Integer> getIdsFromContacts(User user){
		ArrayList<Integer> ids = new ArrayList<>();
		for (String contact: getContactNamesList(user)) {
			ids.add(Integer.valueOf(getText(user.getContactPath().resolve(contact).resolve("id.txt"))));
		}
		return ids;
	}
	
	private static String getContactFromId(User user , int id) {
		for (String contact: getContactNamesList(user)) {
			if (getText(user.getContactPath().resolve(contact).resolve("id.txt")).equals(String.valueOf(id))) {
				return contact;
			}
		}
		return null;
	}
	
	private static int getContactId(User user , String contact) {
		String text = getText(user.getContactPath().resolve(contact).resolve("id.txt"));
		return Integer.valueOf(text);
	}
	private static ArrayList<Integer> getAllIds(){
		ArrayList<Integer> ids = new ArrayList<>();
		for (User user: getUserList()) {
			ids.add(user.getId());
		}
		return ids;
	}
	
	public static ArrayList<Message> getMessages(User user , String contact){
		String[] messages = getText(getChatPath(user , contact)).split("\\R");
		ArrayList<Message> list = new ArrayList<>();
		try {
			for (String message: messages) {
			list.add(new Message(message));
				}
		} catch (ArrayIndexOutOfBoundsException e) {
			return list;
		}
		
		return list;
	}
	
	public static boolean userExists(String user) {
		return getUsernamesList().contains(user);
	}
	
	public static void tryLogIn(String userName , String password) { //tries a logIn, if is not successful calls error function
		//verify: user name exists, user name and password match. the gaps are verified in startPanel 
		if (!(userExists(userName))) {
			StartPanel.getInstance().startError("This user does not exists");return;
		}
		if (!(getUserPassword(userName).equals(password))) {
			StartPanel.getInstance().startError("Incorrect password");return;
		}
		createIfNotExists(getUserPath().resolve(userName).resolve("contacts") , "directory");
		UIManager.userMain(getUserFromName(userName));
		
	}
	
	public static boolean canCreateAccount(String name , String password , String confirmPassword) {
		SignInPanel instance = SignInPanel.getInstance();
		
		if (userExists(name)) {
			instance.SignInError("User name not disponible"); return false;
		}
		if ((name+password+confirmPassword).contains(" ")) {
			instance.SignInError("No spaces allowed"); return false;
		}
		if (!(password.equals(confirmPassword))) {
			instance.SignInError("Passwords must match"); return false;
		}
		
		return true;
	}
	
	public static void createAccount(String name , String password , String confirmPassword) {
		
		Path newUser =  userPath.resolve(name);
		Path passwordText = newUser.resolve("password.txt");
		Path idText = newUser.resolve("id.txt");
		Path configText = newUser.resolve("config.txt");
		Path contactsDir = newUser.resolve("contacts");
		
		createIfNotExists(newUser , "directory");
		createIfNotExists(passwordText , "text");
		createIfNotExists(configText , "text");
		createIfNotExists(idText , "text");
		createIfNotExists(contactsDir , "directory");
		
		try {
			Files.writeString(passwordText , password);
			Files.writeString(configText, ""); //FUTURO
			Files.writeString(idText , String.valueOf(getNextId()));
			Files.writeString(nextId , String.valueOf(Integer.valueOf(getNextId() + 1))); //update the id
		} catch (IOException error) {
			System.out.println("error");
		}
	}
	
	private static void createIfNotExists(Path path , String type) {
		try {
			if (!(Files.exists(path))) {
				if (type.equals("directory")) {
					Files.createDirectory(path);
				}
				else if(type.equals("text")) {
					Files.createFile(path);
				}
			}
		} catch (IOException error) {
			System.out.println("Error");
		}
	}
	
	public static boolean canAddContact(User user , String name , int id) {
		if (getContactNamesList(user).contains(name)) {
			AddContactPanel.getInstance().addContactError("Username not aviable"); return false;
		}
		if (getIdsFromContacts(user).contains(id)) {
			AddContactPanel.getInstance().addContactError("Existing contact"); return false;
		}
		if (!(getAllIds().contains(id))) {
			AddContactPanel.getInstance().addContactError("No existing user"); return false;
		}
		if (user.getId() == id) {
			AddContactPanel.getInstance().addContactError("This is your id"); return false;
		}
		if (id < 0) {
			AddContactPanel.getInstance().addContactError("Enter a valid id"); return false;
		}
		
		return true;
	}
	
	public static void addContact(User user , String name , int id) {
		
		Path contactFolder = user.getContactPath().resolve(name);
		Path chatText = contactFolder.resolve("chat.txt");
		Path nameText = contactFolder.resolve("name.txt");
		Path idText = contactFolder.resolve("id.txt");
		
		createIfNotExists(contactFolder , "directory");
		createIfNotExists(chatText , "text");
		createIfNotExists(nameText , "text");
		createIfNotExists(idText , "text");
		
		try {
			
			Files.writeString(nameText , name);
			Files.writeString(idText, String.valueOf(id));
			
		} catch (IOException e) {
			System.out.println("error adding contact");
		}
	}
	
	public static void sendMessage(User user , String contact , String message) {
		try {
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy|HH:mm:ss");
			String text = "ME" + " " + time.format(format) + " " + message + System.lineSeparator();
			Files.writeString(getChatPath(user, contact), text , StandardOpenOption.APPEND);
			//add message for the other user
			User receiver = getUserFromId(getContactId(user , contact));
			text = "OTHER"+ " " + text.substring(3);
			Path ubi = receiver.getContactPath().resolve(getContactFromId(receiver , user.getId())).resolve("chat.txt");
			Files.writeString(ubi , text , StandardOpenOption.APPEND);
			
		} catch (IOException e) {
			System.out.println("error in send message");
		}
	}
	
	public static ImageIcon getImage(String image) {
		String imageString = "/resources/images/" + image;
		URL imageURL = DBManager.class.getResource(imageString);
		return new ImageIcon(imageURL);
	}
	
	public static ImageIcon getResizedImage(String stringImage , String mode) {
		Image image = getImage(stringImage).getImage();
		
		switch (mode) {
		case "preview": return new ImageIcon(image.getScaledInstance(64, 64, Image.SCALE_SMOOTH));
		case "chat": return new ImageIcon(image.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		default: return null;
		}
	}
}
