package model;

import java.sql.Timestamp;

public class Post {
	long id;
	
	long userId;
	
	long eventId;
	
	String postText;
	
	Timestamp ts;
	
	public Post() {
		
	}

	public Post(long id, long userId, long eventId, String postText,
			Timestamp ts) {
		super();
		this.id = id;
		this.userId = userId;
		this.eventId = eventId;
		this.postText = postText;
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

	public String getPostText() {
		return postText;
	}

	public void setPostText(String postText) {
		this.postText = postText;
	}

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	
	
}
