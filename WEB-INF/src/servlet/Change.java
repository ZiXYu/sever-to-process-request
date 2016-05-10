package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Change extends HttpServlet{
	
private static final long serialVersionUID = 1L;
private ThreadChange threadChange = null;
private Thread T_threadChange = null;
		
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		String status = req.getParameter("status"); //status = 1, start thread; otherwise, stop thread
		Connection ct = null;  
	    Statement sm = null;  
	    ResultSet rs = null; 
	    
	    ct = Mysql.connection();
		
		//Check if the thread was created
		if(threadChange == null){
			threadChange = new ThreadChange();
		}
		if(T_threadChange == null){
			T_threadChange = new Thread(threadChange);
		}
		
		if("1".equals(status)){
			//Create a thread to change limit of request number per hour
			T_threadChange.start();
			
		    //Change the status of changing request number
	    	try{  
		        sm = ct.createStatement();
		        sm.executeUpdate("update status set changeNum = 1 where changeNum IS NOT NULL");
		    }catch(Exception ex) {  
		            System.out.println("<p>failed!</p>");
		    }
			out.print("<script>alert('Processing request starts successfully!');</script>");
	    	out.print("<script>window.location.href='homepage.jsp';</script>");
		}else{
			//Stop the thread; set the thread to null 
			threadChange.shutdown();
			threadChange = null;
			T_threadChange = null;
			
			//Change the status of changing number
			try {
		        sm.executeUpdate("update status set changeNum = 0 where changeNum IS NOT NULL");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	      
	        out.print("<script>alert('Changing number stops successfully!');</script>");
	    	out.print("<script>window.location.href='homepage.jsp';</script>");
		}
	}
}
