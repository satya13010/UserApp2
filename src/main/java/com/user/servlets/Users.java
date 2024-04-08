package com.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Users
 */
@WebServlet(urlPatterns="/readUsers", initParams= {
		@WebInitParam(name="dbUrl", value="jdbc:mysql://localhost/mydb"),
		@WebInitParam(name="dbUser", value="root"), @WebInitParam(name="dbPassword", value="root")})
public class Users extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    public Connection connection=null;
 
    
    
	public void init(ServletConfig config)
	{
		
		System.out.println("init in readUsers");
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(config.getInitParameter("dbUrl"),
					config.getInitParameter("dbUser"), config.getInitParameter("dbPassword"));
			
		
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside doGet");
		
		
		
		try(Statement statement = connection.createStatement();
			ResultSet allUsers = statement.executeQuery("Select * from user");
				)
		{
			
			PrintWriter out = response.getWriter();
			out.print("<!DOCTYPE html>");
			out.print("<html>");
			out.print("<head>");
			out.print("<title>Update User</title>");
			  out.print("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");
			out.print("</head>");
			out.print("<body>");
			out.print("<table class=\"table\">");
			out.print("<tr class=\"thead-dark\">");
			  out.print("<th scope=\"col\">First Name</th>");
	            out.print("<th scope=\"col\">Last Name</th>");
	            out.print("<th scope=\"col\">Email</th>");
			out.print("</tr>");
			while(allUsers.next())
			{
				out.println("<tr>");
				out.println("<td>");
				out.print(allUsers.getString(1));
				out.println("</td>");
				out.println("<td>");
				out.print(allUsers.getString(2));
				out.println("</td>");
				out.println("<td>");
				out.print(allUsers.getString(3));
				out.println("</td>");
				out.println("</tr>");
			}
			out.print("</table>");	
			out.print("</body>");
			out.print("</html>");
			out.flush();
			out.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	public void destroy()
	{
		try {
			connection.close();
			} catch (SQLException e) {
				
			e.printStackTrace();
		}
	}
	 

}
