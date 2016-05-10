package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Notification extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private ThreadNotification threadNotification = null;
	private Thread T_threadNotification = null;
		
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException {
		
		PrintWriter out = resp.getWriter();
		String status = req.getParameter("status");
		Connection ct = null;  
	    Statement sm = null;  
	    ResultSet rs = null; 
	    
	    ct = Mysql.connection();
		
		if(threadNotification == null){
			threadNotification = new ThreadNotification();
		}
		if(T_threadNotification == null){
			T_threadNotification = new Thread(threadNotification);
		}
		
		if("1".equals(status)){
			//Create a thread to change limit of request number per hour
			T_threadNotification.start();
			
		    //Change the status of sending notification
	    	try{  
		        sm = ct.createStatement();
		        sm.executeUpdate("update status set notification = 1 where notification IS NOT NULL");
		    }catch(Exception ex) {  
		            System.out.println("<p>failed!</p>");
		    }
	    	out.print("<script>alert('Sending notification starts successfully!');</script>");
	    	out.print("<script>window.location.href='homepage.jsp';</script>");
		}else{
			//Stop the thread; set the thread to null 
			threadNotification.shutdown();
			threadNotification = null;
			T_threadNotification = null;
			
			//Change the status of changing number
			try {
		        sm.executeUpdate("update status set notification = 0 where notification IS NOT NULL");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        out.print("<script>alert('Sending notification stops successfully!');</script>");
	    	out.print("<script>window.location.href='homepage.jsp';</script>");
		}
	}
}
