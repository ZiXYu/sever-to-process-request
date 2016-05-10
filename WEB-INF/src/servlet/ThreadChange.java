package servlet;

import java.sql.*;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServlet;


public class ThreadChange extends HttpServlet implements Runnable {
	
	private volatile boolean flag = true;
	
	public ThreadChange(){
	}
	
	public void run() {
		
		Connection ct = null;  
	    Statement sm = null; 
	    ResultSet rs = null;
		int delay = 0, min = 0;
				
		ct = Mysql.connection();
		
    	try{
	        sm = ct.createStatement();
	    }catch(Exception ex) {  
	        System.out.print("Create statement failed!");
	    }
    	
    	// TODO Auto-generated method stub
		while(flag){
			int request = 30; // Set default request value
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
				System.out.print( min + "\n");
				
				//Replace the current request number with the new request number
				rs = sm.executeQuery("select requestNew from status where requestNew is not NULL");
				
				if(rs.next()){
					request = rs.getInt(1);
				}
				
				sm.executeUpdate("Update status set request = " + request);
				
		    }catch(Exception ex){
				System.out.print("Update request number failed!");
		    }
		}
	}
	
	public void shutdown() {
        flag = false;
		System.out.print("Thead of updating request number is stopped!");
    }

}
