package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.IVentDAO;
import model.Category;
import model.Event;
import model.Post;
import model.User;

/**
 * Servlet implementation class IVentServlet
 */
public class IVentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IVentDAO dao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IVentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() {
    	dao = new IVentDAO();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("iVent web service...");

		String pathInfo = request.getPathInfo();
		
		System.out.println(pathInfo);
		
		String[] paths = pathInfo.split("/");
		
		if (paths.length >= 3) {
			String requestType = paths[1];
			String param = paths[2];
			
			System.out.println("type: " + requestType);
			System.out.println("param: " + param);
			
			if (requestType.equals("user")) {
				long id = Long.parseLong(param);
				User user = dao.getUserById(id);
				
				if (user != null) {
					String json = new Gson().toJson(user, User.class);
					out.println(json);
				} else {
					response.setStatus(200);
				}			
			} else if (requestType.equals("category")) {
				String[] params = param.split(",");
				String json = "";				
				
				/*
					path = /1   get category where category_id = 1
					path = /user_1	get category list where user_id = 1
				*/
				if (params.length == 1) {		// get category list for a userid
					if( params[0].startsWith("user_")){
						long user_id = Long.parseLong( params[0].substring( "user_".length()));
						List<Category> category_list = dao.getCategoriesForUser(user_id);
						if( category_list != null ){
							json = new Gson().toJson(category_list, List.class);
							out.println(json);
						}
						else{
							response.setStatus(200);
						}
					}
					else{			// get category by id
						long id = Long.parseLong(params[0]);
						Category c = dao.getCategoryById(id);				
						if (c != null) {
							json = new Gson().toJson(c, Category.class);
							out.println(json);
						} else {
							response.setStatus(200);
						}
					}
				} else if (params.length == 2) { // get category by user id and name
					long userId = Long.parseLong(params[0]);
					String name = params[1];
					Category c = dao.getCategoryByUserIdAndName(userId, name);
					if (c != null) {
						json = new Gson().toJson(c, Category.class);
						out.println(json);
					} else {
						response.setStatus(200);
					}
				} else {
					response.setStatus(200);
				}			
			} else if (requestType.equals("event")) {
				String json = "";				
				if( param.startsWith("category_")){
					long category_id = Long.parseLong( param.substring("category_".length()));
					List<Event> e = dao.getEventsByCategoryId(category_id);
					if( e != null ){
						json = new Gson().toJson(e, List.class);
						out.println(json);
						response.setStatus(200);
					}
					else{
						response.setStatus(500);
					}
				}
				else{
					long event_id = Long.parseLong( param);
					Event e = dao.getEventById(event_id);
					if( e != null) {
						json = new Gson().toJson(e, Event.class);
						out.println(json);
						response.setStatus(200);
					}
					else{
						response.setStatus(500);
					}
				}
			} else if (requestType.equals("post")) {
				String json = "";
				if( param.startsWith("event_") ){
					long event_id = Long.parseLong( param.substring("event_".length()));
					List<Post> p = dao.getPostsByEventId(event_id);
					if( p != null ){
						json = new Gson().toJson(p, List.class);
						out.print(json);
						response.setStatus(200);
					}
					else{
						response.setStatus(500);
					}
				}
				else{
					long post_id = Long.parseLong( param );
					Post p = dao.getPostById(post_id);
					if( p != null ){
						json = new Gson().toJson(p, Post.class);
						out.print(json);
						response.setStatus(200);
					}
					else{
						response.setStatus(500);
					}
				}
			}
		}
		
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("iVent web service...");

		String pathInfo = request.getPathInfo();
		String method = request.getMethod();
	
		String paths[] = pathInfo.split("/");
		
		String requestType = pathInfo.substring(1);
		if (paths.length != 0) {
			System.out.println("requestType = "+ requestType );
			
			if (requestType.equals("user")) {
				// curl --data "name=test&password=password" http://localhost:8080/IVentServer/ivent/user
				String name = request.getParameter("name");
				String password = request.getParameter("password");
				if( name != null && password != null){
					if( 0 != dao.createUser(name, password))
						response.setStatus(200);
					else
						response.setStatus(500);
				}
				else{
					response.setStatus(500);
				}
			} else if (requestType.equals("category")) {
				// curl --data "userId=123&name=nam1" http://localhost:8080/IVentServer/ivent/category
				long userId = Long.parseLong(request.getParameter("userId"));
				String name = request.getParameter("name");
				if( name != null){
					if( 0 != dao.createCategory(userId, name) )
						response.setStatus(200);
					else
						response.setStatus(500);
				}
				else{
					response.setStatus(500);
				}
				
			} else if (requestType.equals("event")) {
				// TODO: add event category binding?
				// curl --data "name=1&eventTime=2&location=3&description=4&imageLink=5" http://localhost:8080/IVentServer/ivent/category
				String name = request.getParameter("name");
				String eventTime = request.getParameter("eventTime");
				String location = request.getParameter("location");
				String description = request.getParameter("description");
				String imageLink = request.getParameter("imageLink");
				if( name != null && eventTime != null && location != null && description != null && imageLink != null){
					if( 0 != dao.createEvent(name, eventTime, location, description, imageLink) ){
						response.setStatus(200);
					}
					else response.setStatus(500);
				}
				else response.setStatus(500);
				
			} else if (requestType.equals("post")) {
				// curl --data "userId=1&eventId=2&postText=3&ts=4" http://localhost:8080/IVentServer/ivent/category
				long userId = Long.parseLong(request.getParameter("userId"));
				long eventId = Long.parseLong(request.getParameter("eventId"));
				String postText = request.getParameter("postText");
				String ts = request.getParameter("ts");
				if( postText != null && ts != null ){
					if( 0 != dao.createPost(userId, eventId, postText, ts)){
						response.setStatus(200);
					}
					else response.setStatus(500);
				}
				else response.setStatus(500);
			} else {
				System.out.println("unsupport type");
			}
			
		}
	}

}
