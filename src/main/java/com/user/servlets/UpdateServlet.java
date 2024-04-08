package com.user.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CreateUserServlet
 */
@WebServlet(urlPatterns="/updateServlet", initParams= {
		@WebInitParam(name="name", value="Not provided"), 
		@WebInitParam(name="email", value="Not provided")
})
public class UpdateServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    public Connection connection=null;
    
    public void init()
    {
    	System.out.println("*****************Start of init method");
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		connection = DriverManager.getConnection("jdbc:mysql://localhost/mydb", "root", "root");
    		
    	}
    	catch(SQLException | ClassNotFoundException e)
    	{
    		e.printStackTrace();
    	}
    	System.out.println("************End of init method");
    	
    }
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try {
			Statement statement = connection.createStatement();
			int result = statement.executeUpdate("Update user set password = '"+password+"' where email='"+email+"'");
			PrintWriter pw = response.getWriter();
			if(result>0)
			{
				pw.print("<H1> password Updated </H1>");
			}
			else
			{
				pw.print("<H1>Error updating user</H1>");
			}
			pw.flush();
			
			
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

















//
//<!DOCTYPE html>
//<html>
//	<head>
//		<meta charset="UTF-8">
//			<title>Update User</title>
//		</head>
//		<body>
//			<h1>Update User: </h1>
//			<form method="post" action="updateServlet">
//				<table>
//					<tr>
//						<td>Email: </td>
//						<td>
//							<Input name="email"/>
//						</td>
//					</tr>
//					<tr>
//						<td>password:</td>
//						<td>
//							<input name="password" type="password"/>
//						</td>
//					</tr>
//					<tr>
//						<td/>
//						<td>
//							<input type="submit" value="update"/>
//						</td>
//					</tr>
//				</table>
//			</form>
//		</body>
//	</html>

