package objects;

import utils.DBManager;

public class User {
	
	public User(String name) {
		this.name = name;
		this.password = DBManager.getUserPassword(name);
		this.id =  DBManager.getUserId(name);
	}
	
	
	private String name , password;
	private final int id;

}
