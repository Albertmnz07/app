package objects;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class Message {
	
	private String sender , message;
	private LocalDateTime time;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy|HH:mm:ss");
	
	public Message(String text) {
		
		String[] separation = text.split(" ");
		System.out.println(separation[0]);
		System.out.println(separation[1]);
		System.out.println(separation[2]);
		
		sender = separation[0];
		
//		String[] textTime = separation[1].split("|");
		time = LocalDateTime.parse(separation[1], formatter);
		
		message = separation[2];
	}
	
	public Message() {
		message = "";
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

}
