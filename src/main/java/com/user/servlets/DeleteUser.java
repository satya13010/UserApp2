package com.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 
//@WebServlet(urlPatterns="/deleteServlet", initParams = {
//		@WebInitParam(name = "dbUrl", value="jdbc:mysql://localhost/mydb"), 
//		@WebInitParam(name="dbUserName", value="root"),
//		@WebInitParam(name="dbPassword", value="root")
//}
//)
public class DeleteUser extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public Connection connection = null;
       
    public void init(ServletConfig config)
    {
    	System.out.println("Inside init of Delete User");
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			connection=DriverManager.getConnection(config.getInitParameter("dbUrl"), 
					config.getInitParameter("dbUserName"), 
					config.getInitParameter("dbPassword"));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
	 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		System.out.println("Inside init of delete user");
		response.setContentType("text/html");
		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("Delete from user where email='"+email+"'");
			PrintWriter pw = response.getWriter();
			
			if(result>0)
			{
				pw.print("<H1> User is deleted <H1>");
			}
			else
			{
				pw.print("<H1> User is does not exist<H1>");
			}
			pw.flush();
			pw.close();
		} catch (SQLException e) {
			e.printStackTrace();
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
