package objects;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import utils.DBManager;

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
	
	public Path getContactPath() {
		return contactPath;
	}
	
	//getter for psthd
}
