<%@ page import="
		java.sql.*,
		servlet.Mysql	
	"
%>


<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
        <title>Request Delay Management System</title>
    </head>
    
    <body>
    	<%@ include file= "form-above.jsp" %>
					<ul class="breadcrumb">
						<li>
							<i class="icon-home"></i>
							<a href="./homepage.jsp">Homepage</a>
						</li>
					</ul><!--.breadcrumb-->
                
               <div id="page-content" class="clearfix">
					<div class="page-header position-relative">
						<h1>
							Homepage
							<small>
								<i class="icon-double-angle-right"></i>
								Welcome to Request Delay Management System
							</small>
						</h1>
					</div><!--/.page-header-->
					
                    <div class="row-fluid">
						<!--PAGE CONTENT BEGINS HERE-->
						<!--Get current status of our server-->
						<%
							Connection ct = null;
							Statement st = null;
							ResultSet rs = null;
							
							int status = 0, reqNum = 0, reqNew = 0, notification = 0, change = 0;
							
							ct = Mysql.connection();
							
							try{
								st = ct.createStatement();
								rs = st.executeQuery("select * from status");
								
								if(rs.next()){
									status = rs.getInt(1);  // status = 1, the server is allocating rate for users; otherwise, not
									reqNum = rs.getInt(2); //limit of request number for this hour
									reqNew = rs.getInt(3); //limit of reqest number for next hour
									notification = rs.getInt(4); //notification = 1, the server is sending notification for developer; otherwise, not
									change = rs.getInt(5); //change = 1, the server is changing request number per hour; otherwise, not
								}
							}catch (Exception ex){
								System.out.print("failed!\n");
							}
						%>

						<div class="row-fluid">
							<p>Now the status of process thread is : 
							<%
								if ( status == 0 ){
									out.print("<span class='danger'>Stopped</span></p>");
									out.print("<p> Click the start button to start calculating window size:");
									out.print("<button class='btn btn-small btn-success' onClick='processConfirm();' style='margin-left:10px'>Start</button>");
									out.print("</p>");
								}else{
									out.print("<span class='success'>Started</span></p>");
									out.print("<p> Click the stop button to stop calculating window size:");
									out.print("<button class='btn btn-small btn-danger' onClick='stopProConfirm();' style='margin-left:10px'>Stop</button>");
									out.print("</p>");
								}
							%>
							
							<p>Now the status of sending notification thread is : 
							<%
								if ( notification == 0 ){
									out.print("<span class='danger'>Stopped</span></p>");
									out.print("<p> Click the start button to start sending notification:");
									out.print("<button class='btn btn-small btn-success' onClick='notiConfirm();' style='margin-left:10px'>Start</button>");
									out.print("</p>");
								}else{
									out.print("<span class='success'>Started</span></p>");
									out.print("<p> Click the stop button to stop sending notification:");
									out.print("<button class='btn btn-small btn-danger' onClick='stopNotiConfirm();' style='margin-left:10px'>Stop</button>");
									out.print("</p>");
								}
							%>
							
							<p>Now the status of changing request number is : 
							<%
								if ( change == 0 ){
									out.print("<span class='danger'>Stopped</span></p>");
									out.print("<p> Click the start button to start changing request number:");
									out.print("<button class='btn btn-small btn-success' onClick='changeConfirm();' style='margin-left:10px'>Start</button>");
									out.print("</p>");
								}else{
									out.print("<span class='success'>Started</span></p>");
									out.print("<p> Click the stop button to stop changing request number:");
									out.print("<button class='btn btn-small btn-danger' onClick='stopChangeConfirm();' style='margin-left:10px'>Stop</button>");
									out.print("</p>");
								}
							%>
						</div>				

						<div class="clearfix">
							<form class="form-horizontal" action="updatenum" method="POST">
								<p>Now the number of request per second is : <% out.print(reqNum); %> /s</p>
								<p>Now the number of request per second for next hour is : <% out.print(reqNew); %> /s</p>
								<p>Please submit new API call number: 
									<input style="margin-left:10px" type="text" class="input-mini" name="number" id="spinner1" />	
									<button class='btn btn-small btn-info' style='margin-left:10px;' value="submit">Submit</button>
								</p> 
							</form>
						</div>
						<hr />
						
					</div>
                 	 
		<%@ include file= "form-under.jsp" %>           
		
		<script type="text/javascript">
			
			function stopProConfirm(){
				if(window.confirm('Are you sure to stop processing user requests?')==true){
					window.location.href="process?status=2";
				}
			}
			
			function stopNotiConfirm(){
				if(window.confirm('Are you sure to stop sending notification?')==true){
					window.location.href="notification?status=2";
				}
			}
			
			function stopChangeConfirm(){
				if(window.confirm('Are you sure to stop changing request number?')==true){
					window.location.href="change?status=2";
				}
			}
			
			function processConfirm(){
				if(window.confirm('Are you sure to start processing user request?')==true){
					window.location.href="process?status=1";
				}
			}
			
			function notiConfirm(){
				if(window.confirm('Are you sure to start sending notification?')==true){
					window.location.href="notification?status=1";
				}
			}
			
			function changeConfirm(){
				if(window.confirm('Are you sure to start changing request number?')==true){
					window.location.href="change?status=1";
				}
			}
		</script>
		
		<script src="assets/js/fuelux/fuelux.spinner.min.js"></script>
		<script type="text/javascript">
			$(function() {
				$('#spinner1').ace_spinner({value:<% out.print(reqNew); %>,min:30,max:600,step:10, btn_up_class:'btn-info' , btn_down_class:'btn-info'});
			});
		</script>
    </body>
</html>
