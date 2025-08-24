package objects;

import java.nio.file.Path;
import controlers.DBManager;

public class User {
	
	private String name , password;
	private final int id;
	private Path userPath , contactPath;
	
	public User(String name) {
		this.name = name;
		this.password = DBManager.getUserPassword(name);
		this.id =  DBManager.getUserId(name);
		
		userPath = DBManager.getUserPath().resolve(name);
		contactPath = userPath.resolve("contacts");
		
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public Path getUserPath() {
		return userPath;
	}
	
	public Path getContactPath() {
		return contactPath;
	}
	
	public int getId() {
		return id;
	}
}
