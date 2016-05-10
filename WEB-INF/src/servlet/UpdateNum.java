package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateNum extends HttpServlet{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				
				String num = req.getParameter("number") ;
				
				PrintWriter out = resp.getWriter();
				Connection ct = null;  
			    Statement sm = null;  
			    ResultSet rs = null; 
			    int result = 0;
			    
				//update request number for the next hour
			    try{
			    	ct = Mysql.connection();
			    	sm = ct.createStatement();
			    	result = sm.executeUpdate("update status set requestNew = '" + num  + "' where flag is not null");
			    	
			    	if(result != -1){
			    		out.print("<script>alert('Update request number successfully!');</script>");
			        	out.print("<script>window.location.href='homepage.jsp';</script>");
			    	}else{
			    		out.print("<script>alert('Update request number failed!');</script>");
			        	out.print("<script>window.location.href='homepage.jsp';</script>");
			    	}
			    	
			    }catch(Exception ex){
			    	System.out.print("Fail!\n");
			    }
	}
}
