package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import model.Category;
import model.ChatMessage;
import model.Event;
import model.Post;
import model.User;

public class IVentDAO {
	private static final String DRIVER_NAME = "com.mysql.jdbc.Driver";

	private static final String URL = "jdbc:mysql://localhost:3306/ivent";

	private static final String USER_NAME = "root";

	// only for testing (should reset for different machines)
	private static final String PASSWORD = "";

	// text file containing all SQL queries
	// FIXME: hardcoded
	private static final String SQL_FILE_NAME = "/Users/shuo/18641/project/IVentServer/WebContent/sql.properties";

	private static final String CREATE_TABLES_FILE_NAME = "create_tables.sql";
	
	// SQL query keys
	private static final String CREATE_USER = "CreateUser";
	private static final String GET_USER_BY_ID = "GetUserById";
	private static final String CREATE_CATEGORY = "CreateCategory";
	private static final String GET_CATEGORY_BY_ID = "GetCategoryById";
	private static final String GET_CATEGORY_BY_USER_ID_AND_NAME = "GetCategoryByUserIdAndName";
	private static final String GET_CATEGORIES_BY_USER_ID = "GetCategoriesByUserId";
	private static final String CREATE_EVENT = "CreateEvent";
	private static final String ADD_CATEGORY_EVENT_BINDING = "AddCategoryEventBinding";
	private static final String GET_EVENT_BY_ID = "GetEventById";
	private static final String GET_EVENTS_BY_CATEGORY_ID = "GetEventsByCategoryId";
	private static final String CREATE_POST = "CreatePost";
	private static final String GET_POST_BY_ID = "GetPostById";
	private static final String GET_POSTS_BY_EVENT_ID = "GetPostsByEventId";
	
