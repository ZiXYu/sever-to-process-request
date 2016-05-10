package servlet;

import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServlet;


public class ThreadNotification extends HttpServlet implements Runnable {	
	private volatile boolean flag = true;
	
	public ThreadNotification(){
	}
	
	public void run() {
		
		Connection ct = null;  
	    Statement sm = null; 
	    ResultSet rs = null; 	
		int delay = 0, min = 0, hour = 0;	
		
		ct = Mysql.connection();
		
    	try{
	        sm = ct.createStatement();

	    }catch(Exception ex) {  
	            System.out.println("<p>failed!</p>");
	    }
    	
    	// TODO Auto-generated method stub
		while(flag){
			
			int total = 0;
			Calendar calendar = Calendar.getInstance();
			min = calendar.get(Calendar.MINUTE);
		    
			//To synchronize the thread to run at 45 each hour
			if(min != 45){
		    	delay = 45 + ( min / 45 ) * 60 - min;
		    }else{
		    	delay = 60;
		    }
		    
			try{
				Thread.sleep( 1000 * delay * 60 ); //sleep for delay minutes
				
				min = calendar.get(Calendar.MINUTE);
				hour = calendar.get(Calendar.HOUR);
				System.out.print( min + "\n");
				
				//Select historical data in one hour; it does not use deep learning
				rs = sm.executeQuery("select sum(request) from history where hour = " + hour + " and min < " + min);
				
				if(rs.next()){
					total = total + rs.getInt(1);
				}else{
					System.out.print("No historical data!");
					continue;
				}
				
				hour = hour - 1;
				rs = sm.executeQuery("select sum(request) from history where hour = " + hour + " and min >= " + min);
				
				if(rs.next()){
					total = total + rs.getInt(1);
				}else{
					System.out.print("No historical data!");
					continue;
				}
				
				System.out.print(total + "\n");
				
				if( (total / 60) > 30 ){
					total = total / 60;
				}else{
					total = 30;
				}
				
				System.out.print(total);
				
				//Send Email use AWS email service
				SendEmail sendEmail =new SendEmail();
				sendEmail.send(total);
				
		    }catch(Exception ex){
		    	  
		    }
		}
	}
	
	public void shutdown() {
        flag = false;
		System.out.print("Thead of sending notication is stopped!");
    }

}
