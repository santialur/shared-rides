<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 

<body>
	<div class="tupper mini">
		<span id="i18n">
    		<a href="?lang=es">
    			<img src="resources/images/ar.png"/>
    		</a> 
    		 
    		<a href="?lang=en">
    			<img src="resources/images/us.png"/>
    		</a>
		</span>
	</div>

    <div class="tupper mini">
        <!----------------  CONTENT     -------------->
		<div id="theLogin">
			<img 	width=275px src="resources/images/logo1.png"/>
			<hr		class="hrs"/>
			
			<h3 	style="text-align:center"> 
				<spring:message code="label.login"/>
			</h3>
	
	
			<form method="POST" action="validate.do">

				<!-- <spring:message code="label.email"/>	<br> --> 
				<input class="theInputs" type="text"		name="email"	placeholder="mike@email.com">	<br>			
				<!--<spring:message code="label.password"/>	<br> -->
				<input class="theInputs" type="password"	name="pwd"		placeholder="Password">			<br>
				
				<input class="but" id="butLogin" type="submit" value="<spring:message code="label.enter"/>">
			</form>
		</div><%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 

<body>
	<div class="tupper mini">
		<span id="i18n">
    		<a href="?lang=es">
    			<img src="resources/images/ar.png"/>
    		</a> 
    		 
    		<a href="?lang=en">
    			<img src="resources/images/us.png"/>
    		</a>
		</span>
	</div>

    <div class="tupper mini">
        <!----------------  CONTENT     -------------->
		<div id="theLogin">
			<img 	width=275px src="resources/images/logo1.png"/>
			<hr		class="hrs"/>
			
			<h3 	style="text-align:center"> 
				<spring:message code="label.login"/>
			</h3>
	
	
			<form method="POST" action="validate.do">

				<!-- <spring:message code="label.email"/>	<br> --> 
				<input class="theInputs" type="text"		name="email"	placeholder="mike@email.com">	<br>			
				<!--<spring:message code="label.password"/>	<br> -->
				<input class="theInputs" type="password"	name="pwd"		placeholder="Password">			<br>
				
				<input class="but" id="butLogin" type="submit" value="<spring:message code="label.enter"/>">
			</form>
		</div>
		
		
		<!----------------  PICTURE     -------------->
		<img id="introPic" width="50px" src="resources/images/intropic.png"/>		
	</div>
</body>


<!--
	http://i43.tinypic.com/15840oi.png  #LOGO
	http://i43.tinypic.com/28hmrf8.png	#FOTO		
-->
		
		
		<!----------------  PICTURE     -------------->
		<img id="introPic" width="50px" src="resources/images/intropic.png"/>		
	</div>
</body>


<!--
	http://i43.tinypic.com/15840oi.png  #LOGO
	http://i43.tinypic.com/28hmrf8.png	#FOTO		
-->