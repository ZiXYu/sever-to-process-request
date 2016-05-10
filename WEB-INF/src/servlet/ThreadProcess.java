package servlet;

import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServlet;


public class ThreadProcess extends HttpServlet implements Runnable {
	
	private int api; //total api per min	
	private volatile boolean flag = true;
	
	public ThreadProcess(){
	}
	
	public void run() {
		
		Connection ct = null;  
	    Statement sm = null;  
	    Statement sm2 = null;
	    ResultSet rs = null; 
		
		ct = Mysql.connection();
		
    	try{
	        sm = ct.createStatement();
	        sm2 = ct.createStatement();
	    }catch(Exception ex) {  
	            System.out.println("<p>failed!</p>");
	    }
    	
    	Calendar calendar = Calendar.getInstance();
	    int second = calendar.get(Calendar.SECOND);
	    System.out.print(second + "\n");
	    
	    int delay = 0;
    	
    	// TODO Auto-generated method stub
		while(flag){
			
			calendar = Calendar.getInstance();
			second = calendar.get(Calendar.SECOND);
		    
			int user = 0, reqNum = 0, rest = 0;
			Double reqWeight = 0.0;
			try{
			    if(second <= 10){
			    	delay = 10 - second;
			    }else if (second <= 25){
			    	delay = 25 - second;
			    }else if (second <= 40){
			    	delay = 40 - second;
			    }else if (second <= 55){
			    	delay = 55 - second;
			    }else{
			    	delay = 70 - second;
			    }
	    
		    	System.out.print(delay + "\n");
				
				//To synchronize the thread to run every 15 second
		    	try {
					Thread.sleep(1000 * delay);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//Count user number and all request number for all users at last 15s
				rs = sm.executeQuery("select count(id), sum(reqNumber) from window ");
				
				if(rs.next() && rs.getInt(1) != 0){
					user = rs.getInt(1);
					reqNum = rs.getInt(2);
					System.out.print(rs.getInt(1));
				}else{
					System.out.print("No active user!");
					continue;
				}
				
				//Count all request weight ^ 1/2 and rest rate which could be used in the minute
				rs = sm.executeQuery("select * from window where id is not null");
				
				while(rs.next()){
					reqWeight = reqWeight + Math.sqrt(rs.getDouble(4));
					int temp = rs.getInt(5) - rs.getInt(3);
					if( temp > 0 ){
						rest = rest + temp;
					}
				}
				
				//If the minute will be pasted right away, set the rest rate to 0
				if(second >= 55){
					rest = 0;
				}
				
				//Allocate defalut window size as 10 to all users, it will avoid that a user stop use the app for more than 15s, she could not send any requests with a window size 0
				int total = 0;
				rs = sm.executeQuery("select request from status");
				if(rs.next()){
					total = rs.getInt(1) * 15 + rest - 10 * user;
				}
			
				//Allocate rest rate to all users based on individual request number, request weight and total request number and total request weight;
				rs = sm.executeQuery("select * from window where id is not null");				
				while(rs.next()){
					int window = 0;
					
					if(reqNum != 0 && reqWeight != 0 && rs.getInt(3) != 0 && rs.getInt(4) != 0){
						double temp = 10 + total * Math.sqrt(rs.getDouble(4)) * rs.getInt(3) / ( reqNum * reqWeight);
						window = (int) temp;
					}else{
						window = 10;
					}
					int id = rs.getInt(1);
					sm2.executeUpdate("update window set window = " + window + " where id = " + id);
				}
		    }catch(Exception ex){
		    	  
		    }
		}
	}
	
	public void shutdown() {
        flag = false;
		System.out.print("Thead of allocating rate is stopped!");
    }
	
}
