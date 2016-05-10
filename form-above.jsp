<%@ page import="java.io.*,java.util.*" %>

<%   
	String url=request.getRequestURI();   
	
	url=url.substring(url.lastIndexOf("/")+1);   
%> 

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />

		<meta name="description" content="and Validation" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />

		<!--basic styles-->

		<link href="assets/css/bootstrap.min.css" rel="stylesheet" />
		<link href="assets/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="assets/css/font-awesome.min.css" />
        <link rel="stylesheet" href="css/use.css" />
		
		<link rel="stylesheet" href="use/width.css" />
		<link rel="stylesheet" href="use/font.css" />
		<link rel="stylesheet" href="use/test.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!--page specific plugin styles-->

		<link rel="stylesheet" href="assets/css/chosen.css" />
		<link rel="stylesheet" href="assets/css/jquery-ui-1.10.3.custom.min.css" />
		<link rel="stylesheet" href="assets/css/jquery.gritter.css" />

		<!--fonts-->

		<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />

		<!--ace styles-->

		<link rel="stylesheet" href="assets/css/ace.min.css" />
		<link rel="stylesheet" href="assets/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="assets/css/ace-skins.min.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="assets/css/ace-ie.min.css" />
		<![endif]-->

		<!--inline styles if any-->
	</head>

	<body>
		<div class="navbar navbar-inverse">
			<div class="navbar-inner">
				<div class="container-fluid">
					<a href="#" class="brand">
						<small>
							<i class="icon-leaf"></i>
							<span class="font_wryh">Request Delay Management System</span>
						</small>
					</a><!--/.brand-->

					<ul class="nav ace-nav pull-right">
						<li class="light-blue user-profile">
							<a data-toggle="dropdown" href="#" class="user-menu dropdown-toggle">
								<span id="user_info" class="font_wryh">
									<small>Welcome,</small>
									Users
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer" id="user_menu">
								<li>
									<a href="">
										<i class="icon-off"></i>
										Login	
									</a>
								</li>
							</ul>
						</li>
					</ul><!--/.ace-nav-->
				</div><!--/.container-fluid-->
			</div><!--/.navbar-inner-->
		</div>

		<div class="container-fluid" id="main-container">
			<a id="menu-toggler" href="#">
				<span></span>
			</a>

			<div id="sidebar">
				<div id="sidebar-shortcuts">
					<div id="sidebar-shortcuts-large">
						<button class="btn btn-small btn-success" onclick="window.location.href=''">
							<i class="icon-home"></i>
						</button>

						<button class="btn btn-small btn-info" onclick="window.location.href=''">
							<i class="icon-pencil"></i>
						</button>

						<button class="btn btn-small btn-warning" onclick="window.location.href=''">
							<i class="icon-group"></i>
						</button>

						<button class="btn btn-small btn-danger" onclick="window.location.href=''">
							<i class="icon-cogs"></i>
						</button>
					</div>

					<div id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>

						<span class="btn btn-info"></span>

						<span class="btn btn-warning"></span>

						<span class="btn btn-danger"></span>
					</div>
				</div><!--#sidebar-shortcuts-->

				<ul class="nav nav-list">
					<li <% if("homepage.jsp".equals(url) || "".equals(url)) out.print("class='active'");  %>>
						<a href="./homepage.jsp">
							<i class="icon-home"></i>
							<span>
								Homepage
							 
							</span>
						</a>
					</li>
					
					<li <% if("API.jsp".equals(url)) out.print("class='active'");  %>>
						<a href="./API.jsp">
							<i class="icon-book"></i>
							<span>API Request</span>
						</a>
					</li>
				</ul><!--/.nav-list-->

				<div id="sidebar-collapse">
					<i class="icon-double-angle-left"></i>
				</div>
			</div>

			<div id="main-content" class="clearfix">
				<div id="breadcrumbs">

	</body>
</html>
