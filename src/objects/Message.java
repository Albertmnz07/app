package objects;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Message {
	
	private String sender , message;
	private LocalDateTime time;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy|HH:mm:ss");
	
	public Message(String text) {
		
		String[] separation = text.split(" " , 3);
		
		sender = separation[0];
		
//		String[] textTime = separation[1].split("|");
		time = LocalDateTime.parse(separation[1], formatter);
		
		message = separation[2];
	}
	
	public Message() {
		message = "";
	}
	
	public Message(String sender , LocalDateTime time , String message) {
		this.sender = sender;
		this.time = time;
		this.message = message;
	}
	
	public String getText() {
		return message;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getCompleteHour() {
		return time != null ? time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) : "";
	}
	
	public static Message createMessage(String sender , String message) {
		return new Message(sender , LocalDateTime.now() , message);
	}

}
