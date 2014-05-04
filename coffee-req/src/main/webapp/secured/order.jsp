<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.springframework.security.core.Authentication" %>
<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page import="org.springframework.stereotype.Controller" %>



<html>
<head>
    <title></title>
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.common.min.css" rel="stylesheet" />
    <link href="http://cdn.kendostatic.com/2014.1.416/styles/kendo.default.min.css" rel="stylesheet" />
	<link href="style.css" rel="stylesheet" />
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script src="http://cdn.kendostatic.com/2014.1.416/js/kendo.all.min.js"></script>
	<script src="./nespresso.js"></script>
</head>
<body>
<div>	
<%
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String name = auth.getName(); //get logged in username

//    UserService userService = UserServiceFactory.getUserService();
//    User user = userService.getCurrentUser();
%>
<p>Logged in as <%= name %>. (You can <a href="/j_spring_security_logout">logout</a>.)</p>
	
			<input id="email" type="hidden" value=<%= name %>><br />

			<label class="emptyEmail">Please fill in a valid email</label><br /><br />
			<button class="k-button" id="submit">Submit</button><br /><br />
</div>
		
    <div id="mainContainer" >
		<h3>Choose Your Flavor: </h3>
        <div id="listView"></div>
		
	</div>
	
	

    <script type="text/x-kendo-template" id="template">
        <div id="#= ProductID #" class="product">
            <img src="../Cap/#= ProductID #.png" alt="#: ProductName # image" /><br />
            <label>#:ProductName#</label><br />
			<label>#:Price# NIS</label><br />
			<input id="amount#= ProductID #" class="nTextbox" />
        </div>
		
    </script>

</body>
</html>