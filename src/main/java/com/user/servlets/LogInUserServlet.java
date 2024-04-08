package com.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LogInUserServlet
 */
@WebServlet(urlPatterns="/logInUserServlet", initParams={
		@WebInitParam(name = "dbUrl", value="jdbc:mysql://localhost/mydb"), 
			@WebInitParam(name="dbUser", value="root"), @WebInitParam(name="dbPassword", value="root")})
public class LogInUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Connection connection = null;   
	
	
	public void init(ServletConfig config)
	{
		try {
			ServletContext servletContext = config.getServletContext();
			connection = DriverManager.getConnection(servletContext.getInitParameter("dbUrl"),
					servletContext.getInitParameter("dbUser"), servletContext.getInitParameter("dbPassword"));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String email = request.getParameter("email");
		 String password =  request.getParameter("password");
		 try {
			Statement statement = connection.createStatement();
			ResultSet set = statement.executeQuery("Select firstName, lastName from user where email IN ('"+email+"') AND password IN ('"+password+"');");
			String firstName = set.getString(1);
			String lastName = set.getString(2);
			
			PrintWriter pw = response.getWriter();
			pw.print("<H1>Hello" + firstName + " " + lastName + "</H1>");
			
			pw.flush();
			pw.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
	}
	
	public void destroy()
	{
		try
		{
			connection.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

}
