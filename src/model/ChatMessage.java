package model;

import java.sql.Timestamp;

public class ChatMessage {
	private long id;
	
	private long userId;
	
	private long eventId;
	
	private String chatText;
	
	private Timestamp ts;
	
	public ChatMessage() {
		
	}

	public ChatMessage(long id, long userId, long eventId, String chatText,
			Timestamp ts) {
		super();
		this.id = id;
		this.userId = userId;
		this.eventId = eventId;
		this.chatText = chatText;
		this.ts = ts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}

	public String getChatText() {
		return chatText;
	}

	public void setChatText(String chatText) {
		this.chatText = chatText;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	
	
}
