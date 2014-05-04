coffee_req
==========

appengine app to order coffee - ShipIt !


IF you want to secure static resources, such as HTML files, the way to do this using Spring MVC is to put them in directory A, and to
map this location to path B. Then, using Spring Security, path B can be "secured".
If resources are in path A and SS secures the same path, then when a user tries to reach a resource from the browser, it "confises" spring 
so the resource is not secured and the resource can be seen.

	<mvc:resources mapping="/secured/**" location="/secured_resources/" />

	<sec:http authentication-manager-ref="authenticationManager">
	    <sec:intercept-url pattern="/secured/**" access="ROLE_USER" />


If you want to secure JSP - this is wasy since it is not "static" resource.