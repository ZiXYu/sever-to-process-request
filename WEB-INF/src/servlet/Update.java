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

public class Update extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
				
				String userid = req.getParameter("userid") ;
				String type = req.getParameter("type");
				
				PrintWriter out = resp.getWriter();
				Connection ct = null;  
			    Statement sm = null;  
			    ResultSet rs = null; 
			    int result = 0;
			    
			    Calendar calendar = Calendar.getInstance();
			    int day = calendar.get(Calendar.DATE);
			    int hour = calendar.get(Calendar.HOUR_OF_DAY);
			    int min = calendar.get(Calendar.MINUTE);
			    int second = calendar.get(Calendar.SECOND);
			    
			    ct = Mysql.connection();
			    
			    if ("1".equals(type)){
					//type = 1; user just open the app and try to synchronize with our server
			    	try{  
				        sm = ct.createStatement();
				        rs = sm.executeQuery("select * from window where UUID = '" + userid + "'");
						//set default rate as 10, reqest number and request weight as 0;
				        if(rs.next()){
				        	sm.executeUpdate("update window set reqNumber = 0, reqWeight = 0, window = 10 where UUID ='" + userid + "'"  );	       	
				        }else{
				        	sm.executeUpdate("insert into window(UUID, reqNumber, reqWeight, window) values ('" + userid + "', '0', '0', '10')");
				        }
						//send the rate back to user
				        out.print(10 + "," + second);
				    }catch(Exception ex) {  
				            System.out.println("<p>failed!</p>");
				    }
			    }
			    else if("2".equals(type)){
					//type = 2; user report her request number and request data every 15s
			    	String reqNumber = req.getParameter("reqNumber"); //total generated request number
			    	String reqWeight = req.getParameter("reqWeight"); //total request weight 
			    	String reqNumOne = req.getParameter("reqNumOne"); //request number of weight = 1
			    	String reqNumTwo = req.getParameter("reqNumTwo"); //request number of weight = 2
			    	String reqNumThe = req.getParameter("reqNumThe"); //request number of weight = 3
			    	String reqReal = req.getParameter("reqReal"); //real sent request number 
			    	String query = null;
			    	try{  
						//store all these historical data into the database
						
				        sm = ct.createStatement();
				        result = sm.executeUpdate("update window set reqNumber = '" + reqNumber + "', reqWeight = '" + reqWeight + "' where UUID = '" + userid + "'");
				        // update the reqNum and reqWeight value of the window table
				        
				        rs = sm.executeQuery("select request from request where day =" + day + " and hour = " + hour + " and min = " + min);
				        if(rs.next()){
				        	int request = rs.getInt(1) + Integer.parseInt(reqReal);
				        	result = sm.executeUpdate("update request set request = " + request + " where day = " + day + " and hour = " + hour + " and min = " + min);
				        }else{
				        	result = sm.executeUpdate("insert into request(day,hour,min,request) values (" + day + "," + hour + "," + min + ",'" + reqReal + "')" );
				        }
				        
				        rs = sm.executeQuery("select request from history where day = " + day + " and hour = " + hour + " and min = " + min + " and weight = 1");
				        if(rs.next()){
				        	int request = rs.getInt(1) + Integer.parseInt(reqNumOne);
				        	result = sm.executeUpdate("update history set request = " + request + " where day =" + day + " and hour = " + hour + " and min = " + min + " and weight = 1");
				        }else{
				        	result = sm.executeUpdate("insert into history(day,hour,min,request,weight) values (" + day + "," + hour + "," + min + ",'" + reqNumOne + "', 1 )" );
				        }
				        
				        rs = sm.executeQuery("select request from history where day = " + day + " and hour = " + hour + " and min = " + min + " and weight = 2");
				        if(rs.next()){
				        	int request = rs.getInt(1) + Integer.parseInt(reqNumTwo);
				        	result = sm.executeUpdate("update history set request = " + request + " where day =" + day + " and hour = " + hour + " and min = " + min + " and weight = 2");
				        }else{
				        	result = sm.executeUpdate("insert into history(day,hour,min,request,weight) values (" + day + "," + hour + "," + min + ",'" + reqNumTwo + "', 2 )" );
				        }
				        
				        rs = sm.executeQuery("select request from history where day = " + day + " and hour = " + hour + " and min = " + min + " and weight = 3");
				        if(rs.next()){
				        	int request = rs.getInt(1) + Integer.parseInt(reqNumThe);
				        	result = sm.executeUpdate("update history set request = " + request + " where day =" + day + " and hour = " + hour + " and min = " + min + " and weight = 3");
				        }else{
				        	result = sm.executeUpdate("insert into history(day,hour,min,request,weight) values (" + day + "," + hour + "," + min + ",'" + reqNumThe + "', 3 )" );
				        }
				        //merge the request number in same day same hour same min same weight
				        
				    }catch(Exception ex) {  
				            System.out.println("<p>failed!</p>");
				    }
			    	
					//access the rate allocated to the user and send it back to the user
			    	try{  
				        sm = ct.createStatement();
				        rs = sm.executeQuery("select window from window where UUID = '" + userid + "'");
				  
				        if(rs.next()){
				        	int window = rs.getInt(1);
				        	out.print(window);
				        	System.out.print(window);
				        }
				    }catch(Exception ex) {  
				            System.out.println("<p>failed!</p>");
				    }
			    	
			    }else if("3".equals(type)){
					//type = 3; user close our app
					try{  
				        sm = ct.createStatement();
				        sm.executeUpdate("delete from window where UUID = '" + userid + "'");
				    }catch(Exception ex) {  
				            System.out.println("<p>failed!</p>");
				    }
				}
			}
	
}
