package utils;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import objects.User;
import panels.MainInterface;
import panels.SignInPanel;
import panels.StartPanel;

import java.io.File;

public class DBManager {
	
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
	
	public static User getUser(String name) {
		return new User(name);
	}
	
	public static int getNextId() {
		try {
			return Integer.valueOf(Files.readString(nextId));
		} catch (IOException error) {
			return 0;
		}
	}
	
	public static boolean userExists(String user) {
		return getUserList().contains((String) user);
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
			System.out.println(error.getMessage());
			return "";
		}
	}
	
	public static void tryLogIn(String userName , String password) { //tries a logIn, if is not successful calls error function
		//verify: user name exists, user name and password match. the gaps are verified in startPanel 
		if (!(userExists(userName))) {
			StartPanel.getInstance().startError("This user does not exists");return;
		}
		if (!(getUserPassword(userName).equals(password))) {
			StartPanel.getInstance().startError("Incorrect password");return;
		}
		MainInterface.userPanel(getUser(userName));
		
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
		Path contactsDir = newUser.resolve("contacs");
		
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
	
	private static ArrayList<String> getUserList(){
		File userPathFile = userPath.toFile();
		String[] files = userPathFile.list();
		return new ArrayList<String>(Arrays.asList(files));
	}
	
	
	
	private static Path db = Paths.get("db");
	private static Path userPath = Paths.get("db" , "user");
	private static Path adminPath = Paths.get("db" , "admin");
	private static Path nextId = Paths.get("db" , "nextId.txt");

}
