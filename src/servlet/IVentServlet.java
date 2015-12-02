package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.IVentDAO;
import model.Category;
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
				
				// TODO: get categories for a user???
				if (params.length == 1) {	// get category by id
					long id = Long.parseLong(params[0]);
					Category c = dao.getCategoryById(id);				
					if (c != null) {
						json = new Gson().toJson(c, Category.class);
						out.println(json);
					} else {
						response.setStatus(200);
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
				
			} else if (requestType.equals("post")) {
				
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
		
		System.out.println(pathInfo);
		
		String[] paths = pathInfo.split("/");
		
		if (paths.length != 0) {
			String requestType = paths[0];
			
			if (requestType.equals("user")) {
				
			} else if (requestType.equals("category")) {
				
			} else if (requestType.equals("event")) {
				
			} else if (requestType.equals("post")) {
				
			}
		}
	}

}
