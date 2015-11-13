package model;

public class Category {
	long id;
	
	long userId;
	
	String name;
	
	public Category() {
		
	}

	public Category(long id, long userId, String name) {
		super();
		this.id = id;
		this.userId = userId;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
