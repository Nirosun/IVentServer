package dao;

import java.util.List;

import model.Category;
import model.Event;
import model.Post;
import model.User;

public class DAOTest {
	public static void main(String[] args) {
		IVentDAO dao = new IVentDAO();
		
		// use mysql workbeanch to clear/create tables
		
		dao.createUser("u1", "p1");
		dao.createUser("u2", "p2");
		
		dao.createCategory(1, "c1");
		dao.createCategory(1, "c2");
		dao.createCategory(2, "c2");
		dao.createCategory(2, "c3");
		dao.createCategory(2, "c4");
		
		dao.createEvent("e1", "2015-04-20 15:59:10", "here", "desc", "k/dl");
		dao.addCategoryEventBinding(1, 2);
		dao.createEvent("e2", "2015-04-10 15:59:10", "here", "desc", "k/gl");
		dao.createEvent("e3", "2015-04-10 15:59:10", "wow", "desc", "k/gl");
		dao.addCategoryEventBinding(2, 2);
		dao.addCategoryEventBinding(2, 4);
		dao.addCategoryEventBinding(3, 4);
		
		dao.createPost(1, 1, "hahaha", "2015-04-10 15:59:10");
		dao.createPost(2, 2, "wow", "2015-04-11 15:59:10");
		dao.createPost(1, 2, "owhw", "2015-04-11 15:59:12");
		
		User user = dao.getUserById(1);
		System.out.println(user.getName());
		System.out.println();
		
		Category cate = dao.getCategoryById(2);
		System.out.println(cate.getName());
		System.out.println();
		
		cate = dao.getCategoryByUserIdAndName(1, "c2");
		System.out.println(cate.getName());
		System.out.println();
		
		List<Category> cates = dao.getCategoriesForUser(2);
		for (Category c : cates)
			System.out.println(c.getName());	
		System.out.println();
		
		Event e = dao.getEventById(2);
		System.out.println(e.getName());
		System.out.println();

		List<Event> events = dao.getEventsByCategoryId(4);
		for (Event ee : events)
			System.out.println(ee.getName());
		System.out.println();
		
		Post post = dao.getPostById(2);
		System.out.println(post.getPostText());
		System.out.println();
		
		List<Post> posts = dao.getPostsByEventId(2);
		for (Post p : posts)
			System.out.println(p.getPostText());
		System.out.println();

		System.out.println("end");
		
	}
}
