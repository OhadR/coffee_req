<html>
<head>
	<script type="text/javascript" src="https://code.jquery.com/jquery-1.4.2.js"></script>
	<script type="text/javascript" src="./javascript/oauth.js"></script>
	<title>Create Account Page</title>
</head>

<body onload='document.f.email.focus();InitCreateAccount();'>
	<h3>Create Account</h3>

	<div>
		<img src="http://nicepresso.appspot.com/nicepresso_logo.jpg" width="256"
		height="52" alt="NICE" title="NICE" border="0" /> 
	</div> 

	<%   
	    if ( null != request.getParameter("err_msg") ) {
	%>	
	<div style="margin-top:  25px ;position: relative; color: red; font:15px">
		<span style="font-weight:bold"><%= request.getParameter("err_msg") %></span>
	</div>
	<%   } %>
	
	<form name='f' 
		action='../createAccount'
		method='POST'>
		<table>
			<tr>
				<td>E-mail:</td>
				<td><input type='text' name='email' value=''></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type='password' name='confirm_password' /></td>
			</tr>
			<tr>
				<td colspan='2'><input name="submit" type="submit"
					value='Create Account' /></td>
			</tr>
		</table>
	</form>
</body>
</html>