	/**
	 * Create database and 5 tables
	 */
	public void createDatabaseAndTables() {
		Connection conn = getConnection();

		try {
			Statement statement = conn.createStatement();

			BufferedReader reader = new BufferedReader(
					new FileReader(CREATE_TABLES_FILE_NAME));

			String line = null;
			while ((line = reader.readLine()) != null) {
				statement.executeUpdate(line);
			}

			reader.close();
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieve requested SQL query from text file using given key
	 * 
	 * @param key
	 * @return
	 */
	private String loadSQL(String key) {
		Properties p = new Properties();

		try {						
			FileInputStream in = new FileInputStream(SQL_FILE_NAME);			
						
			p.load(in);
						
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return p.getProperty(key);
	}

	/**
	 * Get JDBC connection
	 * 
	 * @return
	 */
	private Connection getConnection() {
		Connection conn = null;

		try {
			Class.forName(DRIVER_NAME);
			conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("error get connection. ");
			e.printStackTrace();
		}

		return conn;
	}
	
	private void closeResources(ResultSet rs, Statement st, Connection con)
			throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}
	}
	
	/* User operations */
	
	public synchronized int createUser(String name, String password) {
		int rowCount = 0;
		
		try {
			String sql = loadSQL(CREATE_USER);
						
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			
			statement.setString(1, name);
			statement.setString(2, password);
			rowCount = statement.executeUpdate();
			
			closeResources(null, statement, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}
	
	public User getUserById(long id) {
		try {
			String sql = loadSQL(GET_USER_BY_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			
			System.out.println(statement.toString());
			
			ResultSet rs = statement.executeQuery();
			
			User user = null;
			if (rs.next()) {
				user = new User();
				user.setId(rs.getLong("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}		
			
			closeResources(rs, statement, conn);			
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* Category operations */
	
	public synchronized int createCategory(long userId, String name) {
		int rowCount = 0;

		try {
			String sql = loadSQL(CREATE_CATEGORY);

			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setLong(1, userId);
			statement.setString(2, name);

			statement.executeUpdate();
			
			closeResources(null, statement, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}
	
	public Category getCategoryById(long id) {
		try {
			String sql = loadSQL(GET_CATEGORY_BY_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			Category category = null;
			if (rs.next()) {
				category = new Category();
				category.setId(rs.getLong("id"));
				category.setUserId(rs.getLong("user_id"));
				category.setName(rs.getString("name"));
			}		
			
			closeResources(rs, statement, conn);			
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Category getCategoryByUserIdAndName(long userId, String name) {
		try {
			String sql = loadSQL(GET_CATEGORY_BY_USER_ID_AND_NAME);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, userId);
			statement.setString(2, name);
			
			ResultSet rs = statement.executeQuery();
			
			Category category = null;
			if (rs.next()) {
				category = new Category();
				category.setId(rs.getLong("id"));
				category.setUserId(rs.getLong("user_id"));
				category.setName(rs.getString("name"));
			}		
			
			closeResources(rs, statement, conn);			
			return category;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Category> getCategoriesForUser(long userId) {
		List<Category> categories = new ArrayList<>();
		
		try {
			String sql = loadSQL(GET_CATEGORIES_BY_USER_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, userId);
			
			ResultSet rs = statement.executeQuery();
			
			Category category = null;
			while (rs.next()) {
				category = new Category();
				category.setId(rs.getLong("id"));
				category.setUserId(rs.getLong("user_id"));
				category.setName(rs.getString("name"));
				
				categories.add(category);
			}		
			
			closeResources(rs, statement, conn);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}
	
	public synchronized void deleteCategory(long id) {
		// TODO: to be completed
	}
	
	/* Event operations */
	
	public synchronized int createEvent(String name, String eventTime, 
			String location, String description, String imageLink) {
		int rowCount = 0;
		
		try {
			String sql = loadSQL(CREATE_EVENT);

			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setString(1, name);
			statement.setString(2, eventTime);
			statement.setString(3, location);
			statement.setString(4, description);
			statement.setString(5, imageLink);		
			statement.executeUpdate();
			
			closeResources(null, statement, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}
	
	public synchronized int addCategoryEventBinding(long eventId, long categoryId) {
		int rowCount = 0;
		
		try {
			String sql = loadSQL(ADD_CATEGORY_EVENT_BINDING);

			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setLong(1, categoryId);
			statement.setLong(2, eventId);
			rowCount = statement.executeUpdate();
			
			closeResources(null, statement, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}
	
	public Event getEventById(long id) {
		try {
			String sql = loadSQL(GET_EVENT_BY_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			Event event = null;
			if (rs.next()) {
				event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setEventTime(rs.getString("event_time"));
				event.setLocation(rs.getString("location"));
				event.setDescription(rs.getString("description"));
				event.setImageLink(rs.getString("image_link"));
			}		
			
			closeResources(rs, statement, conn);			
			return event;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Event> getEventsByCategoryId(long categoryId) {
		List<Event> events = new ArrayList<>();
		
		try {
			String sql = loadSQL(GET_EVENTS_BY_CATEGORY_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, categoryId);
			
			ResultSet rs = statement.executeQuery();
			
			Event event = null;
			while (rs.next()) {
				event = new Event();
				event.setId(rs.getLong("id"));
				event.setName(rs.getString("name"));
				event.setEventTime(rs.getString("event_time"));
				event.setLocation(rs.getString("location"));
				event.setDescription(rs.getString("description"));
				event.setImageLink(rs.getString("image_link"));
				
				events.add(event);
			}		
			
			closeResources(rs, statement, conn);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return events;
	}
	
	public synchronized void deleteEvent(long id) {
		// TODO: to be completed
	}
	
	/* Post operations */
	
	public synchronized int createPost(long userId, long eventId, String postText, String ts) {
		int rowCount = 0;
		
		try {
			String sql = loadSQL(CREATE_POST);

			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);

			statement.setLong(1, userId);	
			statement.setLong(2, eventId);
			statement.setString(3, postText);
			statement.setString(4, ts);
			statement.executeUpdate();
			
			closeResources(null, statement, conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowCount;
	}
	
	public Post getPostById(long id) {
		try {
			String sql = loadSQL(GET_POST_BY_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			Post post = null;
			if (rs.next()) {
				post = new Post();
				post.setId(rs.getLong("id"));
				post.setEventId(rs.getLong("event_id"));
				post.setUserId(rs.getLong("user_id"));
				post.setPostText(rs.getString("post_text"));
				post.setTs(rs.getTimestamp("ts"));
			}		
			
			closeResources(rs, statement, conn);			
			return post;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Post> getPostsByEventId(long eventId) {
		List<Post> posts = new ArrayList<>();
		
		try {
			String sql = loadSQL(GET_POSTS_BY_EVENT_ID);
			
			Connection conn = getConnection();
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setLong(1, eventId);
			
			ResultSet rs = statement.executeQuery();
			
			Post post = null;
			while (rs.next()) {
				post = new Post();
				post.setId(rs.getLong("id"));
				post.setEventId(rs.getLong("event_id"));
				post.setUserId(rs.getLong("user_id"));
				post.setPostText(rs.getString("post_text"));
				post.setTs(rs.getTimestamp("ts"));
				
				posts.add(post);
			}		
			
			closeResources(rs, statement, conn);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}
	
	public synchronized void deletePost(long id) {
		// TODO: to be completed
	}
	
	/* Chat operations */
	
	public synchronized int createChatMessage(long userId, long eventId, String chatText, String ts) {
		// TODO: to be completed
		return 0;
	}
	
	public ChatMessage getChatMessageById(long id) {
		// TODO: to be completed
		return null;
	}
	
	public List<ChatMessage> getChatMessagesByEventId(long eventId) {
		// TODO: to be completed
		return null;
	}
	
}
