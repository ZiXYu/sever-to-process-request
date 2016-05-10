package servlet;

import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;

import java.sql.*;

public class SetupMysql extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    @Override
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {
    	
    	PrintWriter out = resp.getWriter();
        Connection ct = null;  
	    Statement sm = null;  
	    ResultSet rs = null; 
		int result;
	    
	    ct = Mysql.connection();
	    
    	try{
    		
	        sm = ct.createStatement();
	        
			//Drop table if already exists
	        result = sm.executeUpdate("drop table if exists history");
	        if(result != -1){
	        	out.print("delete table history successfully!\n");
	        }
	        
	        result = sm.executeUpdate("drop table if exists request");
	        if(result != -1){
	        	out.print("delete table request successfully!\n");
	        }
	        
	        result = sm.executeUpdate("drop table if exists window");
	        if(result != -1){
	        	out.print("delete table window successfully!\n");
	        }
	        
	        result = sm.executeUpdate("drop table if exists status");
	        if(result != -1){
	        	out.print("delete table status successfully!\n");
	        }
	        
	        
	        //Create new tables
	        result = sm.executeUpdate("create table history(id int(1) not null AUTO_INCREMENT, day int(1), hour int(1), min int(1), request int(1), weight int(1),  primary key (id))");
	        if(result != -1){
	        	out.print("create table history successfully!\n");
	        }else{
	        	out.print("create table history failed!\n");
	        }
			
	        result = sm.executeUpdate("create table request(id int(1) not null AUTO_INCREMENT, day int(1), hour int(1), min int(1), request int(1), primary key (id))");
	        if(result != -1){
	        	out.print("create table request successfully!\n");
	        }else{
	        	out.print("create table request failed!\n");
	        }
			
	        result = sm.executeUpdate("create table window(id int(1) not null AUTO_INCREMENT, UUID char(255), reqNumber int(1), reqWeight int(1), window int(1), primary key (id))");
	        if(result != -1){
	        	out.print("create table window successfully!\n");
	        }else{
	        	out.print("create table window failed!\n");
	        }
			
	        result = sm.executeUpdate("create table status(flag int(1), request int(1), requestNew int(1), notification int(1), changeNum int(1))");
	        if(result != -1){
	        	out.print("create table status successfully!\n");
	        }else{
	        	out.print("create table status failed!\n");
	        }  
	        
			//Insert defalut row to table status
	        sm.executeUpdate("insert into status(flag,request,requestNew,notification,changeNum) values (0,30,30,0,0)");

	        rs.close();
	        sm.close();
	        ct.close();
	        out.close();
	        
        }
        catch(Exception ex) {  
            out.println("<p>failed!</p>");
        }
    
    }
}
