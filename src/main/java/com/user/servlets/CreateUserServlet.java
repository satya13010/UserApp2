package com.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateUserServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    public Connection connection=null;
    
    
    public void init(ServletConfig config)
    {
    	System.out.println("******************inside init method");
    	try {
    		
    		
			ServletContext servletContext = config.getServletContext();
			
			Enumeration<String> e = servletContext.getInitParameterNames();
			
			
			while(e.hasMoreElements())
			{
				String s = e.nextElement();
				System.out.println(s + " " + servletContext.getInitParameter(s));
			}
    		
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		connection = DriverManager.getConnection(config.getInitParameter("dbUrl"), config.getInitParameter("dbUser"), config.getInitParameter("dbPassword"));
    		
    		
    	}
    	catch(SQLException e)
    	{
    		e.printStackTrace();
    	}
    	catch(ClassNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	
    	System.out.println("*******************end of init method");
    	
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		System.out.println("Inside Dopost");
		PrintWriter pw = null;
		try {
			System.out.println("*******Inside try");
			Statement statement = connection.createStatement();
			System.out.println("***********above result *************");
			int result = statement.executeUpdate("insert into user values('"+firstName+"', '"+lastName+"','"+email+"','"+password+"')");
			System.out.println("**************Below result*************" + email + " " + result);
			pw = response.getWriter();
	
			
			if(result>0)
			{
				pw.print("<H1> User created </H1>");
			}
			else
			{
				pw.print("<H1> User already exists</H1>");
			}
			
			
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("***********Inside catch block");
			pw.print("<h1>Email ID created already exists, please retry with a differe mail</h1>");
			pw.print("<a href=addUser.html>redirect to previous page</a>");
			
		} catch (SQLException e) {
			System.out.println("*********INSIDE SQLException");
			e.printStackTrace();
		}
		finally
		{
			System.out.println("*********Inside finally");
			pw.flush();
			pw.close();
		}
		
	}

	
	public void destroy()
	{
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